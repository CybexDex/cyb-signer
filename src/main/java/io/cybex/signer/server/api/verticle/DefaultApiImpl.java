package io.cybex.signer.server.api.verticle;

import io.cybex.graphenej.Asset;
import io.cybex.signer.Constants;
import io.cybex.signer.Utils;
import io.cybex.signer.server.api.Error;
import io.cybex.signer.server.api.AssetPair;
import io.cybex.signer.server.api.model.*;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class DefaultApiImpl implements DefaultApi {
    final static Logger LOGGER = LoggerFactory.getLogger(DefaultApiImpl.class);

    private String refBlockId;
    private String chainId;
    private Map<String, AssetPair> availableAssetPairs = new HashMap();
    private Map<String, SignerAsset> availableAssets = new HashMap();

    private ECKey ecPrivateKey;

    private String sellerId;

    private String feeAssetId;
    public Asset feeAsset;

    private long newFee;
    private long cancelFee;
    private long cancelAllFee;

    private boolean checkSymbolPair(String assetPair, Handler handler)
    {
        if (!availableAssetPairs.containsKey(assetPair))
        {
            buildRestError(handler, Error.ErrorUnsupportedAssetPair.getCode(), Error.ErrorUnsupportedAssetPair.getMessage() + assetPair);
            return false;
        }

        return true;
    }

    public void buildRestError(Handler<AsyncResult<Object>> handler, int errorCode, String errorMsg)
    {
        JsonObject resJson= new JsonObject();
        resJson.put(Constants.ReturnCode.RES_KEY_STATUS, Constants.ReturnCode.RES_VALUE_FAILED);
        resJson.put(Constants.ReturnCode.RES_KEY_CODE, errorCode);
        resJson.put(Constants.ReturnCode.RES_KEY_MSG, errorMsg);
        handler.handle(Future.succeededFuture(resJson));
    }

    @Override
    public void cancelAllPost(CancelAllRequest request, Handler<AsyncResult<Object>> handler) {
        String symbol = request.getAssetPair();
        if (!checkSymbolPair(symbol, handler)) return;

        String[] items = symbol.split("/");
        String sellCurrency = items[0];
        String recvCurrency = items[1];

        String sellAsset = availableAssets.get(sellCurrency).getAssetId();
        String recvAsset = availableAssets.get(recvCurrency).getAssetId();
        CancelAll cancelAll = new CancelAll();

        cancelAll = Utils.signCancelAll(chainId, cancelAll, refBlockId, ecPrivateKey, sellerId, sellAsset, recvAsset, this.cancelAllFee, feeAsset);

        handler.handle(Future.succeededFuture(cancelAll));
    }

    @Override
    public void cancelOrderPost(CancelOrderRequest request, Handler<AsyncResult<Object>> handler) {
        CancelOrder cancel = new CancelOrder();

        cancel = Utils.signCancelOrder(chainId, cancel, refBlockId, ecPrivateKey, sellerId, request.getOriginalTransactionId(), this.cancelFee, feeAsset);

        handler.handle(Future.succeededFuture(cancel));
    }

    @Override
    public void newOrderPost(NewOrderRequest request, Handler<AsyncResult<Object>> handler) {
        String symbol = request.getAssetPair();
        if (!checkSymbolPair(symbol, handler)) return;

        AssetPair pair = this.availableAssetPairs.get(symbol);
        if (request.getQuantity() < pair.getMinQuantity().doubleValue())
        {
            buildRestError(handler, Error.ErrorLessThanMinQuantity.getCode(), Error.ErrorLessThanMinQuantity.getMessage() + pair.getMinQuantity());
            return;
        }

        if (pair.getMinTickSize().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal num = BigDecimal.valueOf(request.getPrice()).divide(pair.getMinTickSize());
            if (num.stripTrailingZeros().scale() > 0) {
                buildRestError(handler, Error.ErrorMinTickSizeViolated.getCode(), Error.ErrorMinTickSizeViolated.getMessage() + " Current  tick size: " + pair.getMinTickSize().stripTrailingZeros());
                return;
            }
        }

        String[] items = symbol.split("/");
        String baseCurrency = items[0];
        String quoteCurrency = items[1];

        String sellAsset;
        String recvAsset;

        long amtToSell = 0;
        long minToRecv = 0;

        String side = request.getSide();
        if (!Constants.SIDE.BUY.equals(side) && !Constants.SIDE.SELL.equals(side)) {
            buildRestError(handler, Error.ErrorUnsupportedSide.getCode(), Error.ErrorUnsupportedSide.getMessage() + side);
            return;
        }

        if (Constants.SIDE.BUY.equals(side)) {
            amtToSell = Utils.moveDecimalPlaceRight(request.getPrice() * request.getQuantity(), availableAssets.get(quoteCurrency).getPrecision());
            sellAsset = availableAssets.get(quoteCurrency).getAssetId();
            minToRecv = Utils.moveDecimalPlaceRight(request.getQuantity(), availableAssets.get(baseCurrency).getPrecision());
            recvAsset = availableAssets.get(baseCurrency).getAssetId();
        }
        else
        {
            amtToSell = Utils.moveDecimalPlaceRight(request.getQuantity(), availableAssets.get(baseCurrency).getPrecision());
            sellAsset = availableAssets.get(baseCurrency).getAssetId();
            minToRecv = Utils.moveDecimalPlaceRight(request.getPrice() * request.getQuantity(), availableAssets.get(quoteCurrency).getPrecision());
            recvAsset = availableAssets.get(quoteCurrency).getAssetId();
        }

        NewOrder order = new NewOrder();

        order = Utils.signNewOrder(chainId, order, refBlockId, ecPrivateKey, sellerId, sellAsset, amtToSell, recvAsset, minToRecv, this.newFee, this.feeAsset);

        handler.handle(Future.succeededFuture(order));
    }

    @Override
    public void setRefData(String refData) {
        JsonObject jsonObj = new JsonObject(refData);
        refBlockId = jsonObj.getString("refBlockId");
        LOGGER.info("Got refBlockId: " + refBlockId);

        // Except for refBlockId, other parameters are initialized once
        if (chainId == null)
        {
            chainId = jsonObj.getString("chainId");
            LOGGER.info("Got chainId: " + chainId);
            JsonArray jsonAssets = jsonObj.getJsonArray("availableAssets");
            LOGGER.info("Got availableAssets: " + jsonAssets);
            for(int i = 0; i < jsonAssets.size(); i++)
            {
                try {
                    SignerAsset signerAsset = Json.mapper.readValue((jsonAssets.getJsonObject(i)).encode(), SignerAsset.class);
                    availableAssets.put(signerAsset.getAssetName(), signerAsset);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JsonArray jsonAssetPairs = jsonObj.getJsonArray("availableAssetPairs");
            LOGGER.info("Got availableAssetPairs: " + jsonAssetPairs);
            for(int i = 0; i < jsonAssetPairs.size(); i++)
            {
                try {
                    AssetPair assetPair = Json.mapper.readValue((jsonAssetPairs.getJsonObject(i)).encode(), AssetPair.class);
                    availableAssetPairs.put(assetPair.getName(), assetPair);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String cybPairName = "CYB/CYB";
            AssetPair cybPair = new AssetPair(cybPairName);
            availableAssetPairs.put(cybPairName, cybPair);

            JsonObject jsonFees = jsonObj.getJsonObject("fees");
            feeAssetId = jsonFees.getString("feeAssetId");
            LOGGER.info("Got feeAssetId: " + feeAssetId);
            feeAsset = new Asset(feeAssetId);
            newFee = jsonFees.getLong("newFee");
            LOGGER.info("Got newFee: " + newFee);
            cancelFee = jsonFees.getLong("cancelFee");
            LOGGER.info("Got cancelFee: " + cancelFee);
            cancelAllFee = jsonFees.getLong("cancelAllFee");
            LOGGER.info("Got cancelAllFee: " + cancelAllFee);
        }
    }

    @Override
    public void setPrivateKey(String privateKey) {
        this.ecPrivateKey = DumpedPrivateKey.fromBase58(null, privateKey).getKey();
    }

    @Override
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
