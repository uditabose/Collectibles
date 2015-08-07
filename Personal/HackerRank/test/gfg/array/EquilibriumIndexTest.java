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
public class EquilibriumIndexTest {
    
    public EquilibriumIndexTest() {
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
     * Test of equilibriumIndex method, of class EquilibriumIndex.
     */
    @Test
    public void testEquilibriumIndex() {
        
        // A[0] = -7, A[1] = 1, A[2] = 5, A[3] = 2, A[4] = -4, A[5] = 3, A[6]=0
        System.out.println("equilibriumIndex");
        int[] input = {-7, 1, 5, 2, -4, 3, 0};
        EquilibriumIndex instance = new EquilibriumIndex();
        int expResult = 3;
        int result = instance.equilibriumIndex(input);
        assertEquals(expResult, result);
        
    }
    
}
