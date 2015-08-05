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
public class MedianOfSortedTest {
    
    public MedianOfSortedTest() {
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
     * Test of medin method, of class MedianOfSorted.
     */
    @Test
    public void testMedin() {
        System.out.println("medin");
        int[] aArray = new int[]{10, 30, 50};
        int[] bArray = new int[]{20, 40, 60};
        MedianOfSorted instance = new MedianOfSorted();
        int expResult = 35;
        int result = instance.medin(aArray, bArray);
        assertEquals(expResult, result);
        
    }
    
}
