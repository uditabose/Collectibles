

package practice;

import java.util.Arrays;

/**
 *
 * @author Udita
 */
public class Heapy {
    public static void main(String[] args) {
        int[] toBeHeaped = //{8, 12, 9, 7, 22, 3, 26, 14, 11, 15, 22};
            {7, 10, 4, 3, 20, 15};
        /*Heap heap = new Heap(toBeHeaped.length, Heap.Type.Max);
        for (int u : toBeHeaped) {
            heap.insert(u);
        }
        heap.printHeap();
        
        heap = new Heap(toBeHeaped.length, Heap.Type.Min);
        for (int u : toBeHeaped) {
            heap.insert(u);
        }
        heap.printHeap();*/
        
        for (int i = 0; i < toBeHeaped.length; i++) {
            findKthMinValue(toBeHeaped, i+1);
            
        }

        Arrays.sort(toBeHeaped);
        System.out.println(Arrays.toString(toBeHeaped));
        
        
    }

    private static void findKthMinValue(int[] toBeHeaped, int pos) {
        Heap heap = new Heap(pos, Heap.Type.Max);
        
        for (int i = 0; i < pos; i++) {
            heap.insert(toBeHeaped[i]);
        }
        
        for (int i = pos; i < toBeHeaped.length; i++) {
            if (heap.head() > toBeHeaped[i]) {
                heap.nodes[0].data = toBeHeaped[i];
                heap.heapiFy();
            }
        }
        
        System.out.println("K-th : " + heap.extractHead());

    }
    
}

class HeapNode {
    int data;
  
    public HeapNode(int data) {
        this.data = data;
    }
}

class Heap {
    HeapNode[] nodes;
    int length = 0;
    int max = 0;
    
    Type type;
    enum Type {
        Max,
        Min
    }

    public Heap(int max, Type t) {
        this.max = max;
        this.nodes = new HeapNode[max];
        this.type = t;
    }
    
    int parent(int pos) {
        return (int)Math.floor((pos - 1)/2);
    }
    
    int left(int pos) {
        return (2 * pos + 1);
    }
    
    int right(int pos) {
        return (2 * pos + 2);
    }
    
    boolean less(HeapNode left, HeapNode right) {
        return (left.data < right.data);
    }
    
    boolean more(HeapNode left, HeapNode right) {
        return (left.data > right.data);
    }
    
    int head() {
        return nodes[0].data;
    }
    
    void insert(int nData) {
        if (length == max) {
            return;
        }
        
        nodes[length++] = new HeapNode(nData);
        heapiFy();
    }
    
    int extractHead() {
        int ret = nodes[0].data;
        if (type == Type.Max) {
            nodes[0].data = Integer.MIN_VALUE;
        } else {
            nodes[0].data = Integer.MAX_VALUE;
        }
        
        heapiFy();
        length--;
        return ret;
    }
    
    private void swap(int curPos, int parentPos) {
        HeapNode child = nodes[curPos];
        HeapNode parent = nodes[parentPos];
        
        nodes[parentPos] = child;
        nodes[curPos] = parent;
    }

    private void minHeapify(int pos) { 
        if (pos < 0 || pos >= length) {
            return;
        }
        int left = left(pos);
        int right = right(pos);
        
        int swapPos = -1;
        
        if (left < length && less(nodes[left], nodes[pos])) {
            swapPos = left;
        } else {
            swapPos = pos;
        }
        
        if (right < length && less(nodes[right], nodes[swapPos])) {
            swapPos = right;
        }
        
        if (pos != swapPos) {
            swap(pos, swapPos);
        }
        
        minHeapify(++pos);
        
    }
    
    private void maxHeapify(int pos) {        
        if (pos < 0 || pos >= length) {
            return;
        }
        int left = left(pos);
        int right = right(pos);
        
        int swapPos = -1;
        
        if (left < length && more(nodes[left], nodes[pos])) {
            swapPos = left;
        } else {
            swapPos = pos;
        }
        
        if (right < length && more(nodes[right], nodes[swapPos])) {
            swapPos = right;
        }
        
        if (pos != swapPos) {
            swap(pos, swapPos);
        }
        
        maxHeapify(++pos);
    }
    
    void heapiFy() {
        if (type == Type.Min) {
            for (int i = length/2; i >= 0; i--) {
                minHeapify(i);
            }
        } else {
            for (int i = length/2; i >= 0; i--) {
                maxHeapify(i);
            }
        } 
    }

    void printHeap() {
        for (HeapNode node : nodes) {
            System.out.printf("%d, ", node.data);
        }
        System.out.println("");
    }
    
}