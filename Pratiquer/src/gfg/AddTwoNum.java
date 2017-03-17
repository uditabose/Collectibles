

package gfg;

/**
 *
 * @author Udita
 */
public class AddTwoNum {
    
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
//        l1.next = new ListNode(4);
//        l1.next.next = new ListNode(9);
//        l1.next.next.next = new ListNode(9);
//        l1.next.next.next.next = new ListNode(2);
        
        
        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
//        l2.next.next = new ListNode(4);
        
        ListNode result = (new AddTwoNum()).addTwoNumbers(l1, l2);
        System.out.println(result);
        
    }
    public  ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null, curr = null;
        ListNode left = l1;
        ListNode right = l2;
        int carry = 0;
        while(left != null && right != null) {
            int tempSum = left.val + right.val + carry;
            if (tempSum > 9) {
                tempSum -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            
            if (curr == null) {
                curr = new ListNode(tempSum);
                result = curr;
            } else {
                curr.next = new ListNode(tempSum);
                curr = curr.next;
            }
            
            left = left.next;
            right = right.next;
        }
        
        ListNode rest = (left == null) ? right : left;
        
        if (rest == null) {
            if (carry > 0) {
                curr.next = new ListNode(carry);
                curr = curr.next;
            } 
        } else {
            while(rest != null) {
                int tempSum = rest.val + carry;
                if (tempSum > 9) {
                    tempSum -= 10;
                    carry = 1;
                } else {
                    carry = 0;
                }

                if (curr == null) {
                    curr = new ListNode(tempSum);
                    result = curr;
                } else {
                    curr.next = new ListNode(tempSum);
                    curr = curr.next;
                }

                rest = rest.next;
            }
            if (carry > 0) {
                curr.next = new ListNode(carry);
                curr = curr.next;
            } 
            
        }
        return result;
    }

}

class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; } 

    @Override
    public String toString() {
        return "ListNode{" + "val=" + val + ", next=" + next + '}';
    }
      
      
}