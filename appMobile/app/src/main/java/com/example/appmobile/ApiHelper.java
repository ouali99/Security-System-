package com.example.appmobile;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper {
    private static final String API_URL = "http://192.168.2.21:5000/route/";
    public static void arm (ApiCallback callback){
        new APITask(callback).execute("arm");

    }
    public static void select (ApiCallback callback){
        new APITask(callback).execute("select");

    }
    public static void deactivate(ApiCallback callback) {
        new APITask(callback).execute("deactivate");
    }
    public static void reset(ApiCallback callback) {
        new APITask(callback).execute("reset");
    }
    private static class APITask extends AsyncTask<String, Void, String> {
        private ApiCallback callback;

        public APITask(ApiCallback callback) {

            this.callback = callback;

        }

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) {
                return "Erreur : Pas de paramètre fourni";
            }

            String endpoint = params[0];
            int statut = 1;

            if (params.length > 1) {
                try {
                    statut = Integer.parseInt(params[1]);
                } catch (NumberFormatException e) {
                    return "Erreur : Valeur du statut invalide";
                }
            }

            String result = "";

            try {
                URL url = new URL(API_URL + endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                // Créez le JSON à envoyer
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("statut", statut);

                // Envoyer la requête
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    result = stringBuilder.toString();
                    reader.close();
                    in.close();
                } else {
                    InputStream errorStream = conn.getErrorStream();
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    StringBuilder errorBuilder = new StringBuilder();
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        errorBuilder.append(errorLine);
                    }
                    result = "Error: " + responseCode + " - " + errorBuilder.toString();
                    errorReader.close();
                }

                conn.disconnect();
            } catch (Exception e) {
                result = "Erreur de requête : " + e.getMessage();
            }
            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            if (callback != null) {
                callback.onResult(result);
            }
        }
    }
    public interface
    ApiCallback {
        void onResult(String result);
    }

}
