/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import org.json.JSONObject;

/**
 *
 * @author Muhammed Suhail
 */
public class Site {
    
    private int siteID;
    private String siteName;
    private String siteAddress;
    private String siteManager;

    public Site(int siteID, String siteName, String siteAddress, String siteManager) {
        this.siteID = siteID;
        this.siteName = siteName;
        this.siteAddress = siteAddress;
        this.siteManager = siteManager;
    }

    public Site(String siteName, String siteAddress, String siteManager) {
        this.siteName = siteName;
        this.siteAddress = siteAddress;
        this.siteManager = siteManager;
    }
    
    public int getSiteID() {
        return siteID;
    }

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getSiteManager() {
        return siteManager;
    }

    public void setSiteManager(String siteManager) {
        this.siteManager = siteManager;
    }
 
    public void addNewSite(){
        JSONObject obj = new JSONObject();
        obj.put("siteName", this.siteName);
        obj.put("siteAddress", this.siteAddress);
        obj.put("siteAddress", this.siteManager);
        
        new API("").postValue(obj);
    }
    
    public void updateSiteDetails(){
        JSONObject obj = new JSONObject();
        obj.put("siteName", this.siteName);
        obj.put("siteAddress", this.siteAddress);
        obj.put("siteAddress", this.siteManager);
        
        new API(""+Integer.toString(this.siteID)).updateValue(obj);
    }
    
}
