/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author janid
 */
public class Electronics extends Inventory{
    
    private String productKey;
    private String isinCode;
    private String description;
    private Image image;
   
    public Electronics(String productKey, String description, String isinCode, String itemName,
                        int itemQuantity, String manufacturerName, double retailPrice, 
                        double customerPrice, String model, String color)
    {
        super(itemName, itemQuantity, manufacturerName, retailPrice, customerPrice, model, color);
        this.image = image;
    }
    
     public Image getImage() {
        return image;
    }


    public Electronics(String itemName, int itemQuantity, String manufacturerName, double retailPrice, double customerPrice, String model, String color)
    {
        super(itemName, itemQuantity, manufacturerName, retailPrice, customerPrice, model, color);
     
        try
        {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/image/Electronics icon.png"));
            image = SwingFXUtils.toFXImage(bufferedImage, null);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public String getProductKey() 
    {
        return productKey;
    }

    public void setProductKey(String productKey)
    {
        
        if(productKey.matches("^[a-zA-Z0-9_]*$"))
            this.productKey = productKey;   
        else 
            throw new IllegalArgumentException("PLease enter the product key only alphanumeric letters.");
   
    }

    public String getIsinCode()
    {
        return isinCode;
    }

    public void setIsinCode(String isinCode) 
    {
        if(isinCode.matches("[A-Z]{2}([A-Z0-9]){10}"))
            this.isinCode = isinCode;
        else 
            throw new IllegalArgumentException("PLease enter 12 letter following the first two alphabets and rest any letters or numbers");
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        if (description.matches("[A-Z][a-zA-Z]") )
             this.description = description;
        else
            throw new IllegalArgumentException("Description must start with an upper"
                                            + "case letter and only contain letters");
    } 
}
