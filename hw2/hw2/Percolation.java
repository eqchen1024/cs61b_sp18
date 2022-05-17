package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int sideLength;
    private WeightedQuickUnionUF joinStatusSet;
    private int[] openStatusArray;
    public Percolation(int N) {
        sideLength = N;
        joinStatusSet =  new WeightedQuickUnionUF(sideLength ^ 2);
        openStatusArray = new int[sideLength ^ 2];
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int cellIndex = sideLength * row + col;
        openStatusArray[cellIndex] = 1;
    }
    private int[] getNeighborIndex(int row, int col){
        int cellIndex = sideLength * row + col;
        List<Integer> neighbourIndex = new ArrayList<Integer>();
        int up, down, left, right;
//        if (row == 0 && col == 0) {
//            right = cellIndex + 1;
//            down  = cellIndex + sideLength;
//        } else if (row == sideLength -1 && col == sideLength -1) {
//            left = cellIndex - 1;
//            up  = cellIndex - sideLength;
//        } else if (row == 0 && col == sideLength -1) {
//            left = cellIndex - 1;
//            up  = cellIndex - sideLength;
//
//    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int cellIndex = sideLength * row + col;
        if (openStatusArray[cellIndex] ==1) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isFull(int row, int col)  // is the site (row, col) full?
    public int numberOfOpenSites()           // number of open sites
    public boolean percolates()              // does the system percolate?
    public static void main(String[] args)   // use for unit testing (not required)
}
