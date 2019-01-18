package io.cybex.signer.server.api;

public final class Error{
    private int code;
    private String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static final Error ErrorUnsupportedAssetPair = new Error(1001, "Unsupported asset pair: ");
    public static final Error ErrorUnsupportedSide = new Error(1002, "Unsupported side: ");
    public static final Error ErrorMinTickSizeViolated = new Error(1003, "MinTickSize is violated in price.");
    public static final Error ErrorLessThanMinQuantity = new Error(1004, "Quantity is less than minimum quantity: ");

}