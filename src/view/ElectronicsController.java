/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    //private ObservableList<Electronics> electronics;
    
    @FXML private ComboBox itemName;
    @FXML private ChoiceBox color;
    @FXML private Spinner itemQuantitySpinner;
    @FXML private TextField manufacturerNamETextField;
    @FXML private TextField retailPriceTextField;
    @FXML private TextField customerPriceTextField;
    @FXML private TextField ModelTextField;
    @FXML private Label errorMsgLabel;
    private Electronics electronics;
    @FXML private Label headerLabel;
    @FXML private CheckBox adminCheckBox;
    
    @FXML private ImageView electronicImage;
     
    //Used for the file chooser
    private FileChooser fileChooser;
    private File imageFile;
    private boolean imageFileChanged;
    
    //used for the passwords
    @FXML private PasswordField pwField;
    @FXML private PasswordField confirmPwField;
    
    JFrame frame = new JFrame("JOptionPane showMessageDialog example");
    
    
    
    public void createNewDeviceButtonPushed(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException
    {
        if(validPassword())
        {
            try
            {
                if (electronics != null) //we need to edit/update an existing volunteer
                    {
                        updateElectronics();
                        electronics.updateElectrornicsInDB();
                    }
                else 
                {
                    if(imageFileChanged)
                        {
                            electronics = new Electronics(itemName.getValue().toString(), 
                                                                Integer.parseInt(itemQuantitySpinner.getValue().toString()),
                                                                manufacturerNamETextField.getText(),
                                                                Double.parseDouble(retailPriceTextField.getText()),
                                                                Double.parseDouble(customerPriceTextField.getText()), 
                                                                ModelTextField.getText() ,
                                                                color.getValue().toString(), imageFile.getAbsoluteFile(), 
                                                                pwField.getText(),adminCheckBox.isSelected() );
                        }



                      else
                        {
                               electronics = new Electronics(itemName.getValue().toString(), 
                                                                Integer.parseInt(itemQuantitySpinner.getValue().toString()),
                                                                manufacturerNamETextField.getText(),
                                                                Double.parseDouble(retailPriceTextField.getText()),
                                                                Double.parseDouble(customerPriceTextField.getText()), 
                                                                ModelTextField.getText() ,
                                                                color.getValue().toString(),
                                                                 pwField.getText(), adminCheckBox.isSelected());

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
        
    }
   
   
      /**
     * When this method is called, it will change the Scene to 
     * a TableView example, so the back button takes it to the table view.
     * @param event
     * @throws java.io.IOException
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        
        //check if it is an admin user and go to the table view
        if (SceneChangingUtility.getLoggedInUser().isAdmin())
            sc.changeScenes(event, "Inventory.fxml", "All Electronics");
        else
        {
            PurchaseOrderItemViewController controller = new PurchaseOrderItemViewController();
            sc.changeScenes(event, "PurchaseOrderItemView.fxml", "Purchase Order", electronics, controller);
        }
          
    }
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         imageFileChanged = false; //initially the image has not changed, use the default
           
        //This is configuring for error Message display.
        errorMsgLabel.setText("");
        
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

    @Override
    public void preloadData(Electronics electronics)
    {
        //update the Spinner value factory
        SpinnerValueFactory<Integer> quantity = new  SpinnerValueFactory.IntegerSpinnerValueFactory(0,900, electronics.getItemQuantity());
        this.itemQuantitySpinner.setValueFactory(quantity);
        
        //update the display
        this.electronics = electronics;
        this.itemName.setValue(electronics.getItemName());
        this.manufacturerNamETextField.setText(electronics.getManufacturerName());
        this.retailPriceTextField.setUserData(electronics.getRetailPrice());
        this.customerPriceTextField.setUserData(electronics.getCustomerPrice());
        this.ModelTextField.setText(electronics.getModel());
        this.color.setUserData(electronics.getColor());
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
     * This method will validate that the passwords match
     * 
     */
    public boolean validPassword()
    {
        if (pwField.getText().length() < 5)
        {
            errorMsgLabel.setText("Passwords must be greater than 5 characters in length");
            return false;
        }
        
        if (pwField.getText().equals(confirmPwField.getText()))
            return true;
        else
            return false;
    }
    
    
    
  
     /**
     * This method will read from the GUI fields and update the volunteer object
     */
    public void updateElectronics() throws IOException
    {
        electronics.setItemName(itemName.getValue().toString());
        electronics.setItemQuantity(Integer.parseInt(itemQuantitySpinner.getValue().toString()));
        electronics.setManufacturerName(manufacturerNamETextField.getText());
        electronics.setRetailPrice( Double.parseDouble(retailPriceTextField.getText()));
        electronics.setCustomerPrice(Double.parseDouble(customerPriceTextField.getText()));
        electronics.setModel(ModelTextField.getText());
        electronics.setColor(color.getValue().toString());
        electronics.setImageFile(imageFile);
        electronics.copyImageFile();
    }
       
    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility.setLoggedInUser(null);
        SceneChangingUtility sc= new SceneChangingUtility();
        sc.changeScenes(event, "LogInView.fxml", "LogIn");
        
    }

}
    
    
 
