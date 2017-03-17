
package practice;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author papa2
 */
public class GraphAdjList implements Iterable<Map.Entry<GNode, List<GNode>>> {
    private final Map<GNode, List<GNode>> adjList = new LinkedHashMap<>();
    
    public void buildmap(int[][] graphValue) {

        GNode key = null;
        List<GNode> adjs = null;
        for (int i = 0; i < graphValue.length; i++) {
            key = new GNode(i + 1);
            adjs = new LinkedList<>();
            adjList.put(key, adjs);
            
            for (int j = 0; j < graphValue[i].length; j++) {
                adjs.add(new GNode(graphValue[i][j]));
            }
        }
    }
    
    public GNode getNode() {
        int random = (int) (Math.random() * Integer.MAX_VALUE % adjList.size());
        return new GNode(random);
    }
    
    public List<GNode> getAdjNodes(GNode node) {
        for (Map.Entry<GNode, List<GNode>> adjs : this) {
            if (adjs.getKey().value == node.value) {
                return adjs.getValue();
            }
        }
        return null;
                
        //return adjList.get(node);
    }

    @Override
    public String toString() {
        return "GraphAdjList{" + "adjList=" + adjList + '}';
    }

    @Override
    public Iterator<Map.Entry<GNode, List<GNode>>> iterator() {
        return adjList.entrySet().iterator();
    }

}

class GNode {
    int value;
    int state;
    int distance;
    int inTime;
    int outTime;

    public GNode(int value) {
        this.value = value;
        this.state = -1;
        this.distance = Integer.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass() || obj.getClass() != Integer.class) {
            return false;
        }
        
        if (obj.getClass() == obj.getClass()) {
            final GNode other = (GNode) obj;
            if (!Objects.equals(this.value, other.value)) {
                return false;
            } else {
                return true;
            }
        } else if (obj.getClass() == Integer.class) {
            return (this.value == (Integer) obj);
        }
        
        return false;
    }

    @Override
    public String toString() {
        String tabs = "";
        if (distance != Integer.MAX_VALUE) {
            for (int i = 0; i < distance; i++) {
                tabs += "\t";
                
            }
        }
        return tabs + "{" + "v=" + value + ", i/o=" + inTime + "/" + outTime + "}\n";
    }

}