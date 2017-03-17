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
public class NonAdjMaxSumTest {
    
    public NonAdjMaxSumTest() {
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
     * Test of findNonAdjucentMaxSum method, of class NonAdjMaxSum.
     */
    @Test
    public void testFindNonAdjucentMaxSum() {
        System.out.println("findNonAdjucentMaxSum");
        int[] input = new int[]{4, 5, 17, 3, 12, 29, 0, -25, 25, 28, 16, 16, 
            16, 11, -10, -4, 3, -25, 10, -20, 1, -9, 23, 13, -25, 9, -24, 
            15, 5, -20};
            
        NonAdjMaxSum instance = new NonAdjMaxSum();
        int expResult = 168;
        int result = instance.findNonAdjucentMaxSum(input);
        assertEquals(expResult, result);
        
    }
    
}
