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
public class ReverseArrayTest {
    
    public ReverseArrayTest() {
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
     * Test of reverse method, of class ReverseArray.
     */
    @Test
    public void testReverseOdd() {
        System.out.println("reverse");
        int[] toReverse = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        ReverseArray instance = new ReverseArray();
        int[] expResult = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] result = instance.reverse(toReverse);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testReverseEven() {
        System.out.println("reverse");
        int[] toReverse = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        ReverseArray instance = new ReverseArray();
        int[] expResult = new int[]{8, 7, 6, 5, 4, 3, 2, 1};
        int[] result = instance.reverse(toReverse);
        assertArrayEquals(expResult, result);
        
    }
    
}
