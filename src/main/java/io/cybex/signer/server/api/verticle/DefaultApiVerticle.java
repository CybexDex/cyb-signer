package io.cybex.signer.server.api.verticle;

import io.cybex.signer.Constants;
import io.cybex.signer.server.api.SignerApiException;
import io.cybex.signer.server.api.model.CancelAllRequest;
import io.cybex.signer.server.api.model.CancelOrderRequest;
import io.cybex.signer.server.api.model.NewOrderRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class DefaultApiVerticle extends AbstractVerticle {
    final static Logger LOGGER = LoggerFactory.getLogger(DefaultApiVerticle.class);

    final static String CANCELALL_POST_SERVICE_ID = "cancelAll.post";
    final static String CANCELORDER_POST_SERVICE_ID = "cancelOrder.post";
    final static String NEWORDER_POST_SERVICE_ID = "newOrder.post";
    private static Set allowedHost = new HashSet();
    final DefaultApi service;


    public DefaultApiVerticle() {
        try {
            Class serviceImplClass = getClass().getClassLoader().loadClass("io.cybex.signer.server.api.verticle.DefaultApiImpl");
            service = (DefaultApi) serviceImplClass.newInstance();
            service.setPrivateKey(System.getProperty("PRIVATE_KEY"));
            service.setSellerId(System.getProperty("ACCOUNT_ID"));
        } catch (Exception e) {
            logUnexpectedError("DefaultApiVerticle constructor", e);
            throw new RuntimeException(e);
        }

        // Add default ones
        allowedHost.add("localhost");
        allowedHost.add("127.0.0.1");

        String envAllowedHost = System.getProperty("ALLOWED_HOST");
        if (envAllowedHost != null) {
            String[] envAllowedIp = envAllowedHost.split(",");
            for (String ip : envAllowedIp) {
                allowedHost.add(ip);
            }
        }
    }

    private static boolean validateRequestUrl(Message message) {
        String url = message.headers().get("Host");
        if (url == null) {
            LOGGER.error("Remote host is empty.");
            return false;
        }

        String[] items = url.split(":");
        String ip = items[0];

        if (allowedHost.contains(ip))
            return true;
        else {
            LOGGER.error("This ip is not in allowed list:" + ip);
            return false;
        }
    }

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject>consumer(CANCELALL_POST_SERVICE_ID).handler(
                new Handler<Message<JsonObject>>() {
                    @Override
                    public void handle(final Message<JsonObject> message) {
                        try {
                            if (!validateRequestUrl(message)) return;

                            CancelAllRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CancelAllRequest.class);
                            service.cancelAllPost(request, new Handler<AsyncResult<Object>>() {
                                @Override
                                public void handle(AsyncResult<Object> result) {
                                    if (result.succeeded())
                                        message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
                                    else {
                                        Throwable cause = result.cause();
                                        manageError(message, cause, "cancelAll.post");
                                    }
                                }
                            });
                        } catch (Exception e) {
                            logUnexpectedError("cancelAll.post", e);
                            message.fail(DefaultApiException.Default_cancelAllPost_400_Exception.getStatusCode(), DefaultApiException.Default_cancelAllPost_400_Exception.getStatusMessage());
                        }
                    }
                });

        //Consumer for cancelAll.post
//        vertx.eventBus().<JsonObject> consumer(CANCELALL_POST_SERVICE_ID).handler(message -> {
//            try {
//                if (!validateRequestUrl(message)) return;
//
//                CancelAllRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CancelAllRequest.class);
//                service.cancelAllPost(request, result -> {
//                    if (result.succeeded())
//                        message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
//                    else {
//                        Throwable cause = result.cause();
//                        manageError(message, cause, "cancelAll.post");
//                    }
//                });
//            } catch (Exception e) {
//                logUnexpectedError("cancelAll.post", e);
//                message.fail(DefaultApiException.Default_cancelAllPost_400_Exception.getStatusCode(), DefaultApiException.Default_cancelAllPost_400_Exception.getStatusMessage());
//            }
//        });

        //Consumer for cancelOrder.post
        vertx.eventBus().<JsonObject>consumer(CANCELORDER_POST_SERVICE_ID).handler(new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> message) {
                try {
                    if (!validateRequestUrl(message)) return;

                    CancelOrderRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), CancelOrderRequest.class);
                    service.cancelOrderPost(request, new Handler<AsyncResult<Object>>() {
                        @Override
                        public void handle(AsyncResult<Object> result) {
                            if (result.succeeded())
                                message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
                            else {
                                Throwable cause = result.cause();
                                manageError(message, cause, "cancelOrder.post");
                            }
                        }
                    });
                } catch (Exception e) {
                    logUnexpectedError("cancelOrder.post", e);
                    message.fail(DefaultApiException.Default_cancelOrderPost_400_Exception.getStatusCode(), DefaultApiException.Default_cancelOrderPost_400_Exception.getStatusMessage());
                }
            }
        });

        //Consumer for newOrder.post
        vertx.eventBus().<JsonObject>consumer(NEWORDER_POST_SERVICE_ID).handler(new Handler<Message<JsonObject>>() {
            @Override
            public void handle(Message<JsonObject> message) {
                try {
                    if (!validateRequestUrl(message)) return;
                    NewOrderRequest request = Json.mapper.readValue(message.body().getJsonObject("request").encode(), NewOrderRequest.class);
                    service.newOrderPost(request, new Handler<AsyncResult<Object>>() {
                        @Override
                        public void handle(AsyncResult<Object> result) {
                            if (result.succeeded())
                                message.reply(new JsonObject(Json.encode(result.result())).encodePrettily());
                            else {
                                Throwable cause = result.cause();
                                manageError(message, cause, "newOrder.post");
                            }
                        }
                    });
                } catch (Exception e) {
                    logUnexpectedError("newOrder.post", e);
                    message.fail(DefaultApiException.Default_newOrderPost_400_Exception.getStatusCode(), DefaultApiException.Default_newOrderPost_400_Exception.getStatusMessage());
                }
            }
        });


        vertx.eventBus().<String>consumer(Constants.TOPICS.INIT_REF_DATA, this::setRefData);
    }

    private void setRefData(Message<String> stringMessage) {
        service.setRefData(stringMessage.body());
    }

    private void manageError(Message<JsonObject> message, Throwable cause, String serviceName) {
        int code = SignerApiException.INTERNAL_SERVER_ERROR.getStatusCode();
        String statusMessage = SignerApiException.INTERNAL_SERVER_ERROR.getStatusMessage();
        if (cause instanceof SignerApiException) {
            code = ((SignerApiException) cause).getStatusCode();
            statusMessage = ((SignerApiException) cause).getStatusMessage();
        } else {
            logUnexpectedError(serviceName, cause);
        }

        message.fail(code, statusMessage);
    }

    private void logUnexpectedError(String serviceName, Throwable cause) {
        LOGGER.error("Unexpected error in " + serviceName, cause);
    }
}
