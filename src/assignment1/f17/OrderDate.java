/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;


import java.time.LocalDateTime;
import java.time.Period;

/**
 *
 * @author janid
 */
public class OrderDate extends Inventory {
    
    
   //instance variables 
    private int itemsSold;
    private int itemsLeft;
   
    private String purchaseOrder;
    private double profitValue;
    
       
    
    public OrderDate(int itemsSold, int itemsLeft, String purchaseOrder, String profitValue,
  String itemName, int itemQuantity, String manufacturerName, double sellingPrice, double buyingPrice, String model, String color)
    
    
    {
        super(itemName, itemQuantity, manufacturerName, sellingPrice , buyingPrice,model , color);
    
    setItemsSold(itemsSold);
    setItemsLeft(itemsLeft);
    setPurchaseOrder(purchaseOrder);
    
   
  
    }//end of the constructor 

  
    public void setItemsSold(int itemsSold)
    {
      if(itemsSold > 0 && itemsSold < super.getItemQuantity() )
          this.itemsSold = itemsSold;
      else 
          throw new IllegalArgumentException("Please enter the Items between 0 and total quantity ");
    }

    public void setItemsLeft(int itemsLeft) 
    {
            itemsLeft = super.getItemQuantity() - itemsSold;
            this.itemsLeft = itemsLeft;
    }

    public void setPurchaseOrder(String purchaseOrder) {
 
        if(itemsSold > 100 && itemsLeft < 20)
            this.purchaseOrder = purchaseOrder;
        else 
            throw new IllegalArgumentException("Please dont purchase the order as we are not short of it.");
    }
    
    public int getItemsSold() {
        return itemsSold;
    }

    public int getItemsLeft() {
        return itemsLeft;
    }
    public String getPurchaseOrder() {
        return purchaseOrder;
    }


    
}
