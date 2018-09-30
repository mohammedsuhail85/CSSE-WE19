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
public class Item {
    
    private int itemId;
    private String decription;
    private float price;
    private Policy itemPolicy;
    private int quantity;

    public Item(int itemId, String decription, float price, Policy itemPolicy, int quantity) {
        this.itemId = itemId;
        this.decription = decription;
        this.price = price;
        this.itemPolicy = itemPolicy;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Policy getItemPolicy() {
        return itemPolicy;
    }

    public void setItemPolicy(Policy itemPolicy) {
        this.itemPolicy = itemPolicy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
