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
public class AllLeadersTest {
    
    public AllLeadersTest() {
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
     * Test of findLeaders method, of class AllLeaders.
     */
    @Test
    public void testFindLeaders() {
        System.out.println("findLeaders");
        int[] input = new int[]{16, 17, 4, 3, 5, 2};
        AllLeaders instance = new AllLeaders();
        int[] expResult = new int[]{17, 17, 5, 5, 5, 2};
        int[] result = instance.findLeaders(input);
        assertArrayEquals(expResult, result);
        
    }
    
}
