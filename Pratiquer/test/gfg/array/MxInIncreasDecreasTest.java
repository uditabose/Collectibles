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
public class MxInIncreasDecreasTest {
    
    public MxInIncreasDecreasTest() {
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
     * Test of findMaxInIncrDecr method, of class MxInIncreasDecreas.
     */
    @Test
    public void testFindMaxInIncrDecr() {
        System.out.println("findMaxInIncrDecr");
        int[] input = {8, 10, 20, 80, 100, 200, 400, 500, 3, 2, 1};
        MxInIncreasDecreas instance = new MxInIncreasDecreas();
        int expResult = 500;
        int result = instance.findMaxInIncrDecr(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindMaxInIncrDecr1() {
        System.out.println("findMaxInIncrDecr");
        int[] input = {1, 3, 50, 10, 9, 7, 6};
        MxInIncreasDecreas instance = new MxInIncreasDecreas();
        int expResult = 50;
        int result = instance.findMaxInIncrDecr(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindMaxInIncrDecr2() {
        System.out.println("findMaxInIncrDecr");
        int[] input = {10, 20, 30, 40, 50};
        MxInIncreasDecreas instance = new MxInIncreasDecreas();
        int expResult = 50;
        int result = instance.findMaxInIncrDecr(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindMaxInIncrDecr3() {
        System.out.println("findMaxInIncrDecr");
        int[] input = {120, 100, 80, 20, 0};
        MxInIncreasDecreas instance = new MxInIncreasDecreas();
        int expResult = 120;
        int result = instance.findMaxInIncrDecr(input);
        assertEquals(expResult, result);
        
    }
    
}
