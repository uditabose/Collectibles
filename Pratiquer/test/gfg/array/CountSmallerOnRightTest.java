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
public class CountSmallerOnRightTest {
    
    public CountSmallerOnRightTest() {
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
     * Test of countSmallerOnRight method, of class CountSmallerOnRight.
     */
    @Test
    public void testCountSmallerOnRight() {
        System.out.println("countSmallerOnRight");
        int[] input = {12, 1, 2, 3, 0, 11, 4};
        CountSmallerOnRight instance = new CountSmallerOnRight();
        int[] expResult = {6, 1, 1, 1, 0, 1, 0} ;
        int[] result = instance.countSmallerOnRight(input);
        assertArrayEquals(expResult, result);
        
    }
    
}
