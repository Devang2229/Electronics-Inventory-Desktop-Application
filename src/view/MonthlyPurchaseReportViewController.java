
package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author janid
 */
public class MonthlyPurchaseReportViewController implements Initializable {

    
      
    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis months;

    @FXML
    private NumberAxis ItemPurchased;
    
    
    private XYChart.Series currentYearSeries, previousYearSeries;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         currentYearSeries = new XYChart.Series<>();
        previousYearSeries = new XYChart.Series<>();
        
        //barChart.setTitle("Hours Worked");
        months.setLabel("Months");
        ItemPurchased.setLabel("Items Purchased");
        
        currentYearSeries.setName(Integer.toString(LocalDate.now().getYear()));
        previousYearSeries.setName(Integer.toString(LocalDate.now().getYear()-1));
        
        
         try{
            populateSeriesFromDB();
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
        
        barChart.getData().addAll(previousYearSeries);
        barChart.getData().addAll(currentYearSeries);
    }    
    
    /**
     * This will read the user data from the database and populate the series
     */
    public void populateSeriesFromDB() throws SQLException
    {
        //get the results from the database
        Connection conn=null;
        Statement statement=null;
        ResultSet resultSet=null;
        
        try
        {
            //1.  connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Electronics?useSSL=false", "root", "Dzian@0901");
            
            //2.  create the statement
            statement = conn.createStatement();
            
            //3.  create a string with the sql statement
            String sql = "SELECT YEAR(purchaseDate), MONTHNAME(purchaseDate), SUM(purchaseOrderQuantity) " +
                         "FROM purchaseOrder " +
                         "GROUP BY YEAR(purchaseDate), MONTH(purchaseDate)" +
                         "ORDER BY YEAR(purchaseDate), MONTH(purchaseDate);";
            
            //4. execute the query
            resultSet = statement.executeQuery(sql);
            
            //5.  loop over the result set and add to our series
            while (resultSet.next())
            {
                if (resultSet.getInt(1) == LocalDate.now().getYear())
                    currentYearSeries.getData().add(new XYChart.Data(resultSet.getString(2), resultSet.getInt(3)));
                else if (resultSet.getInt(1) == LocalDate.now().getYear()-1)
                    previousYearSeries.getData().add(new XYChart.Data(resultSet.getString(2), resultSet.getInt(3)));    
            }       
        }
        catch (SQLException e)
        {
            System.err.println(e.getMessage());
        }
        finally
        {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
    }

    public void backButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility sc = new SceneChangingUtility();
        sc.changeScenes(event, "AdminPortalView.fxml", "All  ELectronics");
    }
  
    public void logoutButtonPushed(ActionEvent event) throws IOException
    {
        SceneChangingUtility.setLoggedInUser(null);
        SceneChangingUtility sc= new SceneChangingUtility();
        sc.changeScenes(event, "LogInView.fxml", "LogIn");
        
    }
    
}
