package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class AdminPortalViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    /**
     * This is the method when the user pushes the button the new user view is open and directed.
     * @param event
     * @throws IOException 
     */
    public void createNewUserButtonPushed(ActionEvent event) throws IOException
    {
          SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "NewUserView.fxml", "Login In");  
    }
    
    /**
     * This is the method when user pushes the button the inventory view is open  and directed.
     * @param event
     * @throws IOException 
     */
    public void InentorySalesButtonPushed(ActionEvent event) throws IOException
    {
          SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "Inventory.fxml", "Login In");  
    }
        
    /**
     * This is the method when the user pushes the button view all the users view is open and directed.
     * @param event
     * @throws IOException 
     */   
    public void ViewAllUsersButtonPushed(ActionEvent event) throws IOException
    {
          SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "ViewAllUsers.fxml", "All Users Information");  
    }
        
        
    /*
    This method takes the user to the report view scene and displays the bar graph
    */
      public void monthlyHoursButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "MonthlyPurchaseReportView.fxml", "View Purchase Order Report");
    }
}
