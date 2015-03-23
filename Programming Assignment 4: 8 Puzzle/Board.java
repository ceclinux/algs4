
import java.util.Iterator;

public class Board {
  private int[][] blocks;
  private final int N;

  // construct a board from an N-by-N array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
      N = blocks.length;
      this.blocks = new int[N][N];
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          this.blocks[i][j] = blocks[i][j];
        }
      }
    
  }

  // board dimension N
  public int dimension() {
    return N;
  }

  // number of blocks out of place
  public int hamming() {
    int hamming = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (blocks[i][j] != (i * N + j + 1)) {
          hamming++;
        }
      }
    }
    return hamming - 1;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    int manhattan = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (blocks[i][j] != 0) {
          int row = (blocks[i][j] - 1) / N;
          int col = (blocks[i][j] - 1) % N;
          manhattan += Math.abs(row - i) + Math.abs(col - j);
        }
      }
    }
    return manhattan;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return manhattan() == 0;
  }

  // a boadr that is obtained by exchanging two adjacent blocks in the same row
  public Board twin() {
    int[][] twinBoard = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        twinBoard[i][j] = blocks[i][j];
      }
    }
    if (blocks[0][0] != 0 && blocks[0][1] != 0) {
      int temp = twinBoard[0][0];
      twinBoard[0][0] = twinBoard[0][1];
      twinBoard[0][1] = temp;
    } else {
      int temp = twinBoard[1][0];
      twinBoard[1][0] = twinBoard[1][1];
      twinBoard[1][1] = temp;
    }
    return new Board(twinBoard);
  }

  // does this board equal y?
  @Override
  public boolean equals(Object y) {
    if (y == this)
      return true;

    if (y == null)
      return false;

    if (y.getClass() != this.getClass()) {
      return false;
    }

    Board thatBoard = (Board) y;
    if (this.N != thatBoard.N) {
      return false;
    }

    int[][] arr_thatBoard = thatBoard.blocks;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (blocks[i][j] != arr_thatBoard[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    return new Iterable<Board>() {

      @Override
      public Iterator<Board> iterator() {
        // TODO Auto-generated method stub
        return new BoardIterator();
      }
    };
  }

  private class BoardIterator implements Iterator<Board> {
    Bag<Board> b = new Bag<Board>();
    Iterator<Board> biterator;

    private BoardIterator() {

      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          if (blocks[i][j] == 0) {
            if (i != 0) {
              b.add(new Board(swap(i- 1, j , i , j)));
            }
            if (i != (N - 1)) {
              b.add(new Board(swap(i+ 1, j , i , j)));
            }
            if (j != 0) {
              b.add(new Board(swap(i, j - 1 , i, j )));
            }
            if (j != (N - 1)) {
              b.add(new Board(swap(i, j + 1, i, j )));
            }
          }
        }
        biterator = b.iterator();
      }

    }

    @Override
    public boolean hasNext() {
      // TODO Auto-generated method stub
      return biterator.hasNext();
    }

    @Override
    public Board next() {
      return biterator.next();
    }

    @Override
    public void remove() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException();

    }

    private int[][] swap(int i1, int j1, int i2, int j2) {
      int[][] newblocks = new int[N][N];
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          newblocks[i][j] = blocks[i][j];
        }
      }
      newblocks[i1][j1] = blocks[i2][j2];
      newblocks[i2][j2] = blocks[i1][j1];
      return newblocks;
    }
  }

  // string representation of this board (in the output format specified below)

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // unit tests (not graded)
  public static void main(String[] args) {
    int[][] n = new int[3][3];
    n[0][0] = 5;
    n[0][1] = 4;
    n[0][2] = 0;
    n[1][0] = 2;
    n[1][1] = 3;
    n[1][2] = 8;
    n[2][0] = 7;
    n[2][1] = 1;
    n[2][2] = 6;

    Board bb = new Board(n);
    System.out.println(bb);
    for (Board b : bb.neighbors()) {
      System.out.println(b);
//      System.out.println(b.manhattan());
    }
//    for (int i = 0; i < 3; i++) {
//      for (int j = 0; j < 3; j++) {
//        System.out.println(n[i][j]);
//      }
//    }
//    System.out.println(bb);
//  System.out.println(bb.manhattan());
  }
  
}