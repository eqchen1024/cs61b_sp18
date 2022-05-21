package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int row, col;
    private int[] threshRecords;
    private int sideLength;
    private int expTime;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        sideLength = N;
        expTime = T;
        threshRecords = new int[expTime];
        for (int expNum = 0; expNum < expTime; expNum ++) {
            Percolation p = pf.make(sideLength);
            while (!p.percolates()) {
                row = StdRandom.uniform(10);
                col = StdRandom.uniform(10);
                if (!p.isOpen(row,col)) {
                    p.open(row,col);
                }
            }
            threshRecords[expNum] = p.numberOfOpenSites();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshRecords);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshRecords);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev()/(Math.sqrt(expTime));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev()/(Math.sqrt(expTime));
    }
}