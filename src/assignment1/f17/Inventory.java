package assignment1.f17;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author janidevang
 */
public class Inventory {
      
    private static int inventoryId = 1000001;
    private SimpleStringProperty itemName, manufacturerName, model, color ;
    protected SimpleIntegerProperty itemQuantity;
    private SimpleDoubleProperty retailPrice ,customerPrice;
   
    /*
    This is constructor for the inventory class.
    */
    public Inventory(String itemName, int itemQuantity, String manufacturerName, double retailPrice, double customerPrice, String model, String color)
    {
       inventoryId++;
        setItemName(itemName);
        setItemQuantity(itemQuantity);
        setManufacturerName(manufacturerName);
        setRetailPrice(retailPrice);
        setCustomerPrice(customerPrice);
        setModel(model);
        setColor(color);
    
    }//end of the constructor

    /*
    This is the get method for itemName.
    */
    public String getItemName()
    {
        return itemName.get();
    }

    /*
    This is the set method for itemName.
    */
    public void setItemName(String itemName)
    {
        if(itemName.matches("Dell Laptops") ||itemName.matches("Iphone 8") ||itemName.matches("Iphone 7") || itemName.matches("ASUS Laptop")  || itemName.matches("Samung s7") || itemName.matches("Samung s8") 
               || !itemName.isEmpty())
             this.itemName = new SimpleStringProperty(itemName);
        else
            throw new IllegalArgumentException("Item name must start with an valid name");
                                                   
    }

    /*
    ThiS is get method for itemQuantity.
    */
    public int getItemQuantity() 
    {
        return itemQuantity.get();
    }

    
    public void setItemQuantity(int itemQuantity) 
    {
       
        if(itemQuantity >  0 && itemQuantity < 900)
            this.itemQuantity = new SimpleIntegerProperty(itemQuantity);       
        else
           throw new IllegalArgumentException("The item quantity must be in the range of 0 - 900");
    }

    public String getManufacturerName() 
    {
        return manufacturerName.get();
    }

    public void setManufacturerName(String manufacturerName)
    {
        
        if(!manufacturerName.isEmpty())
       //if (manufacturerName.matches("[A-Z][a-zA-Z]*"))
            this.manufacturerName = new SimpleStringProperty(manufacturerName);
        else
           throw new IllegalArgumentException("Manufacturer name must start with an upper"
                                            + "case letter and only contain letters");
    }

    public double getCustomerPrice()
    {
        return customerPrice.get();
    }

    public void setCustomerPrice(double sellingPrice)
    {
         if(sellingPrice > 0 && sellingPrice < 40000)
            this.customerPrice = new SimpleDoubleProperty(sellingPrice);
        else 
            throw new IllegalArgumentException("Please enter selling price range 0 to 40000.");
    }

    public double getRetailPrice()
    {
        return retailPrice.get();
    }

    public void setRetailPrice(double buyingPrice) 
    {
        if(buyingPrice > 0 && buyingPrice < 40000)
             this.retailPrice = new SimpleDoubleProperty(buyingPrice);
        else 
            throw new IllegalArgumentException("Please enter the buying price range 0 to 4000.");
    }

    public String getModel()
    {
        return model.get();
    }

    public void setModel(String model) 
    {
        if(!model.isEmpty())
       //if(model.matches("[ABCEGHJKLMNPRSTVXY][0-9][ABCEGHJKLMNPRSTVWXYZ][0-9][ABCDEGHJKLMNPRSTVWXYZ][0-9]"))
           this.model = new SimpleStringProperty(model);
       else 
          throw new IllegalArgumentException("Please enter the model as Letter and Number alternately six spaces.");
    }

    public String getColor() 
    {
        return color.get();
    }

    public void setColor(String color) 
    {
        if(color.matches("Black") || color.matches("White") || color.matches("Silver") || color.matches("Grey") || color.matches("Metallic Silver") && !color.isEmpty())
        {
            this.color = new SimpleStringProperty(color);
            
        }
        else 
            throw new IllegalArgumentException("Please eneter the following colors: Black , White or Silver.");
    }
}
