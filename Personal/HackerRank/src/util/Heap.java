

package util;

/**
 *
 * @author Udita
 */
public class Heap {
    private HeapNode[] nodes;
    private int length = 0;
    private int max = 0;
    private Type type;
    
    public enum Type {
        Max,
        Min
    }

    public Heap(int max, Type t) {
        this.max = max;
        this.nodes = new HeapNode[max];
        this.type = t;
    }
    
    public int parent(int pos) {
        return (int)Math.floor((pos - 1)/2);
    }
    
    public int left(int pos) {
        return (2 * pos + 1);
    }
    
    public int right(int pos) {
        return (2 * pos + 2);
    }
    
    public boolean less(HeapNode left, HeapNode right) {
        return (left.data < right.data);
    }
    
    public boolean more(HeapNode left, HeapNode right) {
        return (left.data > right.data);
    }
    
    public int head() {
        return nodes[0].data;
    }
    
    public void insert(int nData) {
        if (length == max) {
            return;
        }
        
        nodes[length++] = new HeapNode(nData);
        heapiFy();
    }
    
    public int extractHead() {
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
    
    public void heapiFy() {
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

    public void printHeap() {
        for (HeapNode node : nodes) {
            System.out.printf("%d, ", node.data);
        }
        System.out.println("");
    }
}

class HeapNode {
    int data;
  
    public HeapNode(int data) {
        this.data = data;
    }
}
