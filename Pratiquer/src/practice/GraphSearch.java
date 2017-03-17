
package practice;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author papa2
 */
public class GraphSearch {
    
    public static void main(String[] args) {
        int[][] values = new int[6][];
        
        values[0] = new int[]{2, 3};
        values[1] = new int[]{1, 2, 5};
        values[2] = new int[]{4};
        values[3] = new int[]{1, 2, 4, 5};
        values[4] = new int[]{3, 5};
        values[5] = new int[]{1, 4};
        
        GraphAdjList gal = new GraphAdjList();
        gal.buildmap(values);
        
        doBFS(gal);
        
        System.out.println(gal);
        
        System.out.println("========================");
        gal = new GraphAdjList();
        gal.buildmap(values);
        doDFS(gal);
        System.out.println(gal);
    }

    private static void doBFS(GraphAdjList gal) {
        GNode root = gal.getNode();
        Queue<GNode> nodeQ = new LinkedList<>();
        root.distance = 0;
        nodeQ.offer(root);

        while(!nodeQ.isEmpty()) {
            GNode node = nodeQ.poll();
            
            for (GNode adj : gal.getAdjNodes(node)) {
                if (adj.state == -1) {
                    adj.state = 0;
                    adj.distance = node.distance + 1;
                    nodeQ.add(adj);
                }
            }
            
            node.state = 1;
        }
        
    }

    private static void doDFS(GraphAdjList gal) {
        Stack<GNode> nodeS = new Stack<>();
        GNode root = gal.getNode();
        int time = 1;
        root.state = 0;
        root.inTime = time;
        nodeS.push(root);

        while(!nodeS.isEmpty()) {
            GNode node = nodeS.pop();
            
            for (GNode adj : gal.getAdjNodes(node)) {
                if (adj.state == -1) {
                    adj.state = 0;
                    adj.inTime = ++time;
                    nodeS.push(adj); 
                } 
            }
            node.state = 1;
            node.outTime = ++time;
        }
    }
}



