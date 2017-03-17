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
public class FloorOrCeilTest {
    
    public FloorOrCeilTest() {
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
     * Test of floorAndCeil method, of class FloorOrCeil.
     */
    @Test
    public void testFloorAndCeil() {
        System.out.println("floorAndCeil");
        int[] input = new int[]{1, 2, 8, 10, 10, 12, 19};
        int searchable = 5;
        FloorOrCeil instance = new FloorOrCeil();
        int[] expResult = new int[]{2, 8};
        int[] result = instance.floorAndCeil(input, searchable);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testFloorAndCeil1() {
        System.out.println("floorAndCeil1");
        int[] input = new int[]{1, 2, 8, 10, 10, 12, 19};
        int searchable = 0;
        FloorOrCeil instance = new FloorOrCeil();
        int[] expResult = new int[]{-1, 1};
        int[] result = instance.floorAndCeil(input, searchable);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testFloorAndCeil2() {
        System.out.println("floorAndCeil");
        int[] input = new int[]{1, 2, 8, 10, 10, 12, 19};
        int searchable = 1;
        FloorOrCeil instance = new FloorOrCeil();
        int[] expResult = new int[]{1, 1};
        int[] result = instance.floorAndCeil(input, searchable);
        assertArrayEquals(expResult, result);
        
    }
    
    @Test
    public void testFloorAndCeil3() {
        System.out.println("floorAndCeil");
        int[] input = new int[]{1, 2, 8, 10, 10, 12, 19};
        int searchable = 20;
        FloorOrCeil instance = new FloorOrCeil();
        int[] expResult = new int[]{19, -1};
        int[] result = instance.floorAndCeil(input, searchable);
        assertArrayEquals(expResult, result);
        
    }
    
}
