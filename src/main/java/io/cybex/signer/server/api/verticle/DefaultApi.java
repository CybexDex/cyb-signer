package io.cybex.signer.server.api.verticle;

import io.cybex.signer.server.api.model.CancelAllRequest;
import io.cybex.signer.server.api.model.CancelOrderRequest;
import io.cybex.signer.server.api.model.NewOrderRequest;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface DefaultApi  {
    //cancelAll.post
    void cancelAllPost(CancelAllRequest request, Handler<AsyncResult<Object>> handler);
    
    //cancelOrder.post
    void cancelOrderPost(CancelOrderRequest request, Handler<AsyncResult<Object>> handler);
    
    //newOrder.post
    void newOrderPost(NewOrderRequest request, Handler<AsyncResult<Object>> handler);

    void setRefData(String refData);

    void setPrivateKey(String privateKey);

    void setSellerId(String sellerId);
}
