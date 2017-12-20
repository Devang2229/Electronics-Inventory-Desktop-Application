package models;


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
                            String itemName, int itemQuantity, String manufacturerName, 
                            double sellingPrice, double buyingPrice, String model, String color)
                               
    
    {
        super(itemName, itemQuantity, manufacturerName, sellingPrice , buyingPrice,model , color);

        setItemsSold(itemsSold);
        setItemsLeft(itemsLeft);
        setPurchaseOrder(purchaseOrder);

    }//end of the constructor 

    /**
     * This is the setter method for the items sold which validates that greater than 0 and less than total quantitty.
     * @param itemsSold 
     */
    public void setItemsSold(int itemsSold)
    {
      if(itemsSold > 0 && itemsSold < getItemQuantity() )
          this.itemsSold = itemsSold;
      else 
          throw new IllegalArgumentException("Please enter the Items between 0 and total quantity ");
    }
    
    /**
     * This is the setter method for the items left and validates that the items left is subtraction of total quantity and items sold
     * @param itemsLeft 
     */
    public void setItemsLeft(int itemsLeft) 
    {
            itemsLeft = (getItemQuantity() - itemsSold);
            this.itemsLeft = itemsLeft;
    }
    
    /**
     * This is the setter method the purchase order where it validates that the items sold is greater than 100 and less than 20
     * @param purchaseOrder 
     */
    public void setPurchaseOrder(int purchaseOrder) {
 
        if(itemsSold > 100 && itemsLeft < 20)
            this.purchaseOrder = purchaseOrder;
        else 
            throw new IllegalArgumentException("Please dont purchase the order as we are not short of it.");
    }
    
    /**
     * This is the getter method for items sold
     * @return 
     */
    public int getItemsSold() {
        return itemsSold;
    }
    
    /**
     * This is the getter method for ItemsLeft
     * @return 
     */
    public int getItemsLeft() {
        return itemsLeft;
    }
    
     /**
     * This is the getter method for purchase order
     * @return 
     */
    public int getPurchaseOrder() {
        return purchaseOrder;
    }

}
