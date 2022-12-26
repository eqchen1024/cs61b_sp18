import java.awt.color.ICC_ColorSpace;

public class Trie {
    public Node head;
    public int size;

    public static class Node {
        public String key;
        public boolean end = false;
        public Node[] neighbours;

        public Node(String k,boolean e){
            key = k;
            end = e;
            neighbours = new Node[26];
        }
    }

    public Trie(){
        head = new Node("",false);
        size = 0;
    }

    public void add(String s) {
        addNext(head,s);
        size += 1;
    }

    public void addNext(Node n,String remain) {
        char[] charArray = remain.toCharArray();
        int index = (int) charArray[0] - (int) 'a';
        if (charArray.length == 1 && n.neighbours[index] == null) {
            n.neighbours[index] = new Node(""+charArray[0],true);
        } else if (charArray.length == 1 && n.neighbours[index] != null) {
            n.neighbours[index].end = true;
        }
        else if (charArray.length != 1 && n.neighbours[index] != null) {
            addNext(n.neighbours[index],remain.substring(1));
        }
        else {
            n.neighbours[index] = new Node(""+charArray[0],false);
            addNext(n.neighbours[index],remain.substring(1));
        }
    }

    public boolean search(Node n, String remain) {
        if (remain.length() == 0 && n.end == true) {
            return true;
        } else if (remain.length() == 0 && n.end == false) {
            return false;
        }
        char[] charArray = remain.toCharArray();
        int index = (int) charArray[0] - (int) 'a';
        if (n.neighbours[index] == null) {
            return false;
        } else {
            return search(n.neighbours[index],remain.substring(1));
        }
    }

    public boolean contains(String s) {
        return search(head,s);
    }

    public static void main(String[] args) {
//        Trie t = new Trie();
//        String s1 = "abcde";
//        String s2 = "abc";
//        t.add(s1);
//        t.add(s2);
//        System.out.println(t.contains(""));
//        System.out.println(t.contains("abc"));
//        System.out.println(t.contains("abcde"));
        Trie t = Boggle.buildTrie();
        for (Node n: t.head.neighbours) {
            System.out.println(n.key);
            System.out.println(n.end);
        }
    }
}
