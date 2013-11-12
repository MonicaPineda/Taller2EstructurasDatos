package unal.datastructures;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ABT<T> extends LinkedBinaryTree<T> implements BinaryTree<T>  {
    public ArrayList<T> tree;
    BinaryTreeNode<T> root;
//    static Method visit;      // visit method to use during a traversal
//    static Method theAdd1;    // method to increment count by 1
//    static Method theOutput;  // method to output node element
//    static int count; 
//    // counter 
    
     enum TreeType {
        FULL, COMPLETE, NEITHER
    };

//    public ABT(){
//        tree=new ArrayList<>();
//        for(int i=0;i<3;i++){
//            tree.add(null);
//        }
//    }
    
    
    @Override
    public boolean isEmpty() {
        if(tree.isEmpty() || tree.get(0)==null){
            return true;
        }
        return true;
    }

    @Override
    public T root() {
        return (tree.get(0)==null) ? null : tree.get(0);
    }

    @Override
    public void makeTree(T root, BinaryTree<T> left, BinaryTree<T> right) {
         this.root = new BinaryTreeNode<T>( root,
         ((LinkedBinaryTree<T>) left).root,
         ((LinkedBinaryTree<T>) right).root ); this.root = new BinaryTreeNode<T>( root,
         ((LinkedBinaryTree<T>) left).root,
         ((LinkedBinaryTree<T>) right).root );
         tree=levelOrdertoArray(this);
        
    }

    @Override
    public BinaryTree<T> removeLeftSubtree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BinaryTree<T> removeRightSubtree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preOrder(Method visit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inOrder(Method visit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postOrder(Method visit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void levelOrder(Method visit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public ArrayList<T> levelOrdertoArray(LinkedBinaryTree<T> t) {
        ArrayList<T> list;
        list = new ArrayList<>();
        int height=t.height();
        for(int i=0;i<Math.pow(2, height)-2;i++){
            list.add(null);
        }
        int i = 0;
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> tt = t.root;
        list.add(0,t.root.element);
        while (tt != null) {
            if(list.get(i)!=null){
            // put t's children on queue
            if (tt.leftChild != null) {
                q.put(tt.leftChild);
                 list.set(2 * i + 1, tt.leftChild.element);
            }
          
           
            if (tt.rightChild != null) {
                q.put(tt.rightChild);
                list.set(2 * i + 2, tt.rightChild.element);
            }
            

            
            // get next node to visit
            tt = (BinaryTreeNode<T>) q.remove();
            }
            i++;
        }
        return list;
    }
    
    
    public static void main(String[] args){
        ABT abt=new ABT<>();
        abt.createTree(TreeType.FULL);
        
        //for(T t:tree)
    
    }
    
    private ABT createTree(TreeType type) {
        ABT<Character> a = new ABT<>(),
                x = new ABT<>(),
                y = new ABT<>(),
                z = new ABT<>(),
                w = new ABT<>();
        if (type.equals(TreeType.NEITHER)) {
            x.makeTree('A', a, a);
            z.makeTree('B', x, a);
            y.makeTree('D', z, a);
            x.makeTree('e', y, a);
        } else if (type.equals(TreeType.FULL)) {
            x.makeTree('A', a, a);
            z.makeTree('B', a, a);
            y.makeTree('D', x, z);
            x.makeTree('e', a, a);
            w.makeTree('h', a, a);
            z.makeTree('f', w, x);
            x.makeTree('5', y, z);
        } else if (type.equals(TreeType.COMPLETE)) {
            x.makeTree('A', a, a);
            z.makeTree('B', a, a);
            y.makeTree('D', x, z);
            w.makeTree('h', a, a);
            x.makeTree('x', y, w);
        }
        return x;
    }
    
   
}
