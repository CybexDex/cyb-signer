package io.cybex.signer;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.UnsignedLong;
import io.cybex.graphenej.*;
import io.cybex.graphenej.operations.CancelAllOperation;
import io.cybex.graphenej.operations.LimitOrderCancelOperation;
import io.cybex.graphenej.operations.LimitOrderCreateOperation;
import io.cybex.signer.server.api.model.CancelAll;
import io.cybex.signer.server.api.model.CancelOrder;
import io.cybex.signer.server.api.model.NewOrder;
import org.bitcoinj.core.ECKey;

import java.time.*;
import java.util.ArrayList;

import static io.cybex.graphenej.Util.byteToString;

public class Utils {
    public static long moveDecimalPlaceRight(double value, int decimalPlace) {
        return (long) (value * Math.pow(10, decimalPlace));
    }

    public static int endianReverseU32(int x) {
        return x >> 24 & 255
                | (x >> 16 & 255) << 8
                | (x >> 8 & 255) << 16
                | (x & 255) << 24;
    }

    public static long[] hashRipemd160(String hex) {
        int lHash2[] = new int[5];
        BaseEncoding encoding = BaseEncoding.base16().lowerCase();
        byte[] byteDecode = encoding.decode(hex);

        lHash2[0] = ((byteDecode[3] & 0xff) << 24) | ((byteDecode[2] & 0xff) << 16) | ((byteDecode[1] & 0xff) << 8) | (byteDecode[0] & 0xff);
        lHash2[1] = ((byteDecode[7] & 0xff) << 24) | ((byteDecode[6] & 0xff) << 16) | ((byteDecode[5] & 0xff) << 8) | (byteDecode[4] & 0xff);
        lHash2[2] = ((byteDecode[11] & 0xff) << 24) | ((byteDecode[10] & 0xff) << 16) | ((byteDecode[9] & 0xff) << 8) | (byteDecode[8] & 0xff);
        lHash2[3] = ((byteDecode[15] & 0xff) << 24) | ((byteDecode[14] & 0xff) << 16) | ((byteDecode[13] & 0xff) << 8) | (byteDecode[12] & 0xff);
        lHash2[4] = ((byteDecode[19] & 0xff) << 24) | ((byteDecode[18] & 0xff) << 16) | ((byteDecode[17] & 0xff) << 8) | (byteDecode[16] & 0xff);

        long unsignedLong[] = new long[2];
        unsignedLong[0] = Integer.toUnsignedLong(lHash2[0]);
        unsignedLong[1] = Integer.toUnsignedLong(lHash2[1]);
        return unsignedLong;
    }

    public static int getInstanceId(String id) {
        String[] parts = id.split("\\.");
        if (parts.length == 3) {
            return Integer.parseInt(parts[2]);
        }

        return 0;
    }

    public static int calcRefBlockNum(String refBlockId) {
        long[] unsignedLong = hashRipemd160(refBlockId);
        return endianReverseU32((int) unsignedLong[0]) & 0xFFFF;
    }

    public static long calcRefBlockPrefix(String refBlockId) {
        long[] unsignedLong = hashRipemd160(refBlockId);
        return unsignedLong[1];
    }

    public static long getUtcDayEndEpoch() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("UTC"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight).plusDays(1);

