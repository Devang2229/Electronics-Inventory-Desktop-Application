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
import models.Users;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class LogInViewController implements Initializable {

    @FXML private TextField employeeIDTextField;
    @FXML private PasswordField pwField;
    @FXML private Label errMsgLabel;
    
    /**
     * This is the methods where the users pushes the button it retrieves the information from the database and vaidates 
     * if it is similiar it login ins.
     * @param event
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    public void loginButtonPushed(ActionEvent event) throws IOException, NoSuchAlgorithmException
    {
       
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        
        
    
       
        String phone = (employeeIDTextField.getText());
       
         
        if(validFields())
        {
            try{
                //1.  connect to the DB
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");

                //2.  create a query string with ? used instead of the values given by the user
                String sql = "SELECT * FROM Users WHERE phoneNumber = ?";

                //3.  prepare the statement
                ps = conn.prepareStatement(sql);

                //4.  bind the userID to the ?
                ps.setString(1, phone);

                //5. execute the query
                resultSet = ps.executeQuery();

                //6.  extract the password and salt from the resultSet
                String dbPassword = null;
                byte[] salt = null;
                boolean admin = false;
                Users users = null;


                while (resultSet.next())
                {
                    dbPassword = resultSet.getString("password");

                    Blob blob = resultSet.getBlob("salt");

                    //convert into a byte array
                    int blobLength = (int) blob.length();
                    salt = blob.getBytes(1, blobLength);
                    admin = resultSet.getBoolean("admin");

                    users = new Users(
                            
                            resultSet.getString("firstName"),
                                                resultSet.getString("lastName"),
                                                resultSet.getString("emailAddress"),
                                                 resultSet.getString("phoneNumber"),
                                                resultSet.getString("password"),
                                                resultSet.getBoolean("admin")
                                            );

                }

                //convert the password given by the user into an encryted password
                //using the salt from the database
                String userPW = PasswordGenerator.getSHA512Password(pwField.getText(), salt);

                SceneChangingUtility sc = new SceneChangingUtility();



                 if (userPW.equals(dbPassword))
                    SceneChangingUtility.setLoggedInUser(users);


                //if the passwords match - change to the Inventory
                if (userPW.equals(dbPassword) && admin)
                    sc.changeScenes(event, "AdminPortalView.fxml", "Admin Portal");
                else if(userPW.equals(dbPassword))
                {

                         sc.changeScenes(event, "Electronics.fxml", "Create Electronics");
                }
                else
                    //if the do not match, update the error message
                    errMsgLabel.setText("The Employee ID and password do not match");
            }
            catch (SQLException e)
            {
                System.err.println(e.getMessage());
            }

        }
       
        
        
    }
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errMsgLabel.setText("");
    }      
    
    
        /**
         * This is the method where the button is pushed the user is directed to the create new user view and can register themselves.
         * @param event
         * @throws IOException 
         */
        public void CreateNewUserButtonPushed(ActionEvent event) throws IOException
        {
         SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "NewUserView.fxml", "Admin Portal");  
        }
    
        
     /**
      * This is the method where valid it validates the text fields.
      * @return 
      */    
     public boolean validFields()
    {
        if (employeeIDTextField.getText() ==null && pwField.getText() == null)
        {
            errMsgLabel.setText("Please enter phone number and password");
            return false;
        }
        
        if (employeeIDTextField.getText() != null && pwField.getText() != null)
            return true;
        else
            return false;
    }
}
