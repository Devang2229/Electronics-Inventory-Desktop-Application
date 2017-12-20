package view;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Electronics;

import models.Users;


/**
 *
 * @author janid
 */
public class SceneChangingUtility {
    
    private static Users loggedInUser;
 

 
   
    public void changeScenes(ActionEvent event, String viewName, String title) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        
        Scene scene = new Scene(parent);
        
       
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void changeScenes(ActionEvent event, String viewName, String title, Electronics electronics, ControllerInterface controllerClass) throws IOException  
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        
        Scene scene = new Scene(parent);
        
  
        controllerClass = loader.getController();
        controllerClass.preloadData(electronics);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public void changeScenes(ActionEvent event, String viewName, String title, Users users, ControllerClass controllerClass) throws IOException  
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        
        Scene scene = new Scene(parent);
        
  
        controllerClass = loader.getController();
        controllerClass.preloadData(users);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
     public static Users getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(Users loggedInUser) {
        SceneChangingUtility.loggedInUser = loggedInUser;
    }
   
  
       

  
    
    
}
