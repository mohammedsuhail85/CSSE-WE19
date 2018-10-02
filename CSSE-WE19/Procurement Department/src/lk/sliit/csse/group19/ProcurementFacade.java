/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Muhammed Suhail
 */

/*
*This class provides all other class's contol. 
 */

public class ProcurementFacade {
    
    //API class 
    public JSONArray getValues(String url){
        return new API(url).getValues();
    }
    
    public void postValue(String url, JSONObject data) {
        new API(url).postValue(data);
    }
    
    //Item Add new item and Update
    public void postNewItem(String name, float price, String category) {
        new Item(name, price, category).postItem();
    }
    
    public void setApproveOrder(int purchaseId, String status, String sitemanagerId, int supplierId){
        new PurchaseOrder(purchaseId, sitemanagerId, status, supplierId).setApproveOrder();
    }
    
    //Add new site and Update existing site's details
    public void addNewSite(String siteName, String siteAddress, String siteManager){
        new Site(siteName, siteAddress, siteManager).addNewSite();
    }
    
    public void update(int siteID, String siteName, String siteAddress, String siteManager){
        new Site(siteID, siteName, siteAddress, siteManager).updateSiteDetails();
    }
    
    //Add new Supplier and update existing supplier's detatils
    public void addNewSupplier(String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo){
        new Supplier(supplierName, supplierAddress, supplierPhoneNo, supplierEmail, supplierBankACCNo).addNewSupplier();
    }
    
    public void updateSupplierDetails(int supplierID, String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo){
        new Supplier(supplierID, supplierName, supplierAddress, supplierPhoneNo, supplierEmail, supplierBankACCNo).updateSupplierDetails();
    }
}
