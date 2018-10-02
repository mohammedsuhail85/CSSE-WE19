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
public class Supplier {
    
    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private int supplierPhoneNo;
    private String supplierEmail;
    private int supplierBankACCNo;

    public Supplier(int supplierID, String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhoneNo = supplierPhoneNo;
        this.supplierEmail = supplierEmail;
        this.supplierBankACCNo = supplierBankACCNo;
    }

    public Supplier(String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo) {
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierPhoneNo = supplierPhoneNo;
        this.supplierEmail = supplierEmail;
        this.supplierBankACCNo = supplierBankACCNo;
    }
    
    public Supplier() {
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public int getSupplierPhoneNo() {
        return supplierPhoneNo;
    }

    public void setSupplierPhoneNo(int supplierPhoneNo) {
        this.supplierPhoneNo = supplierPhoneNo;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public int getSupplierBankACCNo() {
        return supplierBankACCNo;
    }

    public void setSupplierBankACCNo(int supplierBankACCNo) {
        this.supplierBankACCNo = supplierBankACCNo;
    }
    
        public void addNewSupplier(){
        JSONObject obj = new JSONObject();
        obj.put("supplierName", this.supplierName);
        obj.put("supplierAddress", this.supplierAddress);
        obj.put("supplierBankACCNo", this.supplierBankACCNo);
        obj.put("supplierEmail", this.supplierEmail);
        obj.put("supplierPhoneNo", this.supplierPhoneNo);
        
        new API("https://jsonplaceholder.typicode.com/posts/1").postValue(obj);
    }
    
    public void updateSupplierDetails(){
        JSONObject obj = new JSONObject();
        obj.put("supplierName", this.supplierName);
        obj.put("supplierAddress", this.supplierAddress);
        obj.put("supplierBankACCNo", this.supplierBankACCNo);
        obj.put("supplierEmail", this.supplierEmail);
        obj.put("supplierPhoneNo", this.supplierPhoneNo);
        
        new API("https://jsonplaceholder.typicode.com/posts/"+Integer.toString(this.supplierID)).updateValue(obj);
    }
}
