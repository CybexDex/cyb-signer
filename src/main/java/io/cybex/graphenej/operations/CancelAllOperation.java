package io.cybex.graphenej.operations;

import com.google.common.primitives.Bytes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.cybex.graphenej.Asset;
import io.cybex.graphenej.AssetAmount;
import io.cybex.graphenej.BaseOperation;
import io.cybex.graphenej.OperationType;
import io.cybex.graphenej.UserAccount;
import io.cybex.graphenej.Varint;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public class CancelAllOperation extends BaseOperation {

    private static final String KEY_SELLER = "seller";
    private static final String KEY_SELL_ASSET_ID = "sellAssetId";
    private static final String KEY_RECV_ASSET_ID = "recvAssetId";

    private final int EXPIRATION_BYTE_LENGTH = 4;
    private UserAccount seller;
    private Asset sellAsset;
    private Asset recvAsset;
    private AssetAmount fee;

    public CancelAllOperation(UserAccount seller, Asset sellAsset, Asset recvAsset) {
        super(OperationType.CANCEL_ALL_OPERATION);
        this.seller = seller;
        this.sellAsset = sellAsset;
        this.recvAsset = recvAsset;
    }

    @Override
    public JsonElement toJsonObject() {
        JsonArray array = (JsonArray) super.toJsonObject();
        JsonObject jsonObject = new JsonObject();

        if (fee != null) {
            jsonObject.add(KEY_FEE, fee.toJsonObject());
        }
        jsonObject.addProperty(KEY_SELLER, seller.getObjectId());
        jsonObject.addProperty(KEY_SELL_ASSET_ID, sellAsset.getObjectId());
        jsonObject.addProperty(KEY_RECV_ASSET_ID, recvAsset.getObjectId());

        array.add(jsonObject);
        return array;
    }

    private byte[] toBytes(int intVar) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutput out = new DataOutputStream(baos);
        try {
            Varint.writeUnsignedVarInt(intVar, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private byte[] toBytes(long longVar) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutput out = new DataOutputStream(baos);
        try {
            Varint.writeUnsignedVarLong(longVar, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    @Override
    public byte[] toBytes() {

        byte[] feeBytes = this.fee.toBytes();
        byte[] sellerBytes = this.seller.toBytes();
        byte[] sellAssetIdBytes = toBytes(getInstanceId(this.sellAsset));
        byte[] recvAssetIdBytes = toBytes(getInstanceId(this.recvAsset));
        byte[] extensions = this.extensions.toBytes();

        return Bytes.concat(feeBytes, sellerBytes, sellAssetIdBytes, recvAssetIdBytes, extensions);
    }

    private int getInstanceIdInt(String instance) {
        return Integer.parseInt(instance.split("\\.")[2]);
    }

    private long getInstanceId(Asset asset) {
        return Long.parseLong(asset.getObjectId().split("\\.")[2]);
    }

    public UserAccount getSeller() {
        return seller;
    }

    public void setSeller(UserAccount seller) {
        this.seller = seller;
    }

    public Asset getSellAsset() {
        return sellAsset;
    }

    public void setSellAsset(Asset sellAsset) {
        this.sellAsset = sellAsset;
    }

    public Asset getRecvAsset() {
        return recvAsset;
    }

    public void setRecvAsset(Asset recvAsset) {
        this.recvAsset = recvAsset;
    }

    public AssetAmount getFee() {
        return fee;
    }

    @Override
    public void setFee(AssetAmount fee) {
        this.fee = fee;
    }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public String toString() {
        return "CancelAllOperation{" +
                "EXPIRATION_BYTE_LENGTH=" + EXPIRATION_BYTE_LENGTH +
                ", seller=" + seller +
                ", sellAsset=" + sellAsset +
                ", recvAsset=" + recvAsset +
                ", fee=" + fee +
                ", type=" + type +
                ", extensions=" + extensions +
                '}';
    }
}
