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
public class MergeMNTest {
    
    public MergeMNTest() {
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
     * Test of mergeMNInPlace method, of class MergeMN.
     */
    @Test
    public void testMergeMNInPlace() {
        System.out.println("mergeMNInPlace");
        int[] mnArray = new int[]{3, 4, -1, -1, 7, -1, 9, 10, -1, -1, -1, 25};
        int[] nArray = new int[]{8, 9, 12, 15, 20, 22};
        MergeMN instance = new MergeMN();
        int[] expResult = new int[]{3, 4, 7, 8, 9, 9, 10, 12, 15, 20, 22, 25};
        int[] result = instance.mergeMNInPlace(mnArray, nArray);
        assertArrayEquals(expResult, result);
        
    }
    
}
