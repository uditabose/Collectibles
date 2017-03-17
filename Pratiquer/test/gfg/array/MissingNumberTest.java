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
public class MissingNumberTest {
    
    public MissingNumberTest() {
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
     * Test of missingNumber method, of class MissingNumber.
     */
    @Test
    public void testMissingNumber() {
        System.out.println("missingNumber");
        int[] input = new int[]{1, 2, 4, 6, 3, 7, 8}; // 1, 2, 4, ,6, 3, 7, 8
        MissingNumber instance = new MissingNumber();
        int expResult = 5;
        int result = instance.missingNumber(input);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of missingNumberXOR method, of class MissingNumber.
     */
    @Test
    public void testMissingNumberXOR() {
        System.out.println("missingNumberXOR");
        int[] input = new int[]{1, 2, 4, 6, 3, 7, 8}; 
        MissingNumber instance = new MissingNumber();
        int expResult = 5;
        int result = instance.missingNumberXOR(input);
        assertEquals(expResult, result);
        
    }
    
}
