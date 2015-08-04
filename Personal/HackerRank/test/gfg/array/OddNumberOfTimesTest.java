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
public class OddNumberOfTimesTest {
    
    public OddNumberOfTimesTest() {
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
     * Test of theOddOne method, of class OddNumberOfTimes.
     */
    @Test
    public void testTheOddOne() {
        System.out.println("theOddOne");
        int[] input = new int[]{1, 2, 3, 2, 3, 1, 3};
        OddNumberOfTimes instance = new OddNumberOfTimes();
        int expResult = 3;
        int result = instance.theOddOne(input);
        assertEquals(expResult, result);
        
    }
    
}
