/**
 * Compilation: PASSED Style: PASSED Findbugs: No potential bugs found. API:
 * PASSED
 * Correctness: 24/24 tests passed Memory: 8/8 tests passed Timing: 9/9 tests
 * passed
 * Aggregate score: 100.00% [Correctness: 65%, Memory: 10%, Timing: 25%, Style:
 * 0%]
 * 
 * @author ceclinux
 *
 */
public class Percolation {
  private boolean[] open;
  private int edgelen;
  private WeightedQuickUnionUF wf;
  private WeightedQuickUnionUF wfBack;

  /**
   * create ncell-by-ncell grid, with all sites blocked.
   * 
   * @param edge
   *          the length of edge
   * 
   */
  public Percolation(int edge) {
    if (edge <= 0) {
      throw new IllegalArgumentException();
    }
    edgelen = edge;
    int count = edge * edge + 2;
    open = new boolean[count];

    wf = new WeightedQuickUnionUF(count);
    wfBack = new WeightedQuickUnionUF(count);
    int lastLine = edge * (edge - 1);
    for (int i = 1; i <= edge; i++) {
      wfBack.union(i, 0);
      wf.union(i, 0);
      wf.union(i + lastLine, count - 1);
    }
  }

  //
  /**
   * Open site (row i, column j) if it is not open already.
   * 
   * @param irow
   *          the row number
   * @param jcol
   *          the column number
   */
  public void open(int irow, int jcol) {
    if (irow <= 0 || irow > edgelen || jcol <= 0 || jcol > edgelen) {
      throw new IndexOutOfBoundsException();
    }
    int cellnum = cellNum(irow, jcol);
    open[cellnum] = true;
    if (irow != 1 && open[cellnum - edgelen]) {
      wf.union(cellnum - edgelen, cellnum);
      wfBack.union(cellnum - edgelen, cellnum);
    }
    if (irow != edgelen && open[cellnum + edgelen]) {
      wf.union(cellnum + edgelen, cellnum);
      wfBack.union(cellnum + edgelen, cellnum);
    }
    if (jcol != 1 && open[cellnum - 1]) {
      wf.union(cellnum - 1, cellnum);
      wfBack.union(cellnum - 1, cellnum);
    }
    if (jcol != edgelen && open[cellnum + 1]) {
      wf.union(cellnum + 1, cellnum);
      wfBack.union(cellnum + 1, cellnum);
    }
  }

  /**
   * check if site (row irow, column j) is open.
   * 
   * @param irow
   *          the row number
   * @param jcol
   *          the column number
   * @return if the specified cell is open
   */
  public boolean isOpen(int irow, int jcol) {
    if (irow <= 0 || irow > edgelen || jcol <= 0 || jcol > edgelen) {
      throw new IndexOutOfBoundsException();
    }
    return open[cellNum(irow, jcol)];
  }

  /**
   * Check if the site(row irow, column jcol) full.
   * 
   * @param irow
   *          the row number
   * @param jcol
   *          the column number
   * @return whether the site is full
   */
  public boolean isFull(int irow, int jcol) {
    if (irow <= 0 || irow > edgelen || jcol <= 0 || jcol > edgelen) {
      throw new IndexOutOfBoundsException();
    }
    return isOpen(irow, jcol) && wfBack.connected(cellNum(irow, jcol), 0);

  }

  /**
   * Check if the system percolate.
   * 
   * @return the boolean value that indicate the system is percolate
   */
  public boolean percolates() {
    /**
     * When the length of edge is only one, the system will percolates even it
     * is not.
     */
    if (edgelen == 1) {
      return isOpen(1, 1);
    }
    return wf.connected(0, edgelen * edgelen + 1);
  }

  private int cellNum(int irow, int jcol) {
    if (irow <= 0 || irow > edgelen || jcol <= 0 || jcol > edgelen) {
      throw new IndexOutOfBoundsException();
    }
    return edgelen * (irow - 1) + jcol;
  }

  /**
   * Test percolation.
   * 
   * @param args
   *          for test
   */
  public static void main(String[] args) {
    Percolation per = new Percolation(2);
    per.open(2, 2);
    per.open(1, 2);
    StdOut.println(per.percolates());
  }
}