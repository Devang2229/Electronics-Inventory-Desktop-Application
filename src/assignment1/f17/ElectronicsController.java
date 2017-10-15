/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class ElectronicsController implements Initializable {

    private ObservableList<Electronics> electronics;
    
    @FXML private ComboBox itemName;
    @FXML private ChoiceBox color;
    @FXML private Spinner itemQuantitySpinner;
    @FXML private TextField manufacturerNamETextField;
    @FXML private TextField BuyingPriceTextField;
    @FXML private TextField SellingPriceTextField;
    @FXML private TextField ModelTextField;
    @FXML private Label errorMsg;
    
     @FXML private ImageView electronicImage;
     
      //Used for the file chooser
    private FileChooser fileChooser;
    private File imageFile;
    
    public void initialData(ObservableList<Electronics> listOfDevice)
    {
        electronics = listOfDevice;
    }
     
    public void createNewDeviceButtonPushed(ActionEvent event) throws IOException
    {
        try
        {
            Electronics newDevice = new Electronics(itemName.getValue().toString(), 
                                                    Integer.parseInt(itemQuantitySpinner.getValue().toString()),
                                                    manufacturerNamETextField.getText(),
                                                    Double.parseDouble(SellingPriceTextField.getText()),
                                                    Double.parseDouble( BuyingPriceTextField.getText()), 
                                                    ModelTextField.getText() ,
                                                    color.getValue().toString());
             
            electronics.add(newDevice);
            changeScene(event, "Inventory.fxml");
        }
    catch (IllegalArgumentException e)
        {
            errorMsg.setText(e.getMessage());
        }
    
    }
   
    public void changeScene(ActionEvent event, String fxmlFileName) throws IOException
    {
        //load a new scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFileName));
        Parent parent = loader.load();
        Scene newScene = new Scene(parent);
        
        //access the controller of the new Scene and send over
        //the current list of employees
        InventoryController controller = loader.getController();
        controller.loadElectronics(electronics );
        
        //Get the current "stage" (aka window) 
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        //change the scene to the new scene
        stage.setTitle("All Electronics Device");
        stage.setScene(newScene);
        stage.show();
    }
    
      /**
     * When this method is called, it will change the Scene to 
     * a TableView example
     * @param event
     * @throws java.io.IOException
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Inventory.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //This items are for configuring the ComboBox.
        itemName.getItems().add("Dell Laptops");
        itemName.getItems().add("Iphone 8");
        itemName.getItems().add("Iphone 7");
        itemName.getItems().add("ASUS Laptop");
        itemName.getItems().add("Samung s7");
        itemName.getItems().add("Samung s8");
        itemName.setValue("Dell Laptops");
        
        //This items are configuring for the Choice Box.
        color.getItems().add("Black");
        color.getItems().add("White");
        color.getItems().add("Silver");
        color.getItems().add("Grey");
        color.getItems().add("Metallic Silver");
        color.setValue("Black");
        
        
        SpinnerValueFactory<Integer> quantity = new  SpinnerValueFactory.IntegerSpinnerValueFactory(0,900, 300);
        this.itemQuantitySpinner.setValueFactory(quantity);
        
        //This is configuring for error Message display.
        errorMsg.setText("");
        
         try
        {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/image/Electronics icon.png"));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            electronicImage.setImage(image);
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }    
    
    /**
     * This method will launch a FileChooser and load the image if accessible
     */
    public void chooseImageButtonPushed(ActionEvent event)
    {
        //get the stage to open a new window
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        
        //filter for only .jpg and .png files
        FileChooser.ExtensionFilter jpgFilter 
                = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter 
                = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        
        
        //Set to the user's picture directory or C drive if not available
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        
        if (!userDirectory.canRead())
            userDirectory = new File("c:/");
        
        fileChooser.setInitialDirectory(userDirectory);
        
        //open the file dialog window
        imageFile = fileChooser.showOpenDialog(stage);
        
        //ensure the user selected a file
        if (imageFile.isFile())
        {
            try
            {
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage,null);
                electronicImage.setImage(image);
            }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
    
}
