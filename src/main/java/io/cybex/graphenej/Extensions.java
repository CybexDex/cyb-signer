package io.cybex.graphenej;

import com.google.common.primitives.Bytes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.cybex.graphenej.interfaces.ByteSerializable;
import io.cybex.graphenej.interfaces.JsonSerializable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 11/9/16.
 */
public class Extensions implements JsonSerializable, ByteSerializable {
    public static final String KEY_EXTENSIONS = "extensions";

    private ExtensionType type;
    private ArrayList<Extension> extensions;

    public Extensions() {
        extensions = new ArrayList<>();
    }

    @Override
    public String toJsonString() {
        return null;
    }

    public void addExtension(Extension ex) {
        extensions.add(ex);
    }

    @Override
    public JsonElement toJsonObject() {
        JsonArray array = new JsonArray();
        for (JsonSerializable o : extensions)
            array.add(o.toJsonObject());
        return array;
    }

    @Override
    public byte[] toBytes() {
        List<Byte> byteArray = new ArrayList<Byte>();

        byteArray.add((byte) this.extensions.size());

        for (Extension ex : extensions) {
            byteArray.add(ex.getId());
            byteArray.addAll(Bytes.asList(ex.toBytes()));
        }
        return Bytes.toArray(byteArray);
    }

    public int size() {
        return extensions.size();
    }
}