/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
public class SortUtilTest {
    
    public SortUtilTest() {
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
     * Test of mergeSort method, of class SortUtil.
     */
    @Test
    public void testMergeSort() {
        System.out.println("mergeSort");
        int[] input = new int[]{20, 50, 10, 40, 70};
        SortUtil.mergeSort(input);
        int[] result = new int[]{10, 20, 40, 50, 70};
        assertArrayEquals(input, result);
    }

    /**
     * Test of quickSort method, of class SortUtil.
     */
    @Test
    public void testQuickSort() {
        System.out.println("quickSort");
        int[] input = new int[]{20, 50, 10, 40, 70};
        SortUtil.quickSort(input);
        int[] result = new int[]{10, 20, 40, 50, 70};
        assertArrayEquals(input, result);
    }
    
}
