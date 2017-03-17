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
public class MajorityTest {
    
    public MajorityTest() {
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
     * Test of getMajorityViaHashMap method, of class Majority.
     */
    @Test
    public void testGetMajorityViaHashMap() {
        System.out.println("getMajorityViaHashMap");
        int[] input = new int[]{3, 3, 4, 2, 4, 4, 2, 4, 4};
        Majority instance = new Majority();
        int expResult = 4;
        int result = instance.getMajorityViaHashMap(input);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getMajorityInplace method, of class Majority.
     */
    @Test
    public void testGetMajorityInplace() {
        System.out.println("getMajorityInplace");
        int[] input = new int[]{3, 3, 4, 2, 4, 4, 2, 4, 4};
        Majority instance = new Majority();
        int expResult = 4;
        int result = instance.getMajorityInplace(input);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetMajorityViaHashMapNo() {
        System.out.println("getMajorityViaHashMapNo");
        int[] input = new int[]{3, 3, 4, 2, 4, 4, 2, 4};
        Majority instance = new Majority();
        int expResult = -1;
        int result = instance.getMajorityViaHashMap(input);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getMajorityInplace method, of class Majority.
     */
    @Test
    public void testGetMajorityInplaceNo() {
        System.out.println("getMajorityInplaceNo");
        int[] input = new int[]{3, 3, 4, 2, 4, 4, 2, 4};
        Majority instance = new Majority();
        int expResult = -1;
        int result = instance.getMajorityInplace(input);
        assertEquals(expResult, result);
        
    }
    
}
