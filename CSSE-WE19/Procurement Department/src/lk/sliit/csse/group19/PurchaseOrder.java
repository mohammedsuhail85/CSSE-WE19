/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author Muhammed Suhail
 */
public class PurchaseOrder {
    
    private int purchaseID;
    private String siteManager;
    private String managerID;
    private String purchaseStatus;
    private int supplierID;
    private String orderComment;
    private Date intialDate;
    private Date ExpectedDate;

    public PurchaseOrder() {
    }

    public PurchaseOrder(int purchaseID, String siteManager, String managerID, String purchaseStatus, int supplierID, String orderComment, Date intialDate, Date ExpectedDate) {
        this.purchaseID = purchaseID;
        this.siteManager = siteManager;
        this.managerID = managerID;
        this.purchaseStatus = purchaseStatus;
        this.supplierID = supplierID;
        this.orderComment = orderComment;
        this.intialDate = intialDate;
        this.ExpectedDate = ExpectedDate;
    }

    public PurchaseOrder(int purchaseID, String siteManager, String purchaseStatus, int supplierID) {
        this.purchaseID = purchaseID;
        this.siteManager = siteManager;
        this.purchaseStatus = purchaseStatus;
        this.supplierID = supplierID;
    }

    
    
    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getSiteManager() {
        return siteManager;
    }

    public void setSiteManager(String siteManager) {
        this.siteManager = siteManager;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = orderComment;
    }

    public Date getIntialDate() {
        return intialDate;
    }

    public void setIntialDate(Date intialDate) {
        this.intialDate = intialDate;
    }

    public Date getExpectedDate() {
        return ExpectedDate;
    }

    public void setExpectedDate(Date ExpectedDate) {
        this.ExpectedDate = ExpectedDate;
    }
    
    public void setApproveOrder(){
        JSONObject order = new JSONObject();
        order.put("purchaseStatus", this.purchaseStatus);
        order.put("managerID", this.siteManager);
        order.put("supplierID", this.supplierID);
        new API(""+Integer.toString(this.purchaseID)).updateValue(order);
    }
    
}
