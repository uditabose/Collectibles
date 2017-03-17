/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

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
public class MaximumSumIncreasingSubsequenceTest {
    
    public MaximumSumIncreasingSubsequenceTest() {
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
     * Test of maxSum method, of class MaximumSumIncreasingSubsequence.
     */
    @Test
    public void testMaxSum() {
        System.out.println("maxSum");
        int[] input = {1, 101, 2, 3, 100, 4, 5};
        MaximumSumIncreasingSubsequence instance = new MaximumSumIncreasingSubsequence();
        int expResult = 106;
        int result = instance.maxSum(input);
        assertEquals(expResult, result);
        
    }
    
}
