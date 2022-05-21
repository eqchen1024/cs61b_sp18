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
        if (row >= sideLength || row < 0 || col >= sideLength || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int cellIndex = sideLength * row + col;
        if (openStatusArray[cellIndex] != 1) {
            openStatusArray[cellIndex] = 1;
            openCellNum += 1;
        }
        UnionAround(row,col);


    }

    private void UnionAround(int row, int col){
        int cellIndex = sideLength * row + col;
        if (row != 0 && openStatusArray[cellIndex - sideLength] == 1) {
            joinStatusSet.union(cellIndex - sideLength, cellIndex);
        }
        if (col !=0 && openStatusArray[cellIndex - 1] == 1) {
            joinStatusSet.union(cellIndex - 1, cellIndex);
        }
        if (row != sideLength - 1 && openStatusArray[cellIndex + sideLength] == 1) {
            joinStatusSet.union(cellIndex + sideLength, cellIndex);
        }
        if (col != sideLength - 1 && openStatusArray[cellIndex + 1] == 1) {
            joinStatusSet.union(cellIndex + 1, cellIndex);
        }
        if (row == 0) {
            joinStatusSet.union(sideLength * sideLength, cellIndex);
        }
        if (row == sideLength - 1) {
            joinStatusSet.union(sideLength * sideLength + 1, cellIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= sideLength || row < 0 || col >= sideLength || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int cellIndex = sideLength * row + col;
        if (openStatusArray[cellIndex] ==1) {
            return true;
        } else {
            return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= sideLength || row < 0 || col >= sideLength || col < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
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
