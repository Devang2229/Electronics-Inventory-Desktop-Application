/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class InventoryController implements Initializable {

     //configure the table
    @FXML private TableView<Electronics> ElectronicsTable;
    @FXML private TableColumn<Electronics, String> name ;
    @FXML private TableColumn<Electronics, Integer> quantity;
    @FXML private TableColumn<Electronics, String> manufacturerName;
    @FXML private TableColumn<Electronics, String> color;
    @FXML private TableColumn<Electronics, Double> sellingPrice;
    @FXML private TableColumn<Electronics, Double> buyingPrice;
    @FXML private TableColumn<Electronics, String> model;
    
    @FXML private Button Sell;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        //set up the columns in the table
        name.setCellValueFactory(new PropertyValueFactory<Electronics, String>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<Electronics, Integer>("itemQuantity"));
        manufacturerName.setCellValueFactory(new PropertyValueFactory<Electronics, String>("manufacturerName"));
        sellingPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("sellingPrice"));
        buyingPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("buyingPrice"));
        model.setCellValueFactory(new PropertyValueFactory<Electronics, String>("model"));
        color.setCellValueFactory(new PropertyValueFactory<Electronics, String>("color"));
        
        //load dummy data
        ElectronicsTable.setItems(getInventoryItems());
       
        
        
        
    }
   
   public void createNewEmployeeButtonPushed(ActionEvent event) throws IOException
    {
        //load a new scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Electronics.fxml"));
        Parent parent = loader.load();
        Scene newEmployeeScene = new Scene(parent);
        
        //access the controller of the newEmployeeScene and send over
        //the current list of employees
        ElectronicsController controller = loader.getController();
        controller.initialData(ElectronicsTable.getItems());
        
        //Get the current "stage" (aka window) 
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //change the scene to the new scene
        stage.setTitle("Create new Electronic Device");
        stage.setScene(newEmployeeScene);
        stage.show();
    }
    
    public void loadElectronics(ObservableList<Electronics> listOfDevice)
    {
        this.ElectronicsTable.setItems(listOfDevice);
    }
     
    /**
     * This method will return an ObservableList of Electronics objects
     */
    public ObservableList<Electronics>  getInventoryItems()
    {
        ObservableList<Electronics> electronics = FXCollections.observableArrayList();
        electronics.add(new Electronics("Dell Inspiron", 56, "Sheetal Enterprise", 1200.25, 1100.36, "L4J6J6", "Black"));
        electronics.add(new Electronics("Samung S8 Plus", 98, "Jassrra Maketing", 23.56, 56.5, "M4N6V4", "Silver"));
        electronics.add(new Electronics("Nokia A8", 25, "Bindu Sales & Co.", 23.56, 56.5, "M8D3N9", "White"));
        electronics.add(new Electronics("Iphone 8X", 200, "Dunga Enterprise", 23.56, 56.5, "O7H6B5", "Black"));
       
        return electronics;
    }
    
    
    /**
     * This method will sell the products in the table view
     */
    public void sellElectronicButtonPushed()
    {
        
        ObservableList<Electronics> selectedRows , allElectronicDevice;
        allElectronicDevice = ElectronicsTable.getItems();
        
        
        selectedRows = ElectronicsTable.getSelectionModel().getSelectedItems();
        
        
        for(Electronics elecronic : selectedRows)
        {
            allElectronicDevice.remove(elecronic);
        }
        
    }
    
}
