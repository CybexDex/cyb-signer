package io.cybex.graphenej;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CxlTrxId extends Extension {
    public static final String KEY_TRX_ID = "trx_id";

    private String trxId;

    public CxlTrxId(String trxId) {
        super(ExtensionType.CXL_TRX_ID);

        this.trxId = trxId;
    }

    @Override
    public JsonElement toJsonObject() {
        JsonObject options = new JsonObject();
        options.addProperty(KEY_TRX_ID, trxId);
        return options;
    }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        //return trxId.getBytes();
        return Util.hexToBytes(trxId);
    }
}
