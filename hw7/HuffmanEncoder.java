import java.util.*;

public class HuffmanEncoder {
    public static Map<Character,Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character,Integer> res = new HashMap<>();
        for (char c: inputSymbols) {
            if (res.containsKey(c)) {
                res.put(c, res.get(c) + 1);
            } else
                res.put(c,1);
        }
        return res;
    }

    public static void main(String[] args) {
        String filePath = args[0];
        char[] words = FileUtils.readFile(filePath);
        Map<Character,Integer> freqTable = buildFrequencyTable(words);
        BinaryTrie t = new BinaryTrie(freqTable);
        ObjectWriter ow = new ObjectWriter(filePath + ".huf");
        ow.writeObject(t);
        List<BitSequence> bsList = new LinkedList<>();
        for (char c: words) {
            bsList.add(t.lookupTable.get(c));
        }
        BitSequence bs = BitSequence.assemble(bsList);
        ow.writeObject(bs);
    }
}
