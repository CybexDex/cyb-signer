package io.cybex.signer.server.api;

public class SignerApiException extends Exception {
    private int statusCode;
    private String statusMessage;

    public SignerApiException(int statusCode, String statusMessage) {
        super();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public static final SignerApiException INTERNAL_SERVER_ERROR = new SignerApiException(500, "Internal Server Error");
    public static final SignerApiException BAD_REQUEST = new SignerApiException(400, "Incorrect input");
}