package com.example.pitectors.pitection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.*;

/**
 * Created by Rob on 9/25/2015.
 */
public class HttpManager {
    public static String getData(String uri) {

        BufferedReader rd = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n");
            }
			JsonParser parse = new JsonParser();


            return sb.toString();
        } catch (Exception e) {

        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (Exception e) {

                }
            }
        }


        return uri;
    }
}
