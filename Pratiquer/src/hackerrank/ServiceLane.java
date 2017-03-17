

package hackerrank;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

/**
 *
 * @author Udita
 */
public class ServiceLane {
    
    public static void main(String[] args) {
        Scanner scanner =  new Scanner(System.in);
        long lengthOfFreeway = -1;
        int noOfTestCase = -1;
        List<Integer> sectionWidth = new ArrayList<Integer>();
        List<String[]> testCases = new ArrayList<String[]>();

        String[] tempSplitted = null;
        int lineCount = 0;
        while(scanner.hasNext()) {
            tempSplitted = scanner.nextLine().split(" ");
            if (lineCount == 0) {
                lengthOfFreeway = Long.parseLong(tempSplitted[0]);
                noOfTestCase = Integer.parseInt(tempSplitted[1]);
                
                if (lengthOfFreeway < 2 || lengthOfFreeway > 100000) {
                    System.err.println("Length of freeway is not within range");
                    System.exit(1);
                }
                
                if (noOfTestCase < 1 || noOfTestCase > 1000) {
                    System.err.println("No of test cases ot within range");
                    System.exit(1);
                }
            } else if (lineCount == 1) {
                for (String widthSt : tempSplitted) {
                    sectionWidth.add(new Integer(widthSt));
                }
            } else {
                if (testCases.size() <= noOfTestCase) {
                    testCases.add(tempSplitted);
                }
                
                if (testCases.size() == noOfTestCase) {
                    break;
                }
            }

            lineCount++;
        }
        
        List<Integer> testSublist = null;
        for (String[] testCase : testCases) {
            testSublist = sectionWidth.subList(Integer.parseInt(testCase[0]), Integer.parseInt(testCase[1]) + 1);
            System.out.println(Collections.min(testSublist));
        }
    }
}
