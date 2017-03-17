/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfg;

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
public class AnagramTest {
    
    public AnagramTest() {
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
     * Test of isAnagram method, of class Anagram.
     */
    @Test
    public void testIsAnagramBlank() {
        System.out.println("testIsAnagramBlank");
        String first = "";
        String second = "";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramNull() {
        System.out.println("testIsAnagramNull");
        String first = null;
        String second = "test";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramUnequalLength() {
        System.out.println("testIsAnagramUnequalLength");
        String first = "THIS";
        String second = "THISIS";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramBad() {
        System.out.println("testIsAnagramBad");
        String first = "THIS";
        String second = "THAT";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramGood() {
        System.out.println("testIsAnagramGood");
        String first = "LISTEN";
        String second = "SILENT";
        Anagram instance = new Anagram();
        boolean expResult = true;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramRepeat() {
        System.out.println("testIsAnagramRepeat");
        String first = "LISTENLISTEN";
        String second = "SILENT";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramRepeatOther() {
        System.out.println("testIsAnagramRepeatOther");
        String first = "LISTEN";
        String second = "SILENTLISTEN";
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramRepeatGood() {
        System.out.println("testIsAnagramRepeatGood");
        String first = "LISTENLISTEN";
        String second = "SILENTLISTEN";
        Anagram instance = new Anagram();
        boolean expResult = true;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testIsAnagramRandom1() {
        System.out.println("testIsAnagramRandom1");
        String first = "abad".toUpperCase();
        String second = "bcba".toUpperCase();
        Anagram instance = new Anagram();
        boolean expResult = false;
        boolean result = instance.isAnagram(first, second);
        assertEquals(expResult, result);
        
    }
//abad , bcba


    /**
     * Test of isEmpty method, of class Anagram.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        String aString = "";
        Anagram instance = new Anagram();
        boolean expResult = true;
        boolean result = instance.isEmpty(aString);
        assertEquals(expResult, result);
        
    }
    
}
