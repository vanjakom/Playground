package com.mungolab.TestInternetAvability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: vanja
 * Date: 4/4/12
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    protected static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        while (true) {
            try {
                long startTimestamp = System.currentTimeMillis();

                URL url = new URL("http://www.google.com");
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(1000);
                connection.setConnectTimeout(1000);

                connection.connect();

                InputStream stream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader bufferedReader = new BufferedReader(reader);
                
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    // nothing
                }

                long deltaInMillis = System.currentTimeMillis() -  startTimestamp;

                if (deltaInMillis > 1000) {
                    logger.info("Time to retrieve: " + deltaInMillis);
                }

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
            } catch (Exception e) {
                logger.error("Unable to query Google");
            }

        }
    }
}
