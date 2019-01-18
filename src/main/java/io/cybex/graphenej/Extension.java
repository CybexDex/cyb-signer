package io.cybex.graphenej;

import io.cybex.graphenej.interfaces.ByteSerializable;
import io.cybex.graphenej.interfaces.JsonSerializable;

public abstract class Extension implements JsonSerializable, ByteSerializable {

    ExtensionType type;

    public Extension(ExtensionType type) {
        this.type = type;
    }

    public byte getId() {
        return (byte) this.type.ordinal();
    }
}
