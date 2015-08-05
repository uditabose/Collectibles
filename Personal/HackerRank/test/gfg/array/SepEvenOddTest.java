/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfg.array;

import java.util.Arrays;
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
public class SepEvenOddTest {
    
    public SepEvenOddTest() {
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
     * Test of separateEvenOdd method, of class SepEvenOdd.
     */
    @Test
    public void testSeparateEvenOdd() {
        System.out.println("separateEvenOdd");
        int[] input = new int[]{12, 34, 45, 9, 8, 90, 3};
        SepEvenOdd instance = new SepEvenOdd();
        int[] expResult1 = new int[]{8, 12, 34, 90};
        int[] expResult2 = new int[]{3, 9, 45};
        int[] result = instance.separateEvenOdd(input);
        int[] result1 = Arrays.copyOfRange(result, 0, 4);
        Arrays.sort(result1);
        int[] result2 = Arrays.copyOfRange(result, 4, result.length);
        Arrays.sort(result2);
        assertArrayEquals(expResult1, result1);
        assertArrayEquals(expResult2, result2);
        
    }
    
}
