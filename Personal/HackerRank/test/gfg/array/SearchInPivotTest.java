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
public class SearchInPivotTest {
    
    public SearchInPivotTest() {
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
     * Test of searchInPivot method, of class SearchInPivot.
     */
    @Test
    public void testSearchInPivot() {
        System.out.println("searchInPivot");
        int[] input = new int[]{2, 3, 4, 5, 6, 7, 8, 1};
        int searchable = 1;
        SearchInPivot instance = new SearchInPivot();
        int expResult = 7;
        int result = instance.searchInPivot(input, searchable);
        assertEquals(expResult, result);
        
    }
    
}
