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
 * @author papa2
 */
public class MergeSortTest {
    private MergeSort instance = null;
    public MergeSortTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new MergeSort();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sort method, of class MergeSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");
        int[] anArr = new int[]{5, 8, 1, 10, 13, 3, 9, 15, 18, 2, 22};
        
        int[] expResult = new int[]{1, 2, 3, 5, 8, 9, 10, 13, 15, 18, 22};
        int[] result = instance.sort(anArr);
        
        assertArrayEquals(expResult, result);
    }
    
}