        ZoneOffset zoneOffSet = ZoneOffset.of("+00:00");
        return todayMidnight.toEpochSecond(zoneOffSet);
    }

    public static NewOrder signNewOrder(String chainId, NewOrder order, String refBlockId, ECKey ecPrivateKey, String sellerId, String sellAssetId, long sellAmount, String recvAssetId, long recvAmount, long fee, Asset feeAsset) {
        // Prepare the signature
        UserAccount seller = new UserAccount(sellerId);
        Asset sellAsset = new Asset(sellAssetId);
        AssetAmount amountToSell = new AssetAmount(UnsignedLong.valueOf(sellAmount), sellAsset);

        Asset recvAsset = new Asset(recvAssetId);
        AssetAmount minToReceive = new AssetAmount(UnsignedLong.valueOf(recvAmount), recvAsset);

        long txExpiration = ZonedDateTime.now(ZoneId.of("UTC")).plusHours(23).plusMinutes(59).toEpochSecond();
        long orderExpiration = ZonedDateTime.now(ZoneId.of("UTC")).withHour(23).withMinute(59).withSecond(59).toEpochSecond();

        LimitOrderCreateOperation limitOrderCreateOperation = new LimitOrderCreateOperation(seller, amountToSell, minToReceive, (int) orderExpiration, false);

        AssetAmount feeAmount = new AssetAmount(UnsignedLong.valueOf(fee), feeAsset);
        limitOrderCreateOperation.setFee(feeAmount);

        int refBlockNum = calcRefBlockNum(refBlockId);
        long refBlockPrefix = calcRefBlockPrefix(refBlockId);

        BlockData blockData = new BlockData(refBlockNum, refBlockPrefix, txExpiration);
        ArrayList operationList = new ArrayList<BaseOperation>();
        operationList.add(limitOrderCreateOperation);

        Transaction tx = new Transaction(Util.hexToBytes(chainId), ecPrivateKey, blockData, operationList);

        byte[] sign = tx.getGrapheneSignature();
        String transactionId = tx.getTransactionId();

        order.setRefBlockNum(Long.valueOf(refBlockNum));
        order.setRefBlockPrefix(refBlockPrefix);
        order.setTxExpiration(blockData.getExpiration());

        io.cybex.signer.server.api.model.AssetAmount apiFeeAmount = new io.cybex.signer.server.api.model.AssetAmount(feeAmount.getAsset().getObjectId(), feeAmount.getAmount().longValue());

        io.cybex.signer.server.api.model.AssetAmount apiAmountToSell = new io.cybex.signer.server.api.model.AssetAmount(amountToSell.getAsset().getObjectId(),
                amountToSell.getAmount().longValue());

        io.cybex.signer.server.api.model.AssetAmount apiMinToReceive = new io.cybex.signer.server.api.model.AssetAmount(minToReceive.getAsset().getObjectId(),
                minToReceive.getAmount().longValue());

        order.setSeller(sellerId);
        order.setFee(apiFeeAmount);
        order.setAmountToSell(apiAmountToSell);
        order.setMinToReceive(apiMinToReceive);
        order.setExpiration(orderExpiration);
        order.setSignature(byteToString(sign));
        order.setFillOrKill(0);
        order.setTransactionId(transactionId);
        order.setTransactionType(Constants.TransactionType.NewLimitOrder);

        return order;
    }

    public static CancelOrder signCancelOrder(String chainId, CancelOrder order, String refBlockId, ECKey ecPrivateKey, String sellerId, String origTrxId, long fee, Asset feeAsset) {
        LimitOrder limitOrder = new LimitOrder(feeAsset.getObjectId());
        UserAccount feePayingAccount = new UserAccount(sellerId);

        LimitOrderCancelOperation cancelByTrxId = new LimitOrderCancelOperation(limitOrder, feePayingAccount);

        CxlTrxId ex = new CxlTrxId(origTrxId);
        cancelByTrxId.addExtension(ex);

        AssetAmount feeAmount = new AssetAmount(UnsignedLong.valueOf(fee), feeAsset);
        cancelByTrxId.setFee(feeAmount);

        long expiration = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(5).toEpochSecond();

        int refBlockNum = calcRefBlockNum(refBlockId);
        long refBlockPrefix = calcRefBlockPrefix(refBlockId);

        BlockData blockData = new BlockData(refBlockNum, refBlockPrefix, expiration);
        ArrayList operationList = new ArrayList<BaseOperation>();
        operationList.add(cancelByTrxId);

        Transaction tx = new Transaction(Util.hexToBytes(chainId), ecPrivateKey, blockData, operationList);

        byte[] sign = tx.getGrapheneSignature();
        String transactionId = tx.getTransactionId();

        order.setRefBlockNum(Long.valueOf(refBlockNum));
        order.setRefBlockPrefix(refBlockPrefix);
        order.setTxExpiration(blockData.getExpiration());
        order.setOrderId(String.valueOf(getInstanceId(feeAsset.getObjectId())));

        io.cybex.signer.server.api.model.AssetAmount apiFeeAmount = new io.cybex.signer.server.api.model.AssetAmount(feeAmount.getAsset().getObjectId(),
                feeAmount.getAmount().longValue());

        order.setFee(apiFeeAmount);
        order.setFeePayingAccount(sellerId);
        order.setSignature(byteToString(sign));
        order.setOriginalTransactionId(origTrxId);
        order.setTransactionId(transactionId);
        order.setTransactionType(Constants.TransactionType.Cancel);

        return order;
    }

    public static CancelAll signCancelAll(String chainId, CancelAll order, String refBlockId, ECKey ecPrivateKey, String sellerId, String sellAssetId, String recvAssetId, long fee, Asset feeAsset) {
        UserAccount seller = new UserAccount(sellerId);
        Asset baseAsset = new Asset(sellAssetId);
        Asset quoteAsset = new Asset(recvAssetId);

        CancelAllOperation cancelAllOperation = new CancelAllOperation(seller, baseAsset, quoteAsset);

        AssetAmount feeAmount = new AssetAmount(UnsignedLong.valueOf(fee), feeAsset);
        cancelAllOperation.setFee(feeAmount);

        long expiryTime = ZonedDateTime.now(ZoneId.of("UTC")).plusMinutes(5).toEpochSecond();

        int refBlockNum = calcRefBlockNum(refBlockId);
        long refBlockPrefix = calcRefBlockPrefix(refBlockId);

        BlockData blockData = new BlockData(refBlockNum, refBlockPrefix, expiryTime);
        ArrayList operationList = new ArrayList<BaseOperation>();
        operationList.add(cancelAllOperation);

        Transaction tx = new Transaction(Util.hexToBytes(chainId), ecPrivateKey, blockData, operationList);

        byte[] sign = tx.getGrapheneSignature();
        String transactionId = tx.getTransactionId();

        order.setRefBlockNum(Long.valueOf(refBlockNum));
        order.setRefBlockPrefix(refBlockPrefix);
        order.setTxExpiration(blockData.getExpiration());

        io.cybex.signer.server.api.model.AssetAmount apiFeeAmount = new io.cybex.signer.server.api.model.AssetAmount(feeAmount.getAsset().getObjectId(),
                feeAmount.getAmount().longValue());

        order.setFee(apiFeeAmount);
        order.setSeller(sellerId);
        order.setSellAssetId((baseAsset.getObjectId()));
        order.setRecvAssetId(quoteAsset.getObjectId());
        order.setSignature(byteToString(sign));
        order.setTransactionType(Constants.TransactionType.CancelAll);
        order.setTransactionId(transactionId);

        return order;
    }
}
