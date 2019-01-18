package io.cybex.signer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InitOperations extends AbstractVerticle {
    final static Logger LOGGER = LoggerFactory.getLogger(InitOperations.class);
    final static int INIT_INTERVAL = 7200;  //re-init every 2 hours

    private String host;
    private String urlRefData;

    public InitOperations(String host) {
        this.host = host;
        this.urlRefData = String.format("https://%s/v1/refData", host);
    }

    @Override
    public void start() throws Exception {
        getInit();

        vertx.periodicStream(TimeUnit.SECONDS.toMillis(INIT_INTERVAL)).handler(event -> {
            getInit();
        });
    }

    public void getInit() {
        try {
            URL url = new URL(urlRefData);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            // optional default is GET
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            int responseCode = con.getResponseCode();

            LOGGER.info("Sending 'GET' request to URL : " + urlRefData);
            LOGGER.info("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String refData = response.toString();
            vertx.eventBus().publish(Constants.TOPICS.INIT_REF_DATA, refData);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
