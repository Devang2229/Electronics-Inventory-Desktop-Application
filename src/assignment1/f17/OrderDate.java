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
   
    private int purchaseOrder;
    
       
    
    public OrderDate(int itemsSold, int itemsLeft, int purchaseOrder,
  String itemName, int itemQuantity, String manufacturerName, double sellingPrice, double buyingPrice, String model, String color)
    
    
    {
        super(itemName, itemQuantity, manufacturerName, sellingPrice , buyingPrice,model , color);
    
    setItemsSold(itemsSold);
    setItemsLeft(itemsLeft);
    setPurchaseOrder(purchaseOrder);
    
   
  
    }//end of the constructor 

  
    public void setItemsSold(int itemsSold)
    {
      if(itemsSold > 0 && itemsSold < getItemQuantity() )
          this.itemsSold = itemsSold;
      else 
          throw new IllegalArgumentException("Please enter the Items between 0 and total quantity ");
    }

    public void setItemsLeft(int itemsLeft) 
    {
            itemsLeft = (getItemQuantity() - itemsSold);
            this.itemsLeft = itemsLeft;
    }

    public void setPurchaseOrder(int purchaseOrder) {
 
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
    public int getPurchaseOrder() {
        return purchaseOrder;
    }


    
}
