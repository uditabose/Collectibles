/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfg.array;

import java.util.Arrays;
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
public class ReplaceByNextGreatestTest {
    
    public ReplaceByNextGreatestTest() {
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
     * Test of replaceByNextGreatest method, of class ReplaceByNextGreatest.
     */
    @Test
    public void testReplaceByNextGreatest() {
        System.out.println("replaceByNextGreatest");
        int[] input = {16, 17, 4, 3, 5, 2};
        ReplaceByNextGreatest instance = new ReplaceByNextGreatest();
        int[] expResult =  {17, 5, 5, 5, 2, -1};
        int[] result = instance.replaceByNextGreatest(input);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult, result);
        
    }
    
}
