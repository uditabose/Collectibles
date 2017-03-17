/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfg.array;

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
public class SmallestPositiveTest {
    
    public SmallestPositiveTest() {
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
     * Test of findSmallestPositive method, of class SmallestPositive.
     */
    @Test
    public void testFindSmallestPositive() {
        System.out.println("findSmallestPositive");
        int[] input = {2, 3, 7, 6, 8, -1, -10, 15};
        SmallestPositive instance = new SmallestPositive();
        int expResult = 1;
        int result = instance.findSmallestPositive(input);
        assertEquals(expResult, result);
        
    }
    
    //{ 2, 3, -7, 6, 8, 1, -10, 15 }
    @Test
    public void testFindSmallestPositive1() {
        System.out.println("findSmallestPositive");
        int[] input = { 2, 3, -7, 6, 8, 1, -10, 15 };
        SmallestPositive instance = new SmallestPositive();
        int expResult = 4;
        int result = instance.findSmallestPositive(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindSmallestPositive2() {
        System.out.println("findSmallestPositive");
        int[] input = {1, 1, 0, -1, -2};
        SmallestPositive instance = new SmallestPositive();
        int expResult = 2;
        int result = instance.findSmallestPositive(input);
        assertEquals(expResult, result);
        
    }
    
    //{0, 10, 2, -10, -20}
    @Test
    public void testFindSmallestPositive3() {
        System.out.println("findSmallestPositive");
        int[] input = {0, 10, 2, -10, -20};
        SmallestPositive instance = new SmallestPositive();
        int expResult = 1;
        int result = instance.findSmallestPositive(input);
        assertEquals(expResult, result);
        
    }
    
    //1,3,5,6,8
    @Test
    public void testFindSmallestPositive4() {
        System.out.println("findSmallestPositive");
        int[] input = {1, 3, 5, 6, 8};
        SmallestPositive instance = new SmallestPositive();
        int expResult = 2;
        int result = instance.findSmallestPositive(input);
        assertEquals(expResult, result);
        
    }
}
