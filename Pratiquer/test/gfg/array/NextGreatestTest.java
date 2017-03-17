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
public class NextGreatestTest {
    
    public NextGreatestTest() {
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
     * Test of nextGreatest method, of class NextGreatest.
     */
    @Test
    public void testNextGreatest() {
        System.out.println("nextGreatest");
        int[] input = {4, 5, 2, 25};
        NextGreatest instance = new NextGreatest();
        int[] expResult = {5, 25, 25, -1};
        int[] result = instance.nextGreatest(input);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testNextGreatest1() {
        System.out.println("nextGreatest");
        int[] input = {11, 13, 21, 3};
        NextGreatest instance = new NextGreatest();
        int[] expResult = {13, 21, -1, -1};
        int[] result = instance.nextGreatest(input);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testNextGreatest2() {
        System.out.println("nextGreatest");
        int[] input = {7, 4, 5, 5, 9, 2, 5, 2, 5, 7};
        NextGreatest instance = new NextGreatest();
        int[] expResult = {9,5,9,9,-1,5,7,5,7,-1};
        int[] result = instance.nextGreatest(input);
        assertArrayEquals(expResult, result);
        
    }
    
}
