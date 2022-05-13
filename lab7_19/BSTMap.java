import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private class Node {
        private K key;
        private V value;
        private int size;
        private Node left = null;
        private Node right = null;
        private Node(K key, V value, int size){
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BSTMap(){
        root = null;
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return contains(root,key);
    }

    private boolean contains(Node x, K key){
        if (x == null){
            return false;
        }
        int cmp = key.compareTo(x.key);
        if (cmp==0){
            return true;
        } else if(cmp < 0) {
            return contains(x.left,key);
        } else {
            return contains(x.right,key);
        }
    }


    @Override
    public V get(K key) {

        return getValue(root, key);
    }

    private V getValue(Node node,K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp < 0) {
            return getValue(node.left,key);
        } else {
            return getValue(node.right,key);
        }
    }

    @Override
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return root.size;
        }
    }

    @Override
    public void put(K key, V value) {
        root = putValue(root, key, value);
    }

    private Node putValue(Node x, K key, V value) {
        if (x == null) {
            x = new Node(key,value,1);
            return x;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            x.value = value;
            return x;
        } else if (cmp < 0) {
            x.size += 1;
            x.left = putValue(x.left,key,value);
            return x;
        } else {
            x.size += 1;
            x.right = putValue(x.right,key,value);
            return x;
        }
    }
    public void printInOrder() {
        printInOrderNode(root);
    }

    private void printInOrderNode(Node x) {
        if (x ==null) {
            System.out.println(" ");
        } else {
            printInOrderNode(x.left);
            System.out.println(x.value);
            printInOrderNode(x.right);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> res = new HashSet();
        return getSet(root,res);
    }

    private Set<K> getSet(Node x,Set<K> res) {
        if (x == null) {
            return res;
        }
        res.add(x.key);
        res = getSet(x.left,res);
        res = getSet(x.right,res);
        return res;
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        root = removeNode(root,key);
        return value;
    }

    private Node removeNode(Node x, K key)  {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            x.size -= 1;
            x = replaceNode(x);
            return x;
        } else if (cmp < 0) {
            x.size -= 1;
            x.left = removeNode(x.left,key);
            return x;
        } else {
            x.size -= 1;
            x.right = removeNode(x.right,key);
            return x;
        }
    }

    private Node replaceNode(Node x) {
        if (x.left == null && x.right == null) {
            return null;
        } else if (x.left == null) {
            return x.right;
        } else if (x.right == null) {
            return x.left;
        } else {
            Node replacer = getMostLeftNode(x.right);
            x.right = removeNode(x.right,replacer.key);
            replacer.left = x.left;
            replacer.right = x.right;
            replacer.size = x.size;
            return replacer;
        }
    }

    private Node getMostLeftNode(Node x) {
        if (x.left == null) {
            return x;
        } else {
            return getMostLeftNode(x.left);
        }
    }





    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}