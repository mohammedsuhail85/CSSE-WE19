/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

/**
 *
 * @author Muhammed Suhail
 */
public class API {

    private String URL;
    private StringBuffer response = new StringBuffer();

    public API(String URL) {
        this.URL = URL;
    }

    public JSONArray getValues() {

        String line = "";
        JSONArray arr = null;
        try {
            URL url = new URL(this.URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

//            if (conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + Integer.toString(conn.getResponseCode()));
//            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            System.out.println("Output from server");
            response.append("{results:");
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            response.append("}");
            conn.disconnect();
//            System.out.println(response.toString());
            JSONObject obj = new JSONObject(response.toString());
//            System.out.println(obj.getJSONArray("results"));
            arr = obj.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arr;
    }

    public JSONObject getSingleValue() {

        String line = "";
        JSONObject obj = null;
        try {
            URL url = new URL(this.URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + Integer.toString(conn.getResponseCode()));
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            System.out.println("single Output from server");
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();
            obj = new JSONObject(response.toString());
//            System.out.println(obj.toString());

        } catch (JSONException e) {
//            e.printStackTrace();
        } catch (Exception e) {
//            e.printStackTrace();
        }

        return obj;
    }

    public String postValue(JSONObject data) {
        String out = null;
        try {
            URL url = new URL(this.URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");

            System.out.println("object POST ");
            OutputStream os = conn.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                out = output;
            }

            conn.disconnect();
            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public String updateValue(JSONObject data) {
        String out = null;
        try {
            URL url = new URL(this.URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("PUT");

            System.out.println("object PUT");
            OutputStream os = conn.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                out = output;
            }

            conn.disconnect();
            System.out.println(output);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
