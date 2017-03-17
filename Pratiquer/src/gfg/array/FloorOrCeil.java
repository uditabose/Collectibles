

package gfg.array;

/**
 * http://www.geeksforgeeks.org/search-floor-and-ceil-in-a-sorted-array/
 * @author Udita
 */
public class FloorOrCeil {
    
    public int[] floorAndCeil(int[] input, int searchable) {
        int[] floorAndCeil = new int[2];
        
        floorAndCeil[0] = findFloor(input, searchable);
        floorAndCeil[1] = findCeil(input, searchable);
        
        return floorAndCeil;
    }

    private int findFloor(int[] input, int searchable) {
        return findFloor0(input, 0, input.length - 1, searchable);
        
    }

    private int findCeil(int[] input, int searchable) {
        return findCeil0(input, 0, input.length - 1, searchable);
    }

    private int findFloor0(int[] input, int start, int end, int searchable) {
        if (start == end) {
            return (searchable >= input[start]) ? input[start] : -1;
        } else if (end - start == 1) {
            if (input[start] == searchable) {
                return input[start];
            } else if (input[end] == searchable) {
                return input[end];
            } else if (input[start] < searchable && input[end] > searchable) {
                return input[start];
            } else if ((input[start] < searchable && input[end] <  searchable)) {
                if (searchable - input[start] < searchable - input[end]) {
                    return input[start];
                } else {
                    return input[end];
                }
                
            } else if (input[start] > searchable && (start - 1) > 0 && input[start - 1] < searchable) {
                return input[start - 1];
            } else {
                return -1;
            }
            
        }
        
        int mid = start + (end - start)/2;
        if (input[mid] == searchable) {
            return searchable;
        } else if (input[mid] < searchable) {
            return findFloor0(input, mid + 1, end, searchable);
        } else {
            return findFloor0(input, start, mid, searchable);
        }
        
    }

    private int findCeil0(int[] input, int start, int end, int searchable) {
        if (start == end) {
            return (input[start] >= searchable) ? input[start] : -1 ;
        } else if (end - start == 1) {
            if (input[start] == searchable) {
                return input[start];
            } else if (input[end] == searchable) {
                return input[end];
            } else if (input[start] < searchable && input[end] > searchable) {
                return input[end];
            } else if (input[start] > searchable && input[end] > searchable) {
                if (input[start] - searchable < input[end] - searchable) {
                    return input[start];
                } else {
                    return input[end];
                }
                
            } else if (input[end] < searchable && (end + 1) < input.length && input[end + 1] > searchable) {
                return input[end + 1];
            } else {
                return -1;
            }
        }
        
        int mid = start + (end - start)/2;
        if (input[mid] == searchable) {
            return searchable;
        } else if (input[mid] < searchable) {
            return findCeil0(input, mid + 1, end, searchable);
        } else {
            return findCeil0(input, start, mid, searchable);
        }
    }

}
