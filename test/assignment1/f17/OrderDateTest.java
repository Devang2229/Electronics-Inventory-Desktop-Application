/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.f17;

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
public class OrderDateTest  {
    
    OrderDate orders;
    
    public OrderDateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        orders = new OrderDate(200, 300, 200, "Samsung Galaxy s8", 25, "Sheetal Enterprise", 1000.0, 990.05, "L4M5V3", "Black");
  
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setItemsSold method, of class OrderDate.
     */
    @Test
    public void testSetItemsSold() {
        System.out.println("setItemsSold");
        int itemsSold = 200;
        orders.setItemsSold(itemsSold);
       
    }

    /**
     * Test of setItemsLeft method, of class OrderDate.
     */
    @Test
    public void testSetItemsLeft() {
        System.out.println("setItemsLeft");
        int itemsLeft = 300;
        orders.setItemsLeft(itemsLeft);
        
    }

    /**
     * Test of setPurchaseOrder method, of class OrderDate.
     */
    @Test
    public void testSetPurchaseOrder() {
        System.out.println("setPurchaseOrder");
        int purchaseOrder =  200;
        orders.setPurchaseOrder(purchaseOrder);
     }

    /**
     * Test of getItemsSold method, of class OrderDate.
     */
    @Test
    public void testGetItemsSold() {
        System.out.println("getItemsSold");
        int expResult = 200;
        int result = orders.getItemsSold();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItemsLeft method, of class OrderDate.
     */
    @Test
    public void testGetItemsLeft() {
        System.out.println("getItemsLeft");
        int expResult = 300;
        int result = orders.getItemsLeft();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getPurchaseOrder method, of class OrderDate.
     */
    @Test
    public void testGetPurchaseOrder() {
        System.out.println("getPurchaseOrder");
        int expResult = 500;
        int result = orders.getPurchaseOrder();
        assertEquals(expResult, result);
        
    }
    
}
