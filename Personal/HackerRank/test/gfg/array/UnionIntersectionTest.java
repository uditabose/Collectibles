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
public class UnionIntersectionTest {
    
    public UnionIntersectionTest() {
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
     * Test of unionIntersection method, of class UnionIntersection.
     */
    @Test
    public void testUnionIntersection() {
        System.out.println("unionIntersection");
        int[] aArray = new int[]{1, 3, 4, 5, 7};
        int[] bArray = new int[]{2, 3, 5, 6};
        UnionIntersection instance = new UnionIntersection();
        int[][] expResult = new int[][]{{1, 2, 3, 4, 5, 6, 7, 0, 0}, {3, 5, 0, 0}};
        int[][] result = instance.unionIntersection(aArray, bArray);
        assertArrayEquals(expResult, result);
        
    }
    
}
