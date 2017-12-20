
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Users;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class NewUserViewController implements Initializable, ControllerClass {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailAddressTextField;
    
     @FXML
    private TextField phoneNumberTextField;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;
    
    
    
    @FXML Label errMsgLabel;
    
     private Users users;
     @FXML private Label headerLabel;
     @FXML private CheckBox adminCheckBox;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         errMsgLabel.setText("");    
    }    
   
    
    /**
     * This method will validate that the passwords match
     * 
     */
    public boolean validPassword()
    {
        if (password.getText().length() < 5)
        {
            errMsgLabel.setText("Passwords must be greater than 5 characters in length");
            return false;
        }
        
        if (password.getText().equals(confirmPassword.getText()))
            return true;
        else
            return false;
    }
    
    
    public void validTextFields()
    {
       
            if(firstNameTextField.getText() == null && lastNameTextField.getText() ==null)
            {
               errMsgLabel.setText("Please provide First and Last Name");
                
            }
            if(emailAddressTextField.getText() == null)
            {
                 errMsgLabel.setText("Please enter the correct email address.");
               
                
            }
       
        }
  
    
    public void cancelButtonPushed(ActionEvent event) throws IOException
        {
          SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "LogInView.fxml", "Login In");  
        }
    
    
    
    
    /**
     * This method will read from the scene and try to create a new instance of a Volunteer.
     * If a volunteer was successfully created, it is updated in the database.
     * @param event
     * @throws java.io.IOException
     */
    public void createUserButtonPushed(ActionEvent event) throws IOException
    {
        if (validPassword())
        {
            try
            {
                
                
                if(users != null)
                {
                    updateUsers();
                    users.updateUsersInDB();
                }
                else 
                {
                       
                    users = new Users(firstNameTextField.getText().toString(),
                                        lastNameTextField.getText().toString(), 
                                        emailAddressTextField.getText().toString(),
                                        (phoneNumberTextField.getText()), 
                                        password.getText(), adminCheckBox.isSelected());

                     errMsgLabel.setText("");    
                     users.insertIntoDB();
                }
            
             
                 SceneChangingUtility sc = new SceneChangingUtility();
                 sc.changeScenes(event, "LogInView.fxml", "Login In");
                
            }
            catch (Exception e)
            {
                errMsgLabel.setText(e.getMessage());
            }
        }
    }

    @Override
    public void preloadData(Users users) {
       this.users = users;
   
       
       this.firstNameTextField.setText(users.getFirstName());
       this.lastNameTextField.setText(users.getLastName());
       this.emailAddressTextField.setText(users.getEmailAddress());
       this.phoneNumberTextField.setText(users.getPhoneNumber());
       this.headerLabel.setText("Edit Volunteer");
      
    }
    
     /**
     * This method will read from the GUI fields and update the volunteer object
     */
    public void updateUsers() throws IOException
    {
        users.setFirstName(firstNameTextField.getText());
        users.setLastName(lastNameTextField.getText());
        users.setEmailAddress(emailAddressTextField.getText());
        users.setPhoneNumber(phoneNumberTextField.getText());
    }
 
    
    
    
}
