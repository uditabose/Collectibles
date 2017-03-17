

package quick;

/**
 *
 * @author Udita
 */
public class Cons {

}

class A {

    public A(int a) {
    }
    
}

class B extends A {

    public B() {
        super(1);
    }
    
}

interface M {
    void doThis();
}

interface N {
    void doThis();
}

class C implements M, N {
    @Override
    public void doThis() {
        System.out.println("doThis ?");
    }
}