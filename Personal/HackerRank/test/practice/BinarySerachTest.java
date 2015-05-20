/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author papa2
 */
public class BinarySerachTest {
    
    BinarySerach instance = null;
    public BinarySerachTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new BinarySerach();
    }
    
    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of search method, of class BinarySerach.
     */
    @Test
    public void testSearchLessThanLeastOdd() {
        System.out.println("search");
        int aNum = 12;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchHigherThanMostOdd() {
        System.out.println("search");
        int aNum = 50;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchLessThanMiddleOdd() {
        System.out.println("search");
        int aNum = 19;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchMoreThanMiddleOdd() {
        System.out.println("search");
        int aNum = 26;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactlyMiddleOdd() {
        System.out.println("search");
        int aNum = 24;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactLeftOfMiddleOdd() {
        System.out.println("search");
        int aNum = 20;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactRightOfMiddleOdd() {
        System.out.println("search");
        int aNum = 24;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactLeftOdd() {
        System.out.println("search");
        int aNum = 15;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactRightOdd() {
        System.out.println("search");
        int aNum = 31;
        int[] anArray = new int[]{15, 20, 24, 28, 31};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    //// ----------------- Even array test -------------------------
    
     @Test
    public void testSearchLessThanLeastEven() {
        System.out.println("search");
        int aNum = 12;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchHigherThanMostEven() {
        System.out.println("search");
        int aNum = 50;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchLessThanMiddleEven() {
        System.out.println("search");
        int aNum = 19;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchMoreThanMiddleEven() {
        System.out.println("search");
        int aNum = 26;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = false;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactlyMiddleEven() {
        System.out.println("search");
        int aNum = 24;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactlyMiddleEven2() {
        System.out.println("search");
        int aNum = 28;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactLeftOfMiddleEven() {
        System.out.println("search");
        int aNum = 20;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactRightOfMiddleEven() {
        System.out.println("search");
        int aNum = 31;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactLeftEven() {
        System.out.println("search");
        int aNum = 15;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSearchExactRightEven() {
        System.out.println("search");
        int aNum = 46;
        int[] anArray = new int[]{15, 20, 24, 28, 31, 46};
        
        boolean expResult = true;
        boolean result = instance.search(aNum, anArray);
        assertEquals(expResult, result);
    }
    
}
