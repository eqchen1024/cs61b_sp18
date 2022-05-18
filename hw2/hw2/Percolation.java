package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int sideLength;
    private WeightedQuickUnionUF joinStatusSet;
    private int[] openStatusArray;
    private int openCellNum = 0;

    public Percolation(int N) {
        sideLength = N;
        joinStatusSet =  new WeightedQuickUnionUF(sideLength * sideLength + 2);
        openStatusArray = new int[sideLength * sideLength + 2];
        openStatusArray[sideLength * sideLength] = 1;
        openStatusArray[sideLength * sideLength + 1] = 1;
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int cellIndex = sideLength * row + col;
        openStatusArray[cellIndex] = 1;
        openCellNum += 1;
        List<Integer> neighbours= getNeighborIndex(row, col);
        for (int neighbour: neighbours) {
            if (openStatusArray[neighbour] == 1) {
                joinStatusSet.union(neighbour, cellIndex);
            }
        }

    }

    private List<Integer> getNeighborIndex(int row, int col){
        int cellIndex = sideLength * row + col;
        List<Integer> neighbourIndex = new ArrayList<Integer>();
        if (row != 0) {
            neighbourIndex.add(cellIndex - sideLength);
        }
        if (col !=0) {
            neighbourIndex.add(cellIndex - 1);
        }
        if (row != sideLength - 1) {
            neighbourIndex.add(cellIndex + sideLength);
        }
        if (col != sideLength - 1) {
            neighbourIndex.add(cellIndex + 1);
        }
        if (row == 0) {
            neighbourIndex.add(sideLength * sideLength);
        }
        if (row == sideLength - 1) {
            neighbourIndex.add(sideLength * sideLength + 1);
        }
        return neighbourIndex;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int cellIndex = sideLength * row + col;
        if (openStatusArray[cellIndex] ==1) {
            return true;
        } else {
            return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean full_flag = false;
        int cellIndex = sideLength * row + col;
        if (joinStatusSet.connected(sideLength * sideLength,cellIndex) && isOpen(row,col)) {
            full_flag = true;
        }
        return full_flag;
    }

    // number of open sites
    public int numberOfOpenSites(){
        return openCellNum;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean percolates_flag = false;
        if (joinStatusSet.connected(sideLength * sideLength,sideLength * sideLength + 1) ) {
            percolates_flag = true;
        }

        return percolates_flag;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation a = new Percolation(10);
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            System.out.println(a.isOpen(i,5));
            a.isFull(i,5);
            System.out.println(a.isFull(i,5));
            a.open(i,5);
            System.out.println(a.isOpen(i,5));
            System.out.println(a.isFull(i,5));
            a.percolates();
            System.out.println(a.percolates());

        }
    }
}
