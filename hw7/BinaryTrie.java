import java.io.Serializable;
import java.util.*;

public class BinaryTrie implements Serializable {
    Set<Character> charSet = new HashSet<>();
    Node head;
    Map<Character, BitSequence> lookupTable = new HashMap<>();
    Map<BitSequence,Character> inverseLookupTable = new HashMap<>();
    PriorityQueue<Node> pq = new PriorityQueue<>();
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        for (Character c: frequencyTable.keySet()) {
            charSet.add(c);
        }
        for (Character c : frequencyTable.keySet()) {
            int rf = frequencyTable.get(c);
            pq.add(new Node(c,true,rf));
        }
        head = buildTrie(pq);
        head.key = "";
        buildLookupTable();

    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        BitSequence curPrefix = new BitSequence("");
        Collection<BitSequence> bitSet = lookupTable.values();
        Match res = new Match(new BitSequence(""),'.');
        int n = 0;
        while (n <= querySequence.length()){
//            System.out.println(curPrefix);
            if (bitSet.contains(curPrefix)){
                res = new Match(curPrefix,inverseLookupTable.get(curPrefix));
                break;
            }
            else {
                curPrefix = curPrefix.appended(querySequence.bitAt(n));
                n++;
            }
        }
        return res;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        search(head,"");
        for (Map.Entry<Character,BitSequence> entry :lookupTable.entrySet()) {
            inverseLookupTable.put(entry.getValue(),entry.getKey());
        }
        return lookupTable;
    }

    public void search(Node n, String prefix) {
        String newPrefix = prefix+ n.key;
        if (n.end == true) {
            lookupTable.put(n.character,new BitSequence(newPrefix));
        }
        else {
            search(n.leftChild,newPrefix);
            search(n.rightChild,newPrefix);
        }
    }

    public static class Node implements Comparable,Serializable {
        String key;
        char character;
        boolean end;
        int relFreq;
        Node leftChild;
        Node rightChild;

        public Node(char c, boolean e, int rf){
            character = c;
            end = e;
            relFreq = rf;
            leftChild = null;
            rightChild = null;
        }

        public Node(boolean e, Node lc, Node rc){
            end = e;
            leftChild = lc;
            rightChild = rc;
            relFreq = lc.relFreq + rc.relFreq;
            lc.key = "0";
            rc.key = "1";
        }

        @Override
        public int compareTo(Object o) {
            Node other = (Node) o;
            return (int) Math.signum(this.relFreq - other.relFreq);
        }
    }

    public Node buildTrie(PriorityQueue<Node> pq) {
        while (pq.size() >= 2) {
            Node min1 = pq.remove();
            Node min2 = pq.remove();
            Node parent = new Node(false,min1,min2);
            pq.add(parent);
        }
        return pq.remove();
    }

    public static void main(String[] args) {
        Map<Character, Integer> ft = new HashMap<>();
        ft.put('a',1);
        ft.put('b',2);
        ft.put('c',4);
        ft.put('d',5);
        ft.put('e',6);
        BinaryTrie t = new BinaryTrie(ft);
        t.buildLookupTable();
        System.out.println(t.inverseLookupTable);
    }
}