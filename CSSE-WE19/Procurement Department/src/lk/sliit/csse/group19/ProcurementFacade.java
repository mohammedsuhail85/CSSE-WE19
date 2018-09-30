/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

/**
 *
 * @author Muhammed Suhail
 */
public class ProcurementFacade {
    
//    public ResultSet getSiteList() {
//        return ;
//    }
    
    public String getValues(String URLe) {
        String output = "";
        try {
            URL url = new URL(URLe);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if(conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + Integer.toString(conn.getResponseCode()));
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            System.out.println("Output from server");
            while((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
            
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return output;
    }
    
//        public String getValues2(String URLe) {
//        String output = "";
//        URL url = new URL(URLe);
//        try(InputStream is = url.openStream();
//            JsonReader rdr = Json.createReader(is)) {
//            
//        } catch(MalformedURLException e){
//            e.printStackTrace();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//        
//        return output;
//    }
}
