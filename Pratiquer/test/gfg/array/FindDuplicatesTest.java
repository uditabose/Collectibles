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
public class FindDuplicatesTest {
    
    public FindDuplicatesTest() {
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
     * Test of findDuplicates method, of class FindDuplicates.
     */
    @Test
    public void testFindDuplicates() {
        System.out.println("findDuplicates");
        int[] input = {1, 2, 4, 1, 3};
        FindDuplicates instance = new FindDuplicates();
        Integer[] expResult = {1};
        Integer[] result = instance.findDuplicates(input);
        assertArrayEquals(expResult, result);
        
    }
    
}
