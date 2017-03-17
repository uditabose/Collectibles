package gfg.array;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uditabose
 */
public class MergeInSeparateTest {
    
    public MergeInSeparateTest() {
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
     * Test of mergeInSeparate method, of class MergeInSeparate.
     */
    @Test
    public void testMergeInSeparate() {
        System.out.println("mergeInSeparate");
        int[] array1 = {1, 5, 9, 10, 15, 20};
        int[] array2 = {2, 3, 8, 13};
        MergeInSeparate instance = new MergeInSeparate();
        int[][] expResult = {{1, 2, 3, 5, 8, 9}, {10, 13, 15, 20}};
        int[][] result = instance.mergeInSeparate(array1, array2);
        //assertArrayEquals(expResult[0], result[0]);
        //assertArrayEquals(expResult[1], result[1]);
        assertArrayEquals(expResult, result);
    }
    
}
