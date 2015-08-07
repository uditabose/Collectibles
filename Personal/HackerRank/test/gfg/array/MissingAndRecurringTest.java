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
public class MissingAndRecurringTest {
    
    public MissingAndRecurringTest() {
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
     * Test of findMissingAndRecurring method, of class MissingAndRecurring.
     */
    @Test
    public void testFindMissingAndRecurring() {
        System.out.println("findMissingAndRecurring");
        int[] input = {3, 1, 3};
        MissingAndRecurring instance = new MissingAndRecurring();
        int[] expResult = {2, 3};
        int[] result = instance.findMissingAndRecurring(input);
        assertArrayEquals(expResult, result);
       
    }
    
    @Test
    public void testFindMissingAndRecurring1() {
        System.out.println("findMissingAndRecurring");
        int[] input = {4, 3, 6, 2, 1, 1};
        MissingAndRecurring instance = new MissingAndRecurring();
        int[] expResult = {5, 1};
        int[] result = instance.findMissingAndRecurring(input);
        assertArrayEquals(expResult, result);
       
    }
    
}
