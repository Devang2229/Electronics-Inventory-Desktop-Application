/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import models.Electronics;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class PurchaseOrderItemViewController implements Initializable, ControllerInterface {

     @FXML   private DatePicker datePicker;
    @FXML    private Spinner itemQuantitySpinner;
    @FXML    private Label userIDLabel;
    @FXML    private Label manufacturereNameLabel;
    @FXML    private Label itemNameLabel;
    @FXML    private Label errMsgLabel;
    @FXML    private Button backButton;

    private Electronics electronics;
    
    
    
    
      /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,900,300);
        itemQuantitySpinner.setValueFactory(valueFactory);
        
      
    }    

    
    
    
     /**
     * This method will read/validate the inputs and store the information
     * in the hoursWorked table
     */
    public void saveButtonPushed(ActionEvent event)
    {
        try{
            electronics.purchaseItem(datePicker.getValue(), (int) itemQuantitySpinner.getValue());           
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            errMsgLabel.setText(e.getMessage());
        }
    }
    
    
     /**
     * This will return the user to the table of all volunteers
     */
    public void cancelButttonPushed(ActionEvent event) throws IOException
    {
        //if this is an admin user, go back to the table of volunteers
        SceneChangingUtility sc = new SceneChangingUtility();
     
        sc.changeScenes(event, "Electronics.fxml", "All Electronics");
            
    }

    @Override
    public void preloadData(Electronics electronics) {
         this.electronics = electronics;
        
        userIDLabel.setText(Integer.toString(electronics.getUserId()));
        manufacturereNameLabel.setText(electronics.getManufacturerName());
        itemNameLabel.setText(electronics.getItemName());
        datePicker.setValue(LocalDate.now());
        errMsgLabel.setText("");
    }
}
