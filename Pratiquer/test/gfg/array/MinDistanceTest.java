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
public class MinDistanceTest {
    
    public MinDistanceTest() {
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
     * Test of findMinDistance method, of class MinDistance.
     */
    @Test
    public void testFindMinDistance() {
        System.out.println("findMinDistance");
        int[] input = {3, 5, 4, 2, 6, 5, 6, 6, 5, 4, 8, 3};
        int a = 3;
        int b = 6;
        MinDistance instance = new MinDistance();
        int expResult = 4;
        int result = instance.findMinDistance(input, a, b);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindMinDistance1() {
        System.out.println("findMinDistance");
        int[] input =  {1, 2};
        int a = 1;
        int b = 2;
        MinDistance instance = new MinDistance();
        int expResult = 1;
        int result = instance.findMinDistance(input, a, b);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testFindMinDistance2() {
        System.out.println("findMinDistance");
        int[] input =  {3, 4, 5};
        int a = 3;
        int b = 5;
        MinDistance instance = new MinDistance();
        int expResult = 2;
        int result = instance.findMinDistance(input, a, b);
        assertEquals(expResult, result);
        
    }
    // 
    
    @Test
    public void testFindMinDistance3() {
        System.out.println("findMinDistance");
        int[] input = {2, 5, 3, 5, 4, 4, 2, 3};
        int a = 3;
        int b = 2;
        MinDistance instance = new MinDistance();
        int expResult = 1;
        int result = instance.findMinDistance(input, a, b);
        assertEquals(expResult, result);
        
    }
    
}
