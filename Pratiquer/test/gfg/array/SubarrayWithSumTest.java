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
public class SubarrayWithSumTest {
    
    public SubarrayWithSumTest() {
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
     * Test of subWithSum method, of class SubarrayWithSum.
     */
    @Test
    public void testSubWithSum() {
        System.out.println("subWithSum");
        int[] input = {15, 2, 4, 8, 9, 5, 10, 23};
        int sum = 23;
        SubarrayWithSum instance = new SubarrayWithSum();
        int[] expResult = {1, 4};
        int[] result = instance.subWithSum(input, sum);
        assertArrayEquals(expResult, result);
        
    }
    
}
