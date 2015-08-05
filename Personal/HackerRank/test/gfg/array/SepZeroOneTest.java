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
public class SepZeroOneTest {
    
    public SepZeroOneTest() {
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
     * Test of separateByPush method, of class SepZeroOne.
     */
    @Test
    public void testSeparateByPush() {
        System.out.println("separateByPush");
        int[] input = new int[]{1, 0, 1, 1, 0, 0, 0, 1};
        SepZeroOne instance = new SepZeroOne();
        int[] expResult = new int[]{0, 0, 0, 0, 1, 1, 1, 1};
        int[] result = instance.separateByPush(input);
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of separateByCounting method, of class SepZeroOne.
     */
    @Test
    public void testSeparateByCounting() {
        System.out.println("separateByCounting");
        int[] input = new int[]{1, 0, 1, 1, 0, 0, 0, 1};
        SepZeroOne instance = new SepZeroOne();
        int[] expResult = new int[]{0, 0, 0, 0, 1, 1, 1, 1};
        int[] result = instance.separateByCounting(input);
        assertArrayEquals(expResult, result);
        
    }
    
    //0 1 0 1 0 0 1 1 1 0

    @Test
    public void testSeparateByPush1() {
        System.out.println("separateByPush");
        int[] input = new int[]{0, 1, 0, 1, 0, 0, 1, 1, 1, 0};
        gfg.array.SepZeroOne instance = new gfg.array.SepZeroOne();
        int[] expResult = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
        int[] result = instance.separateByPush(input);
        assertArrayEquals(expResult, result);
        
    }

    /**
     * Test of separateByCounting method, of class SepZeroOne.
     */
    @Test
    public void testSeparateByCounting1() {
        System.out.println("separateByCounting");
        int[] input = new int[]{0, 1, 0, 1, 0, 0, 1, 1, 1, 0};
        gfg.array.SepZeroOne instance = new gfg.array.SepZeroOne();
        int[] expResult = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
        int[] result = instance.separateByCounting(input);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testSeparateByPush2() {
        System.out.println("separateByPush");
        int[] input = new int[]{1, 1, 1, 1, 1, 0, 0, 0, 0, 0};
        gfg.array.SepZeroOne instance = new gfg.array.SepZeroOne();
        int[] expResult = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 1};
        int[] result = instance.separateByPush(input);
        assertArrayEquals(expResult, result);
        
    }
}
