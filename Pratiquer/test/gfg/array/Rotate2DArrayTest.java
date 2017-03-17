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
public class Rotate2DArrayTest {
    
    public Rotate2DArrayTest() {
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
     * Test of rotateBy90 method, of class Rotate2DArray.
     */
    @Test
    public void testRotateBy90() {
        System.out.println("rotateBy90");
        char[][] image = {{'*', '^', '*'}, 
                        {'*', '|', '*'},
                        {'*', '|', '*'}};
                        
        Rotate2DArray instance = new Rotate2DArray();
        char[][] expResult = {{'*', '*', '*'}, 
                        {'|', '|', '^'},
                        {'*', '*', '*'}};
        char[][] result = instance.rotateBy90(image);
        assertArrayEquals(expResult, result);
        
    }
    
}
