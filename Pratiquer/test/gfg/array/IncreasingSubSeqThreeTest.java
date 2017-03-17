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
public class IncreasingSubSeqThreeTest {
    
    public IncreasingSubSeqThreeTest() {
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
     * Test of increasingSubSeq method, of class IncreasingSubSeqThree.
     */
    @Test
    public void testIncreasingSubSeq() {
        System.out.println("increasingSubSeq");
        int[] input = {12, 11, 10, 5, 6, 2, 30};
        IncreasingSubSeqThree instance = new IncreasingSubSeqThree();
        int[] expResult = {3, 4, 6};
        int[] result = instance.increasingSubSeq(input);
        assertArrayEquals(expResult, result);
        
    }
    // input
    @Test
    public void testIncreasingSubSeq1() {
        System.out.println("increasingSubSeq");
        int[] input = {1, 2, 3, 4};
        IncreasingSubSeqThree instance = new IncreasingSubSeqThree();
        int[] expResult = {0, 1, 2};
        int[] result = instance.increasingSubSeq(input);
        assertArrayEquals(expResult, result);
        
    }
    
    // {12, 1, 10, 5, 6, 2, 30}
    @Test
    public void testIncreasingSubSeq2() {
        System.out.println("increasingSubSeq");
        int[] input = {12, 1, 10, 5, 6, 2, 30};
        IncreasingSubSeqThree instance = new IncreasingSubSeqThree();
        int[] expResult = {1, 2, 6};
        int[] result = instance.increasingSubSeq(input);
        assertArrayEquals(expResult, result);
        
    }
}
