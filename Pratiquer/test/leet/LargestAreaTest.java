/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Udita <udita.bose@nyu.edu>
 */
public class LargestAreaTest {
    
    public LargestAreaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of largestRectangleAreaBrute method, of class LargestArea.
     */
    @Test
    public void testLargestRectangleArea() {
        System.out.println("largestRectangleArea");
        int[] height = new int[]{2,1,5,6,2,3};
        LargestArea instance = new LargestArea();
        int expResult = 10;
        int result = instance.largestRectangleArea(height);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testLargestRectangleArea1() {
        System.out.println("largestRectangleArea1");
        int[] height = new int[]{2,1,6,5,2,3};
        LargestArea instance = new LargestArea();
        int expResult = 10;
        int result = instance.largestRectangleArea(height);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testLargestRectangleArea2() {
        System.out.println("largestRectangleArea2");
        int[] height = new int[]{2,1,11,1,5,2,3};
        LargestArea instance = new LargestArea();
        int expResult = 11;
        int result = instance.largestRectangleArea(height);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testLargestRectangleArea3() {
        System.out.println("largestRectangleArea2");
        int[] height = new int[]{1,1,1,1,1,1,1};
        LargestArea instance = new LargestArea();
        int expResult = 7;
        int result = instance.largestRectangleArea(height);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of largestRectangleAreaBrute method, of class LargestArea.
     */
    //@Test
    public void testLargestRectangleAreaBrute() {
        System.out.println("largestRectangleAreaBrute");
        int[] height = null;
        LargestArea instance = new LargestArea();
        int expResult = 0;
        int result = instance.largestRectangleAreaBrute(height);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
