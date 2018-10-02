/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    
    public JSONArray getValues(){
        
        String line = "";
        JSONArray arr = null;
        try {
            URL url = new URL(this.URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if(conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + Integer.toString(conn.getResponseCode()));
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            System.out.println("Output from server");
            response.append("{results:");
            while((line = br.readLine()) != null) {
                response.append(line);
            }
            response.append("}");
            conn.disconnect();
//            System.out.println(response.toString());
            JSONObject obj = new JSONObject(response.toString());
//            System.out.println(obj.getJSONArray("results"));
            arr = obj.getJSONArray("results");
            
        } catch(JSONException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return arr;
    }
    
    public void postValue(JSONObject data) {
        try{
        URL url = new URL(this.URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        
        System.out.println("object POST ");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateValue(JSONObject data){
        try{
        URL url = new URL(this.URL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("UPDATE");

        System.out.println("Object UPDATE");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
