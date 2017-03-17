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
public class MaxDiffPositionTest {
    
    public MaxDiffPositionTest() {
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
     * Test of findMaxDiffPosition method, of class MaxDiffPosition.
     */
    @Test
    public void testFindMaxDiffPosition() {
        System.out.println("findMaxDiffPosition");
        int[] input = {34, 8, 10, 3, 2, 80, 30, 33, 1};
        MaxDiffPosition instance = new MaxDiffPosition();
        int expResult = 6;
        int result = instance.findMaxDiffPosition(input);
        assertEquals(expResult, result);
        
    }
    
}
