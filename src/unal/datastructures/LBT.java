package unal.datastructures;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class LBT<T extends Comparable<? super T>> extends LinkedBinaryTree<T> {

    static Method theMax;
    static Method theLeaves;
    static Method theReverse;
    static Comparable maxTemp;
    static LBT theTree;

    enum TreeType {

        FULL, COMPLETE, NEITHER
    };

    static {
        try {
            Class<LBT> lbt = LBT.class;
            theMax = lbt.getMethod("max", BinaryTreeNode.class);
            theLeaves = lbt.getMethod("numLeaves", BinaryTreeNode.class);
            theReverse = lbt.getMethod("reverse", BinaryTreeNode.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static <T> void max(BinaryTreeNode<T> t) {
        if (t != null) {
            if (maxTemp == null) {
                maxTemp = (Comparable) (t.getElement());
            }
            Comparable aux = (Comparable) (t.element);
            if (aux.compareTo(maxTemp) > 0) {
                maxTemp = aux;
            }
        }
    }

    public static <T> void numLeaves(BinaryTreeNode<T> t) {
        if (t != null) {
            if (t.leftChild == null && t.rightChild == null) {
                count++;
            }
        }

    }

    public static <T> void reverse(BinaryTreeNode<T> t) {
        if (t != null) {
            BinaryTreeNode<T> temp = t.leftChild;
            t.leftChild = t.rightChild;
            t.rightChild = temp;
        }
    }

    public void doMax() {
        levelOrder(theMax);
    }

    public void doCountLeaves() {
        count = 0;
        levelOrder(theLeaves);
    }

    public void doReverse() {
        levelOrder(theReverse);
    }

    public int theMinHeight(BinaryTreeNode<T> t) {
        if (t == null) {
            return 0;
        }
        int hl = theMinHeight(t.leftChild);  // height of left subtree
        int hr = theMinHeight(t.rightChild); // height of right subtree
        if (hl < hr) {
            return ++hl;
        } else {
            return ++hr;
        }
    }

    public Boolean isFull() {
        int countNodes = theTree.size();
        int height = theTree.height();
        int heightMin = (int) (Math.ceil(Math.log(countNodes + 1) / Math.log(2)));
        int maxNodes = (int) (Math.pow(2, heightMin) - 1);
        if (heightMin != height || maxNodes != countNodes) {
            return false;
        }
        return true;
    }

    public Boolean isComplete(LBT<T> t) {
        int countNodes = theTree.size();
        int countNoNull = countComplete(t);
        if (countNodes != countNoNull) {
            return false;
        }
        return true;
    }

    public ArrayList<T> levelOrdertoArray(LBT<T> t) {
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

    public Boolean compare(LBT l1, LBT l2) {

        ArrayQueue<BinaryTreeNode<T>> p = new ArrayQueue<>();
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> t1 = l1.root;
        BinaryTreeNode<T> t2 = l2.root;

        //compara si son iguales en tamaño
        int size1 = l1.size();
        int size2 = l2.size();
        count = 0;
        if (size1 != size2 || l1.root.equals(l2.root)) {
            return false;
        }

        while (t1 != null || t2 != null) {
            // put t's children on queue
            if (t1.leftChild != null) {
                q.put(t1.leftChild);
            }
            if (t2.leftChild != null) {
                p.put(t2.leftChild);
            }

            if (t1.rightChild != null) {
                q.put(t1.rightChild);
            }

            if (t2.rightChild != null) {
                p.put(t2.rightChild);
            }
            // get next node to visit
            t1 = (BinaryTreeNode<T>) q.remove();
            t2 = (BinaryTreeNode<T>) p.remove();
            if (t1 != null && t2 != null && t1.element.equals(t2.element)) {
                count++;
            }
        }
        if (count + 1 != size1) {
            return false;
        }
        return true;
    }

    public int countComplete(LBT<T> t) {
        int count = 0;
        ArrayQueue<BinaryTreeNode<T>> q = new ArrayQueue<>();
        BinaryTreeNode<T> tt = t.root;
        while (tt != null) {
            // put t's children on queue
            if (tt.leftChild != null) {
                q.put(tt.leftChild);
                count++;
            } else {
                return count + 1;
            }
            if (tt.rightChild != null) {
                q.put(tt.rightChild);
                count++;
            } else {
                return count + 1;
            }
            // get next node to visit
            tt = (BinaryTreeNode<T>) q.remove();
            // count++;
        }
        return count + 1;
    }

    public Boolean isBalanced(LBT t) {
        int height = t.height();
        int minHeight = t.theMinHeight(t.root);
        if (height - minHeight > 2) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        LBT obj = new LBT<>();
        obj = obj.createTree(TreeType.NEITHER);
        obj.levelOrderOutput();
        System.out.println(obj.isComplete(obj));
        obj.doCountLeaves();
        System.out.println("el numero de hojas es:" + count);
        //obj.doReverse();
        //   obj.levelOrderOutput();

        LBT obj2 = new LBT<>();
        obj2 = obj2.createTree(TreeType.COMPLETE);
        Boolean compareBool = obj2.compare(obj, obj2);
        compareBool = obj2.isBalanced(obj2);
        System.out.println("isBalanced=" + compareBool);
        ArrayList<Integer> list;
        
        list=obj.levelOrdertoArray(obj);
        System.out.println(list.toString());
        
        

//        list = new ArrayList<>();
//        list.add(0, 1);
//        list.add(1, 2);
//        list.add(2, 3);
//        list.add(3, 4);
//        list.add(4, 5);
//        list.add(5, null);
//        list.add(6, 7);
//        list.add(7, 8);
        //
//        theTree.doMax();
//        isComplete();
//        System.out.println(isFull());
//        System.out.println(maxTemp.toString());
//



    }

    private LBT createTree(TreeType type) {
        LBT<Character> a = new LBT<>(),
                x = new LBT<>(),
                y = new LBT<>(),
                z = new LBT<>(),
                w = new LBT<>();
        if (type.equals(TreeType.NEITHER)) {
            x.makeTree('A', a, a);
            z.makeTree('B', x, a);
            y.makeTree('D', z, a);
            x.makeTree('e', y, a);
            theTree = x;
        } else if (type.equals(TreeType.FULL)) {
            x.makeTree('A', a, a);
            z.makeTree('B', a, a);
            y.makeTree('D', x, z);
            x.makeTree('e', a, a);
            w.makeTree('h', a, a);
            z.makeTree('f', w, x);
            x.makeTree('5', y, z);
            theTree = x;
        } else if (type.equals(TreeType.COMPLETE)) {
            x.makeTree('A', a, a);
            z.makeTree('B', a, a);
            y.makeTree('D', x, z);
            w.makeTree('h', a, a);
            x.makeTree('x', y, w);
            theTree = x;
        }
        return x;
    }
}
