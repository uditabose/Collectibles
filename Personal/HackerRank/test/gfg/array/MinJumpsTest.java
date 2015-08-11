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
public class MinJumpsTest {
    
    public MinJumpsTest() {
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
     * Test of minJumps method, of class MinJumps.
     */
    @Test
    public void testMinJumps() {
        System.out.println("minJumps");
        int[] input = {1, 3, 6, 1, 0, 9};
        MinJumps instance = new MinJumps();
        int expResult = 3;
        int result = instance.minJumps(input);
        assertEquals(expResult, result);
        
    }
    
}
