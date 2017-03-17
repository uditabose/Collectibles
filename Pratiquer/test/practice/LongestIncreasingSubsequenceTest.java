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
public class LongestIncreasingSubsequenceTest {
    
    public LongestIncreasingSubsequenceTest() {
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
     * Test of longestIncreasingSubseq method, of class LongestIncreasingSubsequence.
     */
    @Test
    public void testLongestIncreasingSubseq() {
        System.out.println("longestIncreasingSubseq");
        int[] input = {10, 22, 9, 33, 21, 50, 41, 60};
        LongestIncreasingSubsequence instance = new LongestIncreasingSubsequence();
        int expResult = 5;
        int result = instance.longestIncreasingSubseq(input);
        assertEquals(expResult, result);
        
    }
    
}
