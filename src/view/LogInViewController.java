
package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Electronics;
import models.PasswordGenerator;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class LogInViewController implements Initializable {

     @FXML private TextField userIDTextField;
    @FXML private PasswordField pwField;
    @FXML private Label errMsgLabel;
    
    public void loginButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException
    {
       
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        
       
        int userID = Integer.parseInt(userIDTextField.getText());
        
        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2.  create a query string with ? used instead of the values given by the user
            String sql = "SELECT * FROM electronics WHERE UserID = ?";
            
            //3.  prepare the statement
            ps = conn.prepareStatement(sql);
            
            //4.  bind the userID to the ?
            ps.setInt(1, userID);
            
            //5. execute the query
            resultSet = ps.executeQuery();
            
            //6.  extract the password and salt from the resultSet
            String dbPassword = null;
            byte[] salt = null;
            boolean admin = false;
            Electronics electronics = null;
           
            
            while (resultSet.next())
            {
                dbPassword = resultSet.getString("password");
                
                Blob blob = resultSet.getBlob("salt");
                
                //convert into a byte array
                int blobLength = (int) blob.length();
                salt = blob.getBytes(1, blobLength);
                admin = resultSet.getBoolean("admin");
                
                
                electronics = new Electronics(resultSet.getString("itemName"),
                                                       resultSet.getInt("itemQuantity"),
                                                       resultSet.getString("manufacturerName"),
                                                       resultSet.getDouble("retailPrice"),
                                                       resultSet.getDouble("customerPrice"),
                                                       resultSet.getString("model"),
                                                       resultSet.getString("color"),
                                                       resultSet.getString("password"),
                                                       resultSet.getBoolean("admin"));
                electronics.setUserId(resultSet.getInt("UserId"));
                electronics.setImageFile(new File (resultSet.getString("imageFile")));
           
            }
            
            //convert the password given by the user into an encryted password
            //using the salt from the database
            String userPW = PasswordGenerator.getSHA512Password(pwField.getText(), salt);
            
            SceneChangingUtility sc = new SceneChangingUtility();
            
            if(userPW.isEmpty())
                throw new IllegalArgumentException("PLease enter the password.");
            
            
            
             if (userPW.equals(dbPassword))
                SceneChangingUtility.setLoggedInUser(electronics);
         
            
            //if the passwords match - change to the Inventory
            if (userPW.equals(dbPassword) && admin)
                sc.changeScenes(event, "Inventory.fxml", "All Electronics");
            else if(userPW.equals(dbPassword))
            {
                
                
                PurchaseOrderItemViewController controllerClass = new PurchaseOrderItemViewController();
                
                
                sc.changeScenes(event, "PurchaseOrderItemView.fxml", "Purchase Order", electronics, controllerClass);
            }
            else
                //if the do not match, update the error message
                errMsgLabel.setText("The UserID and password do not match");
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        
    }
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errMsgLabel.setText("");
    }      
    
}
