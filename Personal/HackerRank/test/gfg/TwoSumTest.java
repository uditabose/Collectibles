/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfg;

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
public class TwoSumTest {
    
    public TwoSumTest() {
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
     * Test of twoSum method, of class TwoSum.
     */
    @Test
    public void testTwoSum1() {
        System.out.println("twoSum1");
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        TwoSum instance = new TwoSum();
        int[] expResult = new int[]{1, 2};
        int[] result = instance.twoSum(nums, target);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testTwoSum2() {
        System.out.println("twoSum2");
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 18;
        TwoSum instance = new TwoSum();
        int[] expResult = new int[]{2, 3};
        int[] result = instance.twoSum(nums, target);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testTwoSum3() {
        System.out.println("twoSum3");
        int[] nums = new int[]{-2, 12, -11, 115, 23, 11};
        int target = 0;
        TwoSum instance = new TwoSum();
        int[] expResult = new int[]{3, 6};
        int[] result = instance.twoSum(nums, target);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testTwoSum4() {
        System.out.println("twoSum4");
        int[] nums = new int[]{12, 17, 11, 15};
        int target = 9;
        TwoSum instance = new TwoSum();
        int[] expResult = null;
        int[] result = instance.twoSum(nums, target);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testTwoSum5() {
        System.out.println("twoSum5");
        int[] nums = new int[]{21, 701, 191, 1150, 1, 12, -921};
        int target = -900;
        TwoSum instance = new TwoSum();
        int[] expResult = new int[]{1, 7};
        int[] result = instance.twoSum(nums, target);
        assertArrayEquals(expResult, result);
        
    }
    
    
    
}
