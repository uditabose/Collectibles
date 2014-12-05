

package hackerrank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Udita
 */
public class AlmostSorted {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int noOfNumbersinSeq = 0;
        String[] sequence = null;
        int inCnt = 0;
        while (scanner.hasNext()) {
            String temp = scanner.nextLine();
            if (inCnt == 0) {
                noOfNumbersinSeq = Integer.parseInt(temp);
            } else {
                sequence = temp.split(" ");
                break;
            }
            inCnt++;
        }
        
        if (noOfNumbersinSeq != sequence.length) {
            System.err.println("Something wrong - " +  sequence.length);
            System.exit(1);
        }
        
        long[] longSeq = new long[sequence.length];
        int counter = 0;
        for (String seq : sequence) {
            longSeq[counter++] = Long.parseLong(seq);
        }
        
        int noOfSubSequence = 0;

        List<Integer> maxSeq = new ArrayList<>();
        List<Integer> minSeq = new ArrayList<>();
        for (int i = 1; i < longSeq.length - 1; i++) {
            if (longSeq[i] > longSeq[i-1] && longSeq[i] > longSeq[i+1]) {
                maxSeq.add(i);
            }
            
            if (longSeq[i] < longSeq[i-1] && longSeq[i] < longSeq[i+1]) {
                minSeq.add(i);
            }
        }
        
        System.out.println(maxSeq);
        System.out.println("-------------------------------------------");
        System.out.println(minSeq);
        noOfSubSequence = longSeq.length - maxSeq.size();
        
        for (int i = 0; i < minSeq.size(); i++) {
            noOfSubSequence += minSeq.get(i) - (i - 1);
        }/**/
        System.out.println(noOfSubSequence);
    }
}

/**
 * 100
1 4 3 5 9 7 10 6 2 11 8 12 13 17 14 15 16 18 19 20 21 22 24 26 27 23 25 28 29 
* 32 31 30 33 34 37 36 35 38 39 40 41 42 43 44 45 46 47 48 52 50 49 51 53 54 55 
* 56 57 58 59 60 62 65 63 61 68 69 64 66 71 72 67 73 70 74 75 76 77 82 79 80 81 78 83 
* 87 84 88 85 86 89 91 90 92 93 94 95 96 99 98 100 97
 */

/**
 * 100
1 4 3 5 9 7 10 6 2 11 8 12 13 17 14 15 16 18 19 20 21 22 24 26 27 23 25 28 29 32 31 30 33 34 37 36 35 38 39 40 41 42 43 44 45 46 47 48 52 50 49 51 53 54 55 56 57 58 59 60 62 65 63 61 68 69 64 66 71 72 67 73 70 74 75 76 77 82 79 80 81 78 83 87 84 88 85 86 89 91 90 92 93 94 95 96 99 98 100 97
 */

 /*int numberOfSingleSequence = 0;
        List<Long> tempSeq = new ArrayList<Long>();
        for (int i = 0; i < longSeq.length; i++) {
            tempSeq.clear();
            noOfSubSequence++;
            tempSeq.add(longSeq[i]);
            for (int j = i + 1; j < longSeq.length; j++) {
                
                tempSeq.add(longSeq[j]);
                Collections.sort(tempSeq);
                
                if (tempSeq.get(0) == longSeq[i] && tempSeq.get(tempSeq.size() - 1) == longSeq[j]) {
                    noOfSubSequence++;
                } 
            }  
        }
        int lastMax = 0;
        for (int i = 0; i < longSeq.length; i++) {
            if (i == 0) {
                noOfSubSequence = 1;
                lastMax = 1;
                continue;
            }
            if (longSeq[i] > longSeq[i - 1]) {
                noOfSubSequence += (++lastMax);
            } else {
                noOfSubSequence += 1; 
            }
            System.out.println("max = " + lastMax + " sub = " + noOfSubSequence 
            + " type - " + longSeq[i] + " <=> " + longSeq[i - 1]);
        }*/