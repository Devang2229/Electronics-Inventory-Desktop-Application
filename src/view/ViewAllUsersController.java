/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Electronics;
import models.Users;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class ViewAllUsersController implements Initializable {

       @FXML
    private TableView<Users> UserTable;

   

    @FXML
    private TableColumn<Users, String> firstName;

    @FXML
    private TableColumn<Users, String> lastName;

    @FXML
    private TableColumn<Users, Integer> phoneNumber;

    @FXML
    private TableColumn<Users, String> emailAddress;
    
    
    @FXML private Button editDeviceButton;
   

    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        editDeviceButton.setDisable(true);
       
        firstName.setCellValueFactory(new PropertyValueFactory<Users, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Users, String>("lastName"));
         phoneNumber.setCellValueFactory(new PropertyValueFactory<Users, Integer>("phoneNumber"));
        emailAddress.setCellValueFactory(new PropertyValueFactory<Users, String>("emailAddress"));
        
        try{
            loadUsers();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
       
    }    
    
    /**
     * This method will load the electronics from the database and load them into 
     * the TableView object
     */
    public void loadUsers() throws SQLException
    {
        
        ObservableList<Users> users = FXCollections.observableArrayList();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            //2.  create a statement object
            statement = conn.createStatement();
            
            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM Users");
            
            //4.  create electronics objects from each record
            while (resultSet.next())
            {
                Users newusers = new Users(resultSet.getString("firstName"),
                                                       resultSet.getString("lastName"),
                                                       resultSet.getString("emailAddress"),
                                                       resultSet.getString("phoneNumber"),
                                                        resultSet.getString("password"),
                                                        resultSet.getBoolean("admin"));
                 
                users.add(newusers);
            }
            
            UserTable.getItems().addAll(users);
            
        } catch (Exception e)
        {
            System.err.println(e);
        }
        finally
        {
            if (conn != null)
                conn.close();
            if(statement != null)
                statement.close();
            if(resultSet != null)
                resultSet.close();
        }
    
    }
    
    
    /**
     * This method will switch to the NewUserView scene when the button is pushed
     */
    public void newUserButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "NewUserView.fxml", "Create New User");
    }
    
       /*
      It allows the user to log out of the system
      */
        public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility.setLoggedInUser(null);
        SceneChangingUtility sc= new SceneChangingUtility();
        sc.changeScenes(event, "LogInView.fxml", "LogIn");
        
    }
        
         /**
     *The button gets enabled when the any of the device is selected
     */
    public void UsersSelected()
    {
        editDeviceButton.setDisable(false);
        
    }
    
    
    /**
     * If the edit button is pushed, pass the selected users to the NewUserView 
     * and pre load it with the data
     */
    public void editButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        Users users = this.UserTable.getSelectionModel().getSelectedItem();
        NewUserViewController npvc = new NewUserViewController();
        
        sc.changeScenes(event, "NewUserView.fxml", "Edit Volunteer", users, npvc );
    }
    
 
    
      /*
    This method takes the user to the admin portal scene.
    */
      public void backButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "AdminPortalView.fxml", "Admin Portal");
    }
}
