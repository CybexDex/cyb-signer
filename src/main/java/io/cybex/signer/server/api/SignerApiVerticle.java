package io.cybex.signer.server.api;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.phiz71.vertx.swagger.router.OperationIdServiceIdResolver;
import com.github.phiz71.vertx.swagger.router.SwaggerRouter;
import io.cybex.signer.InitOperations;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SignerApiVerticle extends AbstractVerticle {
    final static Logger LOGGER = LoggerFactory.getLogger(SignerApiVerticle.class);

    protected Router router;

    public SignerApiVerticle()
    {
    }

    @Override
    public void init(Vertx vertx, Context context) {
        super.init(vertx, context);
        router = Router.router(vertx);
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Json.mapper.registerModule(new JavaTimeModule());
        FileSystem vertxFileSystem = vertx.fileSystem();
        vertxFileSystem.readFile("swagger.json", readFile -> {
            if (readFile.succeeded()) {
                Swagger swagger = new SwaggerParser().parse(readFile.result().toString(Charset.forName("utf-8")));
                Router swaggerRouter = SwaggerRouter.swaggerRouter(router, swagger, vertx.eventBus(), new OperationIdServiceIdResolver());
            
                deployVerticles(startFuture);
                
                vertx.createHttpServer() 
                    .requestHandler(swaggerRouter::accept) 
                    .listen(Integer.valueOf(System.getProperty("SIGNER_SERVER_PORT")));
                startFuture.complete();
                LOGGER.info("Started server on port: " + System.getProperty("SIGNER_SERVER_PORT"));

            } else {
            	startFuture.fail(readFile.cause());
            }
        });        		        
    }

    public void deployVerticles(Future<Void> startFuture) {

        vertx.deployVerticle("io.cybex.signer.server.api.verticle.DefaultApiVerticle", res -> {
            if (res.succeeded()) {
                LOGGER.info("DefaultApiVerticle : Deployed");
            } else {
                startFuture.fail(res.cause());
                LOGGER.error("DefaultApiVerticle : Deployment failed");
            }
        });
    }

    private static void setEnv() {
        try {
            String envFile = System.getProperty("envFile");
            FileInputStream envFileStream = new FileInputStream(envFile);
            Properties props = new Properties();
            props.load(envFileStream);
            Map m = new HashMap<String, String>();
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                System.setProperty((String)entry.getKey(), (String)entry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        setEnv();
        Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new SignerApiVerticle(), event -> {
            if (event.succeeded()) {
                vertx.deployVerticle(new InitOperations(System.getProperty("API_SERVER_ADDRESS")));
            }
        });
    }
}