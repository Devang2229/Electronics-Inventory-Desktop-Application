package models;

import models.Inventory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.Period;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author janid
 */
public class Electronics extends Inventory
{
    
    private String productKey;
    private String isinCode;
    private File imageFile;
    private String description;
   
   //private Image image;
   
    public Electronics( String itemName, int itemQuantity, String manufacturerName, double retailPrice, 
                        double customerPrice, String model, String color, String password, boolean admin) throws NoSuchAlgorithmException
    {
        super(itemName, itemQuantity, manufacturerName, retailPrice, customerPrice, model, color, password, admin);
        setImageFile(new File("./src/images/Electronics icon.png"));
        salt = PasswordGenerator.getSalt();
        this.password = PasswordGenerator.getSHA512Password(password, salt);
        this.admin = admin;
      
    }
    
    
   
    public Electronics(String itemName, int itemQuantity, String manufacturerName, double retailPrice, double customerPrice,String model, String color, File imageFile,String password, boolean admin) throws IOException, NoSuchAlgorithmException
    {
        super(itemName, itemQuantity, manufacturerName, retailPrice, customerPrice, model, color, password, admin);
        setImageFile(imageFile);
        copyImageFile();
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
    
       public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
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
    
    /**
     * This method will copy the file specified to the images directory on this server and give it 
     * a unique name
     */
    public void copyImageFile() throws IOException
    {
        //create a new Path to copy the image into a local directory
        Path sourcePath = imageFile.toPath();
        
        String uniqueFileName = getUniqueFileName(imageFile.getName());
        
        Path targetPath = Paths.get("./src/image/"+ uniqueFileName);
        
        //copy the file to the new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        //update the imageFile to point to the new File
        imageFile = new File(targetPath.toString());
    }
    
    public String getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    
    /**
     * This method will receive a String that represents a file name and return a
     * String with a random, unique set of letters prefixed to it
     */
    private String getUniqueFileName(String oldFileName)
    {
        String newName;
        
        //create a Random Number Generator
        SecureRandom rng = new SecureRandom();
        
        //loop until we have a unique file name
        do
        {
            newName = "";
            
            //generate 32 random characters
            for (int count=1; count <=32; count++)
            {
                int nextChar;
                
                do
                {
                    nextChar = rng.nextInt(123);
                } while(!validCharacterValue(nextChar));
                
                newName = String.format("%s%c", newName, nextChar);
            }
            newName += oldFileName;
            
        } while (!uniqueFileInDirectory(newName));
        
        return newName;
    }
    
    /**
     * This method will search the images directory and ensure that the file name
     * is unique
     */
    public boolean uniqueFileInDirectory(String fileName)
    {
        File directory = new File("./src/image/");
        
        File[] dir_contents = directory.listFiles();
                
        for (File file: dir_contents)
        {
            if (file.getName().equals(fileName))
                return false;
        }
        return true;
    }
    
    /**
     * This method will validate if the integer given corresponds to a valid
     * ASCII character that could be used in a file name
     */
    public boolean validCharacterValue(int asciiValue)
    {
        
        //0-9 = ASCII range 48 to 57
        if (asciiValue >= 48 && asciiValue <= 57)
            return true;
        
        //A-Z = ASCII range 65 to 90
        if (asciiValue >= 65 && asciiValue <= 90)
            return true;
        
        //a-z = ASCII range 97 to 122
        if (asciiValue >= 97 && asciiValue <= 122)
            return true;
        
        return false;
    }
    
    
     /**
     * This method will write the instance of the electronics into the database
     */
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO Electronics (itemName, manufacturerName, itemQuantity,color, model, retailPrice , customerPrice, imageFile, password, salt, admin)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?, ?)";
                    
            //3. prepare the query
            preparedStatement = conn.prepareStatement(sql);
            
            
                   
            //5. Bind the values to the parameters
            preparedStatement.setString(1, itemName.get());
            preparedStatement.setString(2, manufacturerName.get());
            preparedStatement.setInt(3, itemQuantity.get());
            preparedStatement.setString(4, color.get());
            preparedStatement.setString(5, model.get());
            preparedStatement.setDouble(6, retailPrice.get());
            preparedStatement.setDouble(7, customerPrice.get());
            preparedStatement.setString(8, imageFile.getName());
            preparedStatement.setString(9, password);
            preparedStatement.setBlob(10, new javax.sql.rowset.serial.SerialBlob(salt));
            preparedStatement.setBoolean(11, admin);
            
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (preparedStatement != null)
                preparedStatement.close();
            
            if (conn != null)
                conn.close();
        }
    }
    
    
    public void updateElectrornicsInDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE electronics SET itemName = ?, manufacturerName =?, itemQuantity =?,color =?, model=?, retailPrice =? , customerPrice =?, imageFile= ? ";
                    
            
            //3. prepare the query against SQL injection
            preparedStatement = conn.prepareStatement(sql);
            
            //5. Bind the values to the parameters
            preparedStatement.setString(1, itemName.get());
            preparedStatement.setString(2, manufacturerName.get());
            preparedStatement.setInt(3, itemQuantity.get());
            preparedStatement.setString(4, color.get());
            preparedStatement.setString(5, model.get());
            preparedStatement.setDouble(6, retailPrice.get());
            preparedStatement.setDouble(7, customerPrice.get());
            preparedStatement.setString(8, imageFile.getName());
            
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
          
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (conn != null)
                conn.close();
            if (preparedStatement != null)
                preparedStatement.close();
        }
        
    }
}
