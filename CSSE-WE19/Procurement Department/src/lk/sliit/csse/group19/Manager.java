/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.sliit.csse.group19;

/**
 *
 * @author Muhammed Suhail
 */
public class Manager extends AuthorizedEmployee{
    
//    PurchaseOrder(int purchaseID, String siteManager, String purchaseStatus, int supplierID
    public String setPurchaseOrderApprove(int purchaseOrderId, String managerId, int supplierId, String status){
        return new PurchaseOrder(purchaseOrderId, managerId, status, supplierId).setApproveOrder();
    }
    
    public String addNewSuppiler(){
        return null;
    }
    
    public String addNewSite(){
        return null;
    }
    
    public String addNewItem(){
        return null;
    }
}
