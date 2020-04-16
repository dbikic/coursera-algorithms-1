/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double CONFIDENCE_CONSTANT = 1.96;
  private final double[] trialResults;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    trialResults = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation percolation = new Percolation(n);
      do {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        percolation.open(row, col);
      } while (!percolation.percolates());
      double squaredN = n * n;
      trialResults[i] = percolation.numberOfOpenSites() / squaredN;
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(trialResults);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(trialResults);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - ((CONFIDENCE_CONSTANT * stddev()) / Math.sqrt(trialResults.length));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + ((CONFIDENCE_CONSTANT * stddev()) / Math.sqrt(trialResults.length));
  }

  // test client (see below)
  public static void main(String[] args) {
    int n;
    int trials;
    n = Integer.parseInt(args[0]);
    trials = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, trials);
    StdOut.println("mean                    = " + stats.mean());
    StdOut.println("stddev                  = " + stats.stddev());
    StdOut.println(
        "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }
}
