/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

import java.util.Date;

/**
 *
 * @author Muhammed Suhail
 */
public class GoodRecipt {
    
    private int reciptID;
    private int purchaseID;
    private int supplierID;
    private String comment;
    private Date issueDate;

    public GoodRecipt(int reciptID, int purchaseID, int supplierID, String comment, Date issueDate) {
        this.reciptID = reciptID;
        this.purchaseID = purchaseID;
        this.supplierID = supplierID;
        this.comment = comment;
        this.issueDate = issueDate;
    }

    public int getReciptID() {
        return reciptID;
    }

    public void setReciptID(int reciptID) {
        this.reciptID = reciptID;
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(int purchaseID) {
        this.purchaseID = purchaseID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    
    
}
