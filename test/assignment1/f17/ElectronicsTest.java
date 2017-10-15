/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

import javafx.scene.image.Image;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author janid
 */
public class ElectronicsTest {
    
    Electronics validElectronics;
    
    public ElectronicsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        validElectronics = new Electronics("L4M2BJK", "Awesome.", "OOK9K9K9K9K9" , "Samsung Galaxy s8", 25, "Sheetal Enterprise", 1000.0, 990.05, "L4M5V3", "Black" );
            
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getProductKey method, of class Electronics.
     */
    @Test
    public void testGetProductKey() 
    {
        System.out.println("getProductKey");
      
        String expResult = "L4M2BJK";
        String result = validElectronics.getProductKey();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setProductKey method, of class Electronics.
     */
    @Test
    public void testSetProductKey() 
    {
        System.out.println("setProductKey");
        String productKey = "L4M2BJK";
       
        validElectronics.setProductKey(productKey);
        
    }

    /**
     * Test of getIsinCode method, of class Electronics.
     */
    @Test
    public void testGetIsinCode() 
    {
        System.out.println("getIsinCode");
       
        String expResult = "OOK9K9K9K9K9";
        String result = validElectronics.getIsinCode();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setIsinCode method, of class Electronics.
     */
    @Test
    public void testSetIsinCode()
    {
        System.out.println("setIsinCode");
        String isinCode = "OOK9K9K9K9K9";
        validElectronics.setIsinCode(isinCode);
    }

    /**
     * Test of getDescription method, of class Electronics.
     */
    @Test
    public void testGetDescription()
    {
        System.out.println("getDescription");
       
        String expResult = "Awesome.";
        String result = validElectronics.getDescription();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setDescription method, of class Electronics.
     */
    @Test
    public void testSetDescription() 
    {
        System.out.println("setDescription");
        String Description = "Awesome.";
       
        validElectronics.setDescription(Description);
        
    }

    /**
     * Test of getImage method, of class Electronics.
     */
    @Test
    public void testGetImage() {
        System.out.println("getImage");
        Electronics instance = null;
        Image expResult = null;
        Image result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
