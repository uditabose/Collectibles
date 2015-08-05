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
public class MaxDiffWithLargeOnRightTest {
    
    public MaxDiffWithLargeOnRightTest() {
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
     * Test of getMaxDiff method, of class MaxDiffWithLargeOnRight.
     */
    @Test
    public void testGetMaxDiff() {
        System.out.println("getMaxDiff");
        int[] input = new int[]{9, 8, 18, 1, 2, 7, 22, 10};
        MaxDiffWithLargeOnRight instance = new MaxDiffWithLargeOnRight();
        int expResult = 21;
        int result = instance.getMaxDiff(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetMaxDiff1() {
        System.out.println("getMaxDiff");
        int[] input = new int[]{9, 8, 18, 1, 2, 7, 9, 10};
        MaxDiffWithLargeOnRight instance = new MaxDiffWithLargeOnRight();
        int expResult = 10;
        int result = instance.getMaxDiff(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetMaxDiff2() {
        System.out.println("getMaxDiff");
        int[] input = new int[]{9, 10, 11, 12, 13, 14, 15, 16};
        MaxDiffWithLargeOnRight instance = new MaxDiffWithLargeOnRight();
        int expResult = 7;
        int result = instance.getMaxDiff(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetMaxDiff3() {
        System.out.println("getMaxDiff");
        int[] input = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        MaxDiffWithLargeOnRight instance = new MaxDiffWithLargeOnRight();
        int expResult = Integer.MIN_VALUE;
        int result = instance.getMaxDiff(input);
        assertEquals(expResult, result);
        
    }
    
}
