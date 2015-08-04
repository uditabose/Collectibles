

package practice;
import java.util.*;
/**
 *
 * @author Udita
 */
public class SumExprn {
    public static void main(String[] args) {
        MyClass.compute_expression("(2+2)-(3-(6-5))-4");
    }
}



class MyClass {

    public static void compute_expression(String expr) {
        // Write your code here
        // To print results to the standard output please use System.out.println
        // Example: System.out.println("Hello world!");
        
        LinkedList<String> ex = new LinkedList<String>();
        char[] asChar = expr.toCharArray();
        for (int i = 0; i < asChar.length; i++) {
            if (asChar[i] == ')') {
                compute(ex);
            } else {
                ex.push(asChar[i] + "");
            }
        }
        
        compute(ex);
        System.out.println(ex.pop());
    }
    
    private static void compute(LinkedList<String> ex) {
        String expr = "*";
        String num1 = "*";
        String num2 = "*";
        
        while(!ex.isEmpty()) {
            String curr = ex.pop();
            if (curr.equals("(")) {
                break;
            }
            if (curr.equals("+") || curr.equals("-")) {
                if (!num1.equals("*") && !num2.equals("*")) {
                    ex.push(String.valueOf(computeExpr(num1, num2, expr)));
                }
                expr = curr;
                continue;
            }
            
            if (expr.equals("*")) {
                // num1
                if (num1.equals("*")) {
                    num1 = curr + "";
                } else {
                    num1 = curr + num1;
                }
            } else {
                // num2
                if (num2.equals("*")) {
                    num2 = curr + "";
                } else {
                    num2 = curr + num2;
                }
            }
            
        }
        
        if (expr.equals("+") || expr.equals("-")) {
            if (!num1.equals("*") && !num2.equals("*")) {
                ex.push(String.valueOf(computeExpr(num1, num2, expr)));
            }
        }
               
    }
    
    private static int computeExpr(String num1, String num2, String expr) {
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        int n3 = 0;
        if (expr.equals("+")) {
            n3 = n1 + n2;
        } else {
            n3 = n2 - n1;
        }
        return n3;
    }
}
