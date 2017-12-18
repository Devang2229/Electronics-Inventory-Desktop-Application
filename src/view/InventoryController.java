package view;

import java.io.File;
import models.Electronics;
import models.Inventory;
import view.ElectronicsController;
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
    @FXML private TableColumn<Electronics, Integer> userId;
    @FXML private TableColumn<Electronics, Integer> quantity;
    @FXML private TableColumn<Electronics, String> manufacturerName;
    @FXML private TableColumn<Electronics, String> color;
    @FXML private TableColumn<Electronics, Double> retailPrice;
    @FXML private TableColumn<Electronics, Double> customerPrice;
    @FXML private TableColumn<Electronics, String> model;
    
    @FXML private Button sell;
    @FXML private Button editDeviceButton;
    @FXML private Label inventoryValueLabel;
    @FXML private Label electronicsStockLabel;
    @FXML private Label electronicsSoldLabel;
    @FXML private Label totalSalesLabel;
    @FXML private Button purchaseOrderButton;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        //set up the columns in the table
        editDeviceButton.setDisable(true);
        purchaseOrderButton.setDisable(true);
        userId.setCellValueFactory(new PropertyValueFactory<Electronics, Integer>("UserId"));
        name.setCellValueFactory(new PropertyValueFactory<Electronics, String>("itemName"));
        quantity.setCellValueFactory(new PropertyValueFactory<Electronics, Integer>("itemQuantity"));
        manufacturerName.setCellValueFactory(new PropertyValueFactory<Electronics, String>("manufacturerName"));
        retailPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("retailPrice"));
        customerPrice.setCellValueFactory(new PropertyValueFactory<Electronics, Double>("customerPrice"));
        model.setCellValueFactory(new PropertyValueFactory<Electronics, String>("model"));
        color.setCellValueFactory(new PropertyValueFactory<Electronics, String>("color"));
        
        
       try{
            loadElectronics();
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
       
        this.updateInventoryValueLabel();
        this.updateElectronicsInStockLabel();
        this.updateTotalSalesLabel();
       
        
        
    }
    
    
    
      /**
     * If the edit button is pushed, pass the selected Volunteer to the NewUserView 
     * and pre-load it with the data
     */
    public void editButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        Electronics electronics = this.ElectronicsTable.getSelectionModel().getSelectedItem();
        ElectronicsController ec = new ElectronicsController();
        
        sc.changeScenes(event, "Electronics.fxml", "Edit Electronics", electronics, ec );
    }
    
    /**
     *The button gets enabled when the any of the device is selected
     */
    public void devicesSelected()
    {
        editDeviceButton.setDisable(false);
        purchaseOrderButton.setDisable(false);
    }
    
    
     public void PurchaseOrderButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        
        //this gets the volunteer from the table
        Electronics electronics = this.ElectronicsTable.getSelectionModel().getSelectedItem();
        if (electronics == null)
            return;
        
        PurchaseOrderItemViewController poivc = new PurchaseOrderItemViewController();
        sc.changeScenes(event, "PurchaseOrderItemView.fxml", "Purchase Order", electronics, poivc);
    }
    
      /**
     * This method will load the electronics from the database and load them into 
     * the TableView object
     */
    public void loadElectronics() throws SQLException
    {
        
        ObservableList<Electronics> electronics = FXCollections.observableArrayList();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try{
            //1. connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics", "root", "Dzian@0901");
            //2.  create a statement object
            statement = conn.createStatement();
            
            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM electronics");
            
            //4.  create electronics objects from each record
            while (resultSet.next())
            {
                Electronics newelectronics = new Electronics(resultSet.getString("itemName"),
                                                       resultSet.getInt("itemQuantity"),
                                                       resultSet.getString("manufacturerName"),
                                                       resultSet.getDouble("retailPrice"),
                                                       resultSet.getDouble("customerPrice"),
                                                       resultSet.getString("model"),
                                                       resultSet.getString("color"),
                                                       resultSet.getString("password"));
                newelectronics.setUserId(resultSet.getInt("UserId"));

             newelectronics.setImageFile((new File (resultSet.getString("imageFile"))));
             
                                        
                
                electronics.add(newelectronics);
            }
            
            ElectronicsTable.getItems().addAll(electronics);
            
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
    * This method takes the inventory view to the next view where the electronics can be created.
    * @param event
    * @throws IOException 
    */
   public void createElectronicsButtonPushed(ActionEvent event) throws IOException
    {
         SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "Electronics.fxml", "Create New Electronic Device");
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
    
    
      public void monthlyHoursButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "MonthlyPurchaseReportView.fxml", "View Purchase Order Report");
    }
    
    
    
}
