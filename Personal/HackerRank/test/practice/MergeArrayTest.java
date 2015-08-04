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
public class MergeArrayTest {
    
    public MergeArrayTest() {
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
     * Test of merge method, of class MergeArray.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        int[] array1 = new int[]{20, 30, 40};
        int[] array2 = new int[]{25, 35, 45};
        MergeArray instance = new MergeArray();
        int[] expResult = new int[]{20, 25, 30, 35, 40, 45};
        int[] result = instance.merge(array1, array2);
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testMerge1() {
        System.out.println("merge1");
        int[] array1 = new int[]{20, 30, 40};
        int[] array2 = new int[]{20, 30, 40};
        MergeArray instance = new MergeArray();
        int[] expResult = new int[]{20, 20, 30, 30, 40, 40};
        int[] result = instance.merge(array1, array2);
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testMerge2() {
        System.out.println("merge2");
        int[] array1 = new int[]{20, 30, 40};
        int[] array2 = new int[]{120, 130, 140};
        MergeArray instance = new MergeArray();
        int[] expResult = new int[]{20, 30, 40, 120, 130, 140};
        int[] result = instance.merge(array1, array2);
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testMerge3() {
        System.out.println("merge3");
        int[] array1 = new int[]{120, 130, 140};
        int[] array2 = new int[]{20, 30, 40};
        MergeArray instance = new MergeArray();
        int[] expResult = new int[]{20, 30, 40, 120, 130, 140};
        int[] result = instance.merge(array1, array2);
        assertArrayEquals(expResult, result);
    }
    
    @Test
    public void testMerge4() {
        System.out.println("merge4");
        int[] array1 = new int[]{20, 23, 32};
        int[] array2 = new int[]{24, 30, 41};
        MergeArray instance = new MergeArray();
        int[] expResult = new int[]{20, 23, 24, 30, 32, 41};
        int[] result = instance.merge(array1, array2);
        assertArrayEquals(expResult, result);
    }
}
