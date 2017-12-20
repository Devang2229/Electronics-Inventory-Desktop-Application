package models;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author janid
 */
public class Users
{
    //instance variables
    protected SimpleStringProperty firstName, lastName, emailAddress;
    protected byte[] salt;
    protected String password;
    protected boolean admin;
    private  int employeeId;
    private SimpleStringProperty phoneNumber;
    
    /**
     * This is the constructor for the Users
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @param phoneNumber
     * @param password
     * @param admin
     * @throws NoSuchAlgorithmException 
     */
    public Users( String firstName, String lastName, String emailAddress,String phoneNumber, String password, boolean admin) throws NoSuchAlgorithmException
    {
        setLastName(lastName);
        setFirstName(firstName);
        setEmailAddress(emailAddress);
        setPhoneNumber(phoneNumber);
        salt = PasswordGenerator.getSalt();
        this.password = PasswordGenerator.getSHA512Password(password, salt);
        this.admin = admin;
      
    }
    
    /**
     * This is the getter method for phone number
     * @return 
     */
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
    
    /**
     * This is the setter method for the phone number.
     * @param phoneNumber 
     */
    public void setPhoneNumber(String phoneNumber) {
        
        if(phoneNumber != null)
            this.phoneNumber = new SimpleStringProperty(phoneNumber);
        else 
            throw new IllegalArgumentException("Please enter the phone number.");

    }
       
     /**
     * This is the getter method for first name
     * @return 
     */
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        if(firstName != null)
            this.firstName = new SimpleStringProperty(firstName);
        else 
            throw new IllegalArgumentException("Please enter the first name.");
       
    }
    
     /**
     * This is the getter method for last name
     * @return 
     */
    public String getLastName() {
        return lastName.get();
    }
    
    /**
     * This setter method for last name validates that the field is not null or else it throws and error. 
     * @param lastName 
     */
    public void setLastName(String lastName) {
        
          if(lastName != null)
            this.lastName = new SimpleStringProperty(lastName);     
          else 
            throw new IllegalArgumentException("Please enter the last name.");
        
    }
    

    /**
     * This is the getter method for email address
     * @return 
     */
    public String getEmailAddress() {
        return emailAddress.get();
    }

    /**
     * This is the setter method for email address which validates that email address is valid and is not email to null.
     * @param emailAddress 
     */
    public void setEmailAddress(String emailAddress) {
     if(emailAddress != null)
        this.emailAddress = new SimpleStringProperty(emailAddress);
     else 
         throw new IllegalArgumentException("Please enter your emailAddress: firstname.lastname@bestbuy.com");
    }
    
    
     /**
     * This is the getter method for password
     * @return 
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * This is the method for getter salt.
     * @return 
     */
    public byte[] getSalt() {
        return salt;
    }
    
    /**
     * This is the method if it is the admin.
     * @return 
     */
    public boolean isAdmin() {
        return admin;
    }
    
    /**
     * 
     * This is the setter method if it is admin or not.
     * @param admin 
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    /**
     * This is the getter method for employee id
     * @return 
     */ 
    public int getEmployeeId() {
        return employeeId;
    }
    
    /**
     * This is the setter method for the employee id and validates if it is greater than 0 .
     * @param EmployeeId 
     */
    public void setEmployeeId(int EmployeeId) {
        if (EmployeeId >= 0)
            this.employeeId = EmployeeId;
        else
            throw new IllegalArgumentException("User ID must be >= 0");
    }
    
    
     /**
     * This method will write the instance of the electronics into the database
     */
    public void insertIntoDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO Users(firstName,lastName,emailAddress,phoneNumber, password,salt,admin)"
                    + "VALUES (?,?,?,?,?,?,?)";
                    
            //3. prepare the query
            preparedStatement = conn.prepareStatement(sql);
              
            //5. Bind the values to the parameters
          
            preparedStatement.setString(1,firstName.get());
            preparedStatement.setString(2,lastName.get());
            preparedStatement.setString(3,emailAddress.get());
            preparedStatement.setString(4, phoneNumber.get());
            preparedStatement.setString(5,password);
            preparedStatement.setBlob(6,new javax.sql.rowset.serial.SerialBlob(salt));
            preparedStatement.setBoolean(7,admin);
            
            preparedStatement.executeUpdate();
        }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
            finally
            {
                if (preparedStatement != null)
                    preparedStatement.close();

                if (conn != null)
                    conn.close();
            }
    }
    
    /**
     * This method updates the users in the database.
     * @throws SQLException 
     */
    public void updateUsersInDB() throws SQLException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        
        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE Users SET firstName = ?, lastName =?, emailAddress =?,phoneNumber = ?, admin=?";
                    
                    
            
            //3. prepare the query against SQL injection
            preparedStatement = conn.prepareStatement(sql);
            
            //5. Bind the values to the parameters
            preparedStatement.setString(1, firstName.get());
            preparedStatement.setString(2, lastName.get());
            preparedStatement.setString(3, emailAddress.get());
           
            preparedStatement.setString(4, phoneNumber.get());
             
            preparedStatement.setBoolean(5, admin);

            
            preparedStatement.executeUpdate();
            preparedStatement.close();
          
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (conn != null)
                conn.close();
            if (preparedStatement != null)
                preparedStatement.close();
        }
        
    }
    
    
    
}//end of the class
    
