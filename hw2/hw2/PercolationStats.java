package hw2;

import java.util.Random;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    Random random = new Random();
    Percolation p;
    int row, col;
    int[] threshRecords;
    int thresh;
    int sideLength;
    int expTime;
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException();
        }
        sideLength = N;
        expTime = T;
        p = pf.make(sideLength);
        threshRecords = new int[expTime];
        for (int expNum = 0; expNum < expTime; expNum ++) {
            thresh = 0;
            while (!p.percolates()) {
                row = StdRandom.uniform(10);
                col = StdRandom.uniform(10);
                if (!p.isOpen(row,col)) {
                    p.open(row,col);
                    thresh += 1;
                }
            }
            threshRecords[expNum] = thresh;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        int sumOfThresh = 0;
        for (int i =0; i < threshRecords.length; i++) {
            sumOfThresh += threshRecords[i];
        }
        return sumOfThresh/expTime;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double totalDeviationSquare = 0;
        double variance;
        double meanThresh = mean();
        for (int i =0; i < threshRecords.length; i++) {
            totalDeviationSquare += (threshRecords[i] - meanThresh) * (threshRecords[i] - meanThresh);
        }
        variance = totalDeviationSquare/(expTime -1);
        return Math.sqrt(variance);
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