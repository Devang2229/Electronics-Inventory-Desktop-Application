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
import javafx.scene.control.Label;
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
    @FXML private TableColumn<Electronics, Double> retailPrice;
    @FXML private TableColumn<Electronics, Double> customerPrice;
    @FXML private TableColumn<Electronics, String> model;
    
    @FXML private Button sell;
    @FXML private Label inventoryValueLabel;
    @FXML private Label electronicsStockLabel;
    @FXML private Label electronicsSoldLabel;
    @FXML private Label totalSalesLabel;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        //set up the columns in the table
        name.setCellValueFactory(new PropertyValueFactory<Electronics, String>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<Electronics, Integer>("itemQuantity"));
        manufacturerName.setCellValueFactory(new PropertyValueFactory<Electronics, String>("manufacturerName"));
        retailPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("retailPrice"));
        customerPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("customerPrice"));
        model.setCellValueFactory(new PropertyValueFactory<Electronics, String>("model"));
        color.setCellValueFactory(new PropertyValueFactory<Electronics, String>("color"));
        
        
        //load dummy data
        ElectronicsTable.setItems(getInventoryItems());
        this.updateInventoryValueLabel();
        this.updateElectronicsInStockLabel();
        this.updateTotalSalesLabel();
        
        
        
    }
   
   public void createElectronicsButtonPushed(ActionEvent event) throws IOException
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
        electronics.add(new Electronics("Dell Inspiron", 56, "Sheetal Enterprise", 1200.0, 1300.56, "L4J6J6", "Black"));
        electronics.add(new Electronics("Samung S8 Plus", 98, "Jassrra Maketing", 1100.96,1200.96 , "M4N6V4", "Silver"));
        electronics.add(new Electronics("Nokia A8", 25, "Bindu Sales & Co.", 1000.0, 1200.08, "M8D3N9", "White"));
        electronics.add(new Electronics("Iphone 8X", 200, "Dunga Enterprise", 1000.96, 1500.56, "O7H6B5", "Black"));
       
        return electronics;
    }
    
    public void updateInventoryValueLabel()
    {
        double inventoryValue=0;
        
        for (Electronics device : ElectronicsTable.getItems())
        {
            inventoryValue += device.getRetailPrice();
        }
        inventoryValueLabel.setText("Total Inventory Value: " +inventoryValue);
    }
    
    public void updateElectronicsInStockLabel()
    {
        double itemsLeft=0;
        for(Inventory items : ElectronicsTable.getItems())
        {
            itemsLeft+= items.getItemQuantity();
        }
         electronicsStockLabel.setText("Electronics Currently Stock: " + itemsLeft);
    }
    
    public void updateElectronicsSoldLabel()
    {
        double itemsSold=0;
        for(Inventory items : ElectronicsTable.getItems())
        {
            itemsSold+= items.getItemQuantity() ;
        }
         electronicsSoldLabel.setText("Eletronics Sold: " + itemsSold);
    }
    
    public void updateTotalSalesLabel()
    {
        double totalSales =0;
        for(Inventory items : ElectronicsTable.getItems())
        {
            totalSales+= items.getCustomerPrice();
        }
         totalSalesLabel.setText("Total Sales: " + totalSales);
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
        updateInventoryValueLabel();
        updateElectronicsInStockLabel();
        updateElectronicsSoldLabel();
        
        
    }
    
}
