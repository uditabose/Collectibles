/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc;

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
public class CC14Test {
    
    public CC14Test() {
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
     * Test of replaceBlank method, of class CC14.
     */
    @Test
    public void testReplaceBlank() {
        System.out.println("replaceBlank");
        char[] theString = "Mr John Smith    ".toCharArray();
        CC14 instance = new CC14();
        String expResult = "Mr%20John%20Smith";
        String result = instance.replaceBlank(theString);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testReplaceBlankWhoops() {
        System.out.println("replaceBlank");
        char[] theString = " Mr John Smith      ".toCharArray();
        CC14 instance = new CC14();
        String expResult = "%20Mr%20John%20Smith";
        String result = instance.replaceBlank(theString);
        assertEquals(expResult, result);
    }
    
}
