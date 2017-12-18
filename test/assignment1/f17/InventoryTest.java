/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

import models.Inventory;
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
public class InventoryTest 
{
    
    Inventory validInventory;
    
    public InventoryTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        
        validInventory = new Inventory("Samsung Galaxy s8", 500, "Sheetal Enterprise", 990.0, 1000.05, "L4M5V3", "Black");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getItemName method, of class Inventory.
     */
    @Test
    public void testGetItemName()
    {
        System.out.println("getItemName");
        String expResult = "Samsung Galaxy s8";
        String result = validInventory.getItemName();
        assertEquals(expResult, result);
     }

    /**
     * Test of setItemName method, of class Inventory.
     */
    @Test
    public void testSetItemName() 
    {
        System.out.println("setItemName");
        String itemName = "Samsung Galaxy s8";
        validInventory.setItemName(itemName);
       
    }

    /**
     * Test of getItemQuantity method, of class Inventory.
     */
    @Test
    public void testGetItemQuantity() 
    {
        System.out.println("getItemQuantity");
        int expResult = 500;
        int result = validInventory.getItemQuantity();
        assertEquals(expResult, result);
     }

    /**
     * Test of setItemQuantity method, of class Inventory.
     */
    @Test
    public void testSetItemQuantity() 
    {
        System.out.println("setItemQuantity");
        int itemQuantity = 500;
        validInventory.setItemQuantity(itemQuantity);
       
    }

    /**
     * Test of getManufacturerName method, of class Inventory.
     */
    @Test
    public void testGetManufacturerName() 
    {
        System.out.println("getManufacturerName");
     
        String expResult = "Sheetal Enterprise";
        String result = validInventory.getManufacturerName();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setManufacturerName method, of class Inventory.
     */
    @Test
    public void testSetManufacturerName() 
    {
        System.out.println("setManufacturerName");
        String manufacturerName = "Sheetal Enterprise";
   
        validInventory.setManufacturerName(manufacturerName);
    }

    /**
     * Test of getSellingPrice method, of class Inventory.
     */
    @Test
    public void testGetCustomerPrice()
    {
        System.out.println("getSellingPrice");
        double expResult = 1000.05;
        double result = validInventory.getCustomerPrice();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setSellingPrice method, of class Inventory.
     */
    @Test
    public void testSetCustomerPrice() 
    {
        System.out.println("setSellingPrice");
        double sellingPrice = 1000.05;
        validInventory.setCustomerPrice(sellingPrice);
    }

    /**
     * Test of getBuyingPrice method, of class Inventory.
     */
    @Test
    public void testGetRetailPrice()
    {
        System.out.println("getBuyingPrice");
        double expResult = 990.0;
        double result = validInventory.getRetailPrice();
        assertEquals(expResult, result, 0.0);
    }
    

    /**
     * Test of setBuyingPrice method, of class Inventory.
     */
    @Test
    public void testSetRetailPrice()
    {
        System.out.println("setBuyingPrice");
        double buyingPrice = 990.0;
        validInventory.setRetailPrice(buyingPrice);
    }

    /**
     * Test of getModel method, of class Inventory.
     */
    @Test
    public void testGetModel()
    {
        System.out.println("getModel");
        String expResult = "L4M5V3";
        String result = validInventory.getModel();
        assertEquals(expResult, result);
    }

    /**
     * Test of setModel method, of class Inventory.
     */
    @Test
    public void testSetModel() 
    {
        System.out.println("setModel");
        String model = "L4M5V3";
        validInventory.setModel(model);
    }

    /**
     * Test of getColor method, of class Inventory.
     */
    @Test
    public void testGetColor()
    {
        System.out.println("getColor");
        String expResult = "Black";
        String result = validInventory.getColor();
        assertEquals(expResult, result);
    }

    /**
     * Test of setColor method, of class Inventory.
     */
    @Test
    public void testSetColor()
    {
        System.out.println("setColor");
        String color = "Black";
        validInventory.setColor(color);
    }
}
