import java.util.LinkedList;
import java.util.List;

public class HuffmanDecoder {
    public static void main(String[] args) {
        String readFilePath = args[0];
        String writeFilePath = args[1];
        ObjectReader or = new ObjectReader(readFilePath + ".huf");
        /* Read first object from the file. */
        Object x = or.readObject();
        BinaryTrie t = (BinaryTrie) x;

        Object y = or.readObject();
        BitSequence bs = (BitSequence) y;

        String res = new String();
        while (bs.length() != 0) {
            Match charMatch = t.longestPrefixMatch(bs);
            res += charMatch.getSymbol();
            bs = bs.allButFirstNBits(charMatch.getSequence().length());
        }
//        System.out.println(res);
         FileUtils.writeCharArray(writeFilePath, res.toCharArray());
    }
}
