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
            //Make a connection with the passed in URI to the method
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            //Create a new stringBuilder variable
            StringBuilder sb = new StringBuilder();
            //Read in the text from the given URI
            rd = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            //While there are items to read in
            //from the buffered reader add each line to the
            //stringbuilder
            while ((line = rd.readLine()) != null) {
                sb.append(line + "\n");
            }

            //Return the built string
            return sb.toString();
        } catch (Exception e) {

        } finally {
            //close the input stream connection
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
