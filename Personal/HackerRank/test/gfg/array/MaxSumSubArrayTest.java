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
public class MaxSumSubArrayTest {
    
    public MaxSumSubArrayTest() {
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
     * Test of maxSum method, of class MaxSumSubArray.
     */
    @Test
    public void testMaxSum() {
        System.out.println("maxSum");
        int[] input = new int[]{-2, -3, 4, -1, -2, 1, 5, -3};
        MaxSumSubArray instance = new MaxSumSubArray();
        int expResult = 7;
        int result = instance.maxSum(input);
        assertEquals(expResult, result);
        
    }
    
}
