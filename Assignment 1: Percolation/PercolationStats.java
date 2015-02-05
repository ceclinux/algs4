public class PercolationStats {
  private double[] testresult;

  /**
   * Make Percolation tests, build nrow percolation for ntest times.
   * 
   * @param nrow
   *          number of rows
   * @param ntest
   *          number of tests
   */
  public PercolationStats(int nrow, int ntest) {
    if (nrow <= 0 || ntest <= 0) {
      throw new IllegalArgumentException();
    }
    int count = nrow * nrow;

    testresult = new double[ntest];
    for (int s = 0; s < ntest; s++) {
      Percolation per = new Percolation(nrow);
      int[] number = new int[count];
      for (int i = 0; i < number.length; i++) {
        number[i] = i + 1;
      }
      StdRandom.shuffle(number);
      int icell;
      for (icell = 1; icell <= number.length; icell++) {
        int mrow = (number[icell] - 1) / nrow + 1;
        int ncol = number[icell] % nrow;
        if (ncol == 0) {
          ncol = nrow;
        }
        per.open(mrow, ncol);
        if (per.percolates()) {
          testresult[s] = (double) icell / count;
          break;
        }
      }
    }

  }

  /**
   * Mean of percolation threshold.
   * @return mean
   */
  public double mean() {
    return StdStats.mean(testresult); // sample mean of percolation threshold

  }

  /**
   * Sample standard deviation of percolation threshold.
   * 
   * @return stand deviation
   */
  public double stddev() {
    return StdStats.stddev(testresult);

  }

  /**
   * Low  endpoint of 95% confidence interval of the percolation threshold.
   * @return Low  endpoint
   */
  public double confidenceLo() {
    return mean() - ((1.96 * stddev()) / Math.sqrt(testresult.length));

  }

  /**
   * High endpoint of 95% confidence interval of the percolation threshold.
   * @return How  endpoint
   */
  public double confidenceHi() {
    return mean() + ((1.96 * stddev()) / Math.sqrt(testresult.length));

  }

  /**
   * Test PercolationStats.
   * 
   * @param args
   *          for tests
   */
  public static void main(String[] args) { // test client (described below)
    int nrow = Integer.parseInt(args[0]);
    int ntest = Integer.parseInt(args[1]);
    PercolationStats ps = new PercolationStats(nrow, ntest);

    String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
    StdOut.println("mean                    = " + ps.mean());
    StdOut.println("stddev                  = " + ps.stddev());
    StdOut.println("95% confidence interval = " + confidence);
  }
}