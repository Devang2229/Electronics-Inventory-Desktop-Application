
package view;

import models.Electronics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class ElectronicsController implements Initializable, ControllerInterface
{

  
    @FXML private TextField itemName;
    @FXML private ChoiceBox color;
    @FXML private Spinner itemQuantitySpinner;
    @FXML private ChoiceBox manufacturerNamETextField;
    @FXML private TextField retailPriceTextField;
    @FXML private TextField customerPriceTextField;
    @FXML private TextField ModelTextField;
    @FXML private Label errorMsgLabel;
    @FXML private Label headerLabel;

    @FXML private ImageView electronicImage;
  
    //Used for the file chooser
    private FileChooser fileChooser;
    private File imageFile;
    private Electronics electronics;
    private boolean imageFileChanged;
    
   
    JFrame frame = new JFrame("JOptionPane showMessageDialog example");
    
    
    /*
    This method allows the user to create new electronics and update that in the inventory vie. If the button is pushed.
    */
    public void createNewDeviceButtonPushed(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException
    {
       
        
            try
            {
                if (electronics != null) 
                    {
                        updateElectronics();
                        electronics.updateElectrornicsInDB();
                    }
                else 
                {
                    if(imageFileChanged)
                        {
                            electronics = new Electronics(itemName.getText(), 
                                                                Integer.parseInt(itemQuantitySpinner.getValue().toString()),
                                                                manufacturerNamETextField.getValue().toString(),
                                                                Double.parseDouble(retailPriceTextField.getText()),
                                                                Double.parseDouble(customerPriceTextField.getText()), 
                                                                ModelTextField.getText() ,
                                                                color.getValue().toString(), imageFile.getAbsoluteFile()) ;
                                                                
                        }
                    else
                        {
                               electronics = new Electronics(itemName.getText(), 
                                                                Integer.parseInt(itemQuantitySpinner.getValue().toString()),
                                                                manufacturerNamETextField.getValue().toString(),
                                                                Double.parseDouble(retailPriceTextField.getText()),
                                                                Double.parseDouble(customerPriceTextField.getText()), 
                                                                ModelTextField.getText() ,
                                                                color.getValue().toString());
                                                               

                         }
                 errorMsgLabel.setText("");    
                 electronics.insertIntoDB(); 

                }

                 SceneChangingUtility sc = new SceneChangingUtility();
                 sc.changeScenes(event, "Inventory.fxml", "All Electronics");

            }
            catch(IllegalArgumentException e)
                {
                    JOptionPane.showMessageDialog(frame, e.getMessage(),"Invalid Information", JOptionPane.INFORMATION_MESSAGE);
                    //this.errorMsgLabel.setText(e.getMessage());
                }

        }
    
      /**
     * When this method is called, it will change the Scene to 
     * a inventory example, so the back button takes it to the table view.
     * @param event
     * @throws java.io.IOException
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "Inventory.fxml", "All Electronics");
          
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imageFileChanged = false; //initially the image has not changed, use the default
           
        //This is configuring for error Message display.
        errorMsgLabel.setText("");
        
        //This items are for configuring the ChoiceBoX.
        manufacturerNamETextField.getItems().add("Arizona Ltd.");
        manufacturerNamETextField.getItems().add("A1 Mobile Phones Ltd.");
        manufacturerNamETextField.getItems().add("Bay Ltd.");
        manufacturerNamETextField.getItems().add("Samsung");
        manufacturerNamETextField.getItems().add("Apple");
        manufacturerNamETextField.getItems().add("Sony");
        manufacturerNamETextField.getItems().add("China Market Ltd.");
        manufacturerNamETextField.getItems().add("Japan Private Ltd.");
        manufacturerNamETextField.getItems().add("Lenovo Ltd.");
        manufacturerNamETextField.setValue("China Market Ltd.");
    
        
        //This items are configuring for the Choice Box.
        color.getItems().add("Black");
        color.getItems().add("White");
        color.getItems().add("Silver");
        color.getItems().add("Grey");
        color.getItems().add("Metallic Silver");
        color.setValue("Black");
        
        
        SpinnerValueFactory<Integer> quantity = new  SpinnerValueFactory.IntegerSpinnerValueFactory(0,900, 300);
        this.itemQuantitySpinner.setValueFactory(quantity);
     
        
         try
        {
            imageFile = new File("./src/image/Electronics icon.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
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
        File tmpImageFile = fileChooser.showOpenDialog(stage);
        
        
         if (tmpImageFile != null)
        {
            imageFile = tmpImageFile;
            //ensure the user selected a file
            if (imageFile.isFile())
            {
                try
                {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image image = SwingFXUtils.toFXImage(bufferedImage,null);
                    electronicImage.setImage(image);
                     imageFileChanged = true;
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    
    /**
     * This is the method where it pre-loads the data.
     * @param electronics 
     */
    @Override
    public void preloadData(Electronics electronics)
    {
        //update the Spinner value factory
        SpinnerValueFactory<Integer> quantity = new  SpinnerValueFactory.IntegerSpinnerValueFactory(0,900, electronics.getItemQuantity());
        this.itemQuantitySpinner.setValueFactory(quantity);
      
        
        //update the display
        this.electronics = electronics;
        this.itemName.setText(electronics.getItemName().toString());
        this.manufacturerNamETextField.setValue(electronics.getManufacturerName().toString());
        
        this.ModelTextField.setText(electronics.getModel());
        this.color.setValue(electronics.getColor().toString());
        this.headerLabel.setText("Edit Electronics");
         
        try
            {
                String imgLocation = ".\\src\\images\\" + electronics.getImageFile().getName();
                imageFile = new File(imgLocation);
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                electronicImage.setImage(img);
            }
            catch (IOException e)
             {
                    System.err.println(e.getMessage());
             }
    }
    
  
     /**
     * This method will read from the GUI fields and update the volunteer object
     */
    public void updateElectronics() throws IOException
    {
        electronics.setItemName(itemName.getText());
        electronics.setItemQuantity(Integer.parseInt(itemQuantitySpinner.getValue().toString()));
        electronics.setManufacturerName(manufacturerNamETextField.getValue().toString());
        electronics.setRetailPrice( Double.parseDouble(retailPriceTextField.getText()));
        electronics.setCustomerPrice(Double.parseDouble(customerPriceTextField.getText()));
        electronics.setModel(ModelTextField.getText());
        electronics.setColor(color.getValue().toString());
        electronics.setImageFile(imageFile);
        electronics.copyImageFile();
    }
    
    /**
     * This is the method where it logs out of the system.
     * @param event
     * @throws IOException 
     */
    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility.setLoggedInUser(null);
        SceneChangingUtility sc= new SceneChangingUtility();
        sc.changeScenes(event, "LogInView.fxml", "LogIn");
        
    }

}
    
    
 
