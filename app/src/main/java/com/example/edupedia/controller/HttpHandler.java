package com.example.edupedia.controller;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class HttpHandler extends AsyncTask<String, Void, String> {
    private static final String TAG = HttpHandler.class.getSimpleName();
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
    @Override
    protected String doInBackground(String... params) {
        String response = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.e("platessuck", conn.getResponseMessage());
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Log.e("Adidas", "50%");
            }
            Log.e("i wanna buy boost", String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK)
                Log.e("RESPONSE_OK?", String.valueOf(responseCode));
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Log.e("gugle", "YAY");
            response = convertStreamToString(in);
            Log.e("coolies", response);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

