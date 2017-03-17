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
public class SumOfTwoTest {
    
    public SumOfTwoTest() {
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
     * Test of sumOfTwoOnSort method, of class SumOfTwo.
     */
    @Test
    public void testSumOfTwoOnSort() {
        
        System.out.println("sumOfTwoOnSort");
        int[] inputArray = new int[]{13, 56, 81, 67, 19, 0};
        int sum = 100;
        SumOfTwo instance = new SumOfTwo();
        int[] expResult = new int[]{19, 81};
        int[] result = instance.sumOfTwoOnSort(inputArray, sum);
        Arrays.sort(result);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of sumOfTwo method, of class SumOfTwo.
     */
    @Test
    public void testSumOfTwo() {
        System.out.println("sumOfTwo");
        int[] inputArray = new int[]{13, 56, 81, 67, 19, 0};
        int sum = 100;
        SumOfTwo instance = new SumOfTwo();
        int[] expResult = new int[]{19, 81};
        int[] result = instance.sumOfTwo(inputArray, sum);
        Arrays.sort(result);
        assertArrayEquals(expResult, result);
    }
    
}
