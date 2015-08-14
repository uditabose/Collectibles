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
public class TripletSumTest {
    
    public TripletSumTest() {
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
     * Test of tripletSum method, of class TripletSum.
     */
    @Test
    public void testTripletSum() {
        System.out.println("tripletSum");
        int[] input = {12, 3, 4, 1, 6, 9};
        int theSum = 21;
        TripletSum instance = new TripletSum();
        boolean expResult = true;
        boolean result = instance.tripletSum(input, theSum);
        assertEquals(expResult, result);
        
    }
    
}
