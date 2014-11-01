/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.promodoro.logic;

import learning.promodoro.store.PromodoroBean;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaelgerstenfeld
 */
public class PromodoroBeanTest {
    
    public PromodoroBeanTest() {
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
     * Test of getId method, of class PromodoroBean.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        PromodoroBean instance = new PromodoroBean();
        int expResult = 0;
        int result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class PromodoroBean.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 0;
        PromodoroBean instance = new PromodoroBean();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescription method, of class PromodoroBean.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        PromodoroBean instance = new PromodoroBean();
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDescription method, of class PromodoroBean.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        PromodoroBean instance = new PromodoroBean();
        instance.setDescription(description);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isHasStarted method, of class PromodoroBean.
     */
    @Test
    public void testIsHasStarted() {
        System.out.println("isHasStarted");
        PromodoroBean instance = new PromodoroBean();
        boolean expResult = false;
        boolean result = instance.isHasStarted();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHasStarted method, of class PromodoroBean.
     */
    @Test
    public void testSetHasStarted() {
        System.out.println("setHasStarted");
        boolean hasStarted = false;
        PromodoroBean instance = new PromodoroBean();
        instance.setHasStarted(hasStarted);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPriority method, of class PromodoroBean.
     */
    @Test
    public void testGetPriority() {
        System.out.println("getPriority");
        PromodoroBean instance = new PromodoroBean();
        int expResult = 0;
        int result = instance.getPriority();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPriority method, of class PromodoroBean.
     */
    @Test
    public void testSetPriority() {
        System.out.println("setPriority");
        int priority = 0;
        PromodoroBean instance = new PromodoroBean();
        instance.setPriority(priority);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeLeft method, of class PromodoroBean.
     */
    @Test
    public void testGetTimeLeft() {
        System.out.println("getTimeLeft");
        PromodoroBean instance = new PromodoroBean();
        long expResult = 0L;
        long result = instance.getTimeLeft();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTimeLeft method, of class PromodoroBean.
     */
    @Test
    public void testSetTimeLeft() {
        System.out.println("setTimeLeft");
        long timeLeft = 0L;
        PromodoroBean instance = new PromodoroBean();
        instance.setTimeLeft(timeLeft);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PromodoroBean.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PromodoroBean instance = new PromodoroBean();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
