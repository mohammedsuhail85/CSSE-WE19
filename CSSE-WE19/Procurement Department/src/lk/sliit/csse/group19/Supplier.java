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
    
    private int id;
    private String name;
    private String address;
    private int phoneNo;
    private String email;
    private int bankACCNo;

    public Supplier(int supplierID, String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo) {
        this.id = supplierID;
        this.name = supplierName;
        this.address = supplierAddress;
        this.phoneNo = supplierPhoneNo;
        this.email = supplierEmail;
        this.bankACCNo = supplierBankACCNo;
    }

    public Supplier(String supplierName, String supplierAddress, int supplierPhoneNo, String supplierEmail, int supplierBankACCNo) {
        this.name = supplierName;
        this.address = supplierAddress;
        this.phoneNo = supplierPhoneNo;
        this.email = supplierEmail;
        this.bankACCNo = supplierBankACCNo;
    }
    
    public Supplier() {
    }

    public int getSupplierID() {
        return id;
    }

    public void setSupplierID(int supplierID) {
        this.id = supplierID;
    }

    public String getSupplierName() {
        return name;
    }

    public void setSupplierName(String supplierName) {
        this.name = supplierName;
    }

    public String getSupplierAddress() {
        return address;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.address = supplierAddress;
    }

    public int getSupplierPhoneNo() {
        return phoneNo;
    }

    public void setSupplierPhoneNo(int supplierPhoneNo) {
        this.phoneNo = supplierPhoneNo;
    }

    public String getSupplierEmail() {
        return email;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.email = supplierEmail;
    }

    public int getSupplierBankACCNo() {
        return bankACCNo;
    }

    public void setSupplierBankACCNo(int supplierBankACCNo) {
        this.bankACCNo = supplierBankACCNo;
    }
    
        public String addNewSupplier(){
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("address", this.address);
        obj.put("bankAccountNumber", this.bankACCNo);
        obj.put("email", this.email);
        obj.put("phoneNumber", this.phoneNo);
        
        return new API("http://localhost:9000/supplier").postValue(obj);
    }
    
    public String updateSupplierDetails(){
        JSONObject obj = new JSONObject();
        obj.put("name", this.name);
        obj.put("address", this.address);
        obj.put("bankAccountNumber", this.bankACCNo);
        obj.put("email", this.email);
        obj.put("phoneNumber", this.phoneNo);
        
        return new API("http://localhost:9000/supplier/"+Integer.toString(this.id)).updateValue(obj);
    }
}
