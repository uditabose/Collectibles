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
public class PairWithDiffTest {
    
    public PairWithDiffTest() {
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
     * Test of findPairWithDifference method, of class PairWithDiff.
     */
    @Test
    public void testFindPairWithDifference() {
        System.out.println("findPairWithDifference");
        int[] input = {5, 20, 3, 2, 50, 80};
        int diff = 78;
        PairWithDiff instance = new PairWithDiff();
        boolean expResult = true;
        boolean result = instance.findPairWithDifference(input, diff);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindPairWithDifference1() {
        System.out.println("findPairWithDifference");
        int[] input = {90, 70, 20, 80, 50};
        int diff = 45;
        PairWithDiff instance = new PairWithDiff();
        boolean expResult = false;
        boolean result = instance.findPairWithDifference(input, diff);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindPairWithDifference2() {
        System.out.println("findPairWithDifference");
        int[] input = {1, 8, 30, 40, 100};
        int diff = 60;
        PairWithDiff instance = new PairWithDiff();
        boolean expResult = true;
        boolean result = instance.findPairWithDifference(input, diff);
        assertEquals(expResult, result);
        
    }
    
}
