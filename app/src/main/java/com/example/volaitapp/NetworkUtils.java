package com.example.volaitapp;

import android.net.Uri;
import android.os.Bundle;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class NetworkUtils {
    private static final String URL_API = "https://largeashbox33.conveyor.cloud/api/Arte";
    private static HttpURLConnection connec;
    private static BufferedReader reader;
    private static String artJSON;
    static String url = null;

    static HttpURLConnection startConnec(String methName, String parName, String parVal, String httpMeth) {
        HttpURLConnection connec = null;
        try {
            Uri buildURI = Uri.parse(URL_API).buildUpon()
                    .appendPath(methName)
                    .build();
            if (parName != null) {
                buildURI = Uri.parse(String.valueOf(buildURI)).buildUpon()
                        .appendQueryParameter(parName, parVal)
                        .build();
            }
            URL requestUrl = new URL(buildURI.toString());
            url = buildURI.toString();
            connec = (HttpURLConnection) requestUrl.openConnection();
            connec.setRequestMethod(httpMeth);
        } catch (Exception ex) {
        }
        return connec;
    }

    static Bundle getArtsByName(String name){
        Bundle artBundle = new Bundle();

        try {
            connec = NetworkUtils.startConnec("getCupomByID", "name", name, "GET");
            InputStream inputStream = connec.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("/n");
            }
            if (builder.length() == 0) {
                return null;
            }
            artJSON = builder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            connec.disconnect();
        }
        artBundle.putString("artData", artJSON);
        artBundle.putBoolean("isArray", true);
        return artBundle;
    }
}
