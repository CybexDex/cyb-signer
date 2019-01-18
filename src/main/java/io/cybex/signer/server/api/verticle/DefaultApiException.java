package io.cybex.signer.server.api.verticle;

import io.cybex.signer.server.api.SignerApiException;

public final class DefaultApiException extends SignerApiException {
    public DefaultApiException(int statusCode, String statusMessage) {
        super(statusCode, statusMessage);
    }
    
    public static final DefaultApiException Default_cancelAllPost_400_Exception = new DefaultApiException(400, "Parameter Error");
    public static final DefaultApiException Default_cancelAllPost_401_Exception = new DefaultApiException(401, "Unauthorized");
    public static final DefaultApiException Default_cancelAllPost_404_Exception = new DefaultApiException(404, "Not Found");
    public static final DefaultApiException Default_cancelOrderPost_400_Exception = new DefaultApiException(400, "Parameter Error");
    public static final DefaultApiException Default_cancelOrderPost_401_Exception = new DefaultApiException(401, "Unauthorized");
    public static final DefaultApiException Default_cancelOrderPost_404_Exception = new DefaultApiException(404, "Not Found");
    public static final DefaultApiException Default_newOrderPost_400_Exception = new DefaultApiException(400, "Parameter Error");
    public static final DefaultApiException Default_newOrderPost_401_Exception = new DefaultApiException(401, "Unauthorized");
    public static final DefaultApiException Default_newOrderPost_404_Exception = new DefaultApiException(404, "Not Found");
    

}