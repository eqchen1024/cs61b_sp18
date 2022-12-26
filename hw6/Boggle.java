import java.util.*;

import edu.princeton.cs.algs4.In;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";
//    static String boardPath = "exampleBoard.txt";
    static String validChar = "abcdefghijklmnopqrstuvwxyz";
    static List<String> res = new ArrayList<>();
    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        // build trie
        Trie t = buildTrie();
        // build board
        String[][] board = buildBoard(boardFilePath);
        // transverse the board and find valid word.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j <board[0].length; j++) {
                String nextPtr = board[i][j];
                int index = (int) nextPtr.toCharArray()[0] - (int) 'a';
                search(board,i,j,"",t.head.neighbours[index],"");
            }
        }

        res.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        System.out.println(res.subList(0,k));
        return res.subList(0,k);
    }

    public static String[][] buildBoard(String boardFilePath){
        In f = new In(boardFilePath);
        String[] lines = f.readAllLines();
        String[][] board = new String[lines.length][lines[0].length()];
        int row = 0;
        int col = 0;
        for (String i : lines) {
            for (char c : i.toCharArray()) {
                board[row][col] = "" + c;
                col += 1;
            }
            col = 0;
            row += 1;
        }
        return board;
    }

    public static List<int[]> getNeighbour(String[][] board,int row, int col) {
        // no wrap around
        List<int[]> res = new LinkedList<>();
        int maxCol = board[0].length - 1;
        int maxRow = board.length - 1;
        if (row == 0 && col == 0) {
            res.add(new int[]{0, 1});
            res.add(new int[]{1, 0});
            res.add(new int[]{1, 1});
        } else if (row == 0 && col == maxCol) {
            res.add(new int[]{0, maxCol - 1});
            res.add(new int[]{1, maxCol - 1});
            res.add(new int[]{1, maxCol});
        } else if (row == maxRow && col == 0) {
            res.add(new int[]{maxRow, 1});
            res.add(new int[]{maxRow - 1, 1});
            res.add(new int[]{maxRow - 1, 0});
        } else if (row == maxRow && col == maxCol) {
            res.add(new int[]{maxRow, maxCol - 1});
            res.add(new int[]{maxRow - 1, maxCol});
            res.add(new int[]{maxRow - 1, maxCol - 1});
        } else if (row == 0) {
            res.add(new int[]{0, col - 1});
            res.add(new int[]{0, col + 1});
            res.add(new int[]{1, col - 1});
            res.add(new int[]{1, col});
            res.add(new int[]{1, col + 1});
        } else if (row == maxRow) {
            res.add(new int[]{maxRow, col - 1});
            res.add(new int[]{maxRow, col + 1});
            res.add(new int[]{maxRow - 1, col - 1});
            res.add(new int[]{maxRow - 1, col});
            res.add(new int[]{maxRow - 1, col + 1});
        } else if (col == 0) {
            res.add(new int[]{row - 1, 0});
            res.add(new int[]{row + 1, 0});
            res.add(new int[]{row - 1, 1});
            res.add(new int[]{row, 1});
            res.add(new int[]{row + 1, 1});
        } else if (col == maxCol) {
            res.add(new int[]{row - 1, maxCol});
            res.add(new int[]{row + 1, maxCol});
            res.add(new int[]{row - 1, maxCol - 1});
            res.add(new int[]{row, maxCol - 1});
            res.add(new int[]{row + 1,maxCol - 1});
        } else {
            res.add(new int[]{row - 1, col});
            res.add(new int[]{row + 1, col});
            res.add(new int[]{row, col - 1});
            res.add(new int[]{row, col + 1});
            res.add(new int[]{row - 1, col - 1});
            res.add(new int[]{row - 1, col + 1});
            res.add(new int[]{row + 1, col - 1});
            res.add(new int[]{row + 1, col + 1});
        }
        return res;
    }

    public static Trie buildTrie(){
        In f = new In(dictPath);
        Trie t = new Trie();
        for (String i : f.readAllLines()) {
            i = i.trim().toLowerCase(Locale.ROOT);
            for (char j : i.toCharArray()) {
                if (!validChar.contains(""+j)) {
                    i = i.replace(""+j,"");
                }
            }
            if (i.length() == 0){
                continue;
            }
//            System.out.println(i);
            if (i.length() >= 3) {
                t.add(i);
            }
        }
        return t;
    }

    public static int search(String[][] board, int row, int col, String prefix, Trie.Node curNode,String used){
        String curPtr = board[row][col];
        String newPrefix = prefix + curPtr;
        if (curNode == null || used.contains(""+row+","+col+".")) {
            return 1;
        } else if (curNode.end == true && !res.contains(newPrefix)) {
            res.add(newPrefix);
        }
        used+=""+row+","+col+".";
        System.out.println(used);
        for (int[] n : getNeighbour(board, row, col)) {
            String nextPtr = board[n[0]][n[1]];
            int index = (int) nextPtr.toCharArray()[0] - (int) 'a';
            search(board,n[0],n[1],newPrefix,curNode.neighbours[index],used);
        }
        return 1;
    }


    public static void main(String[] args) {
        solve(7,"smallBoard2.txt");
    }
}
