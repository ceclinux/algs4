import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

public class Solver {
  private boolean isSloved;
  private Stack<Board> bStack;
  public Solver(Board initial) {
    String[] sb = initial.toString().split("\n");
    int di = initial.dimension();
    int[][] storeint = new int[di][di];
    for (int i = 1; i <= di; i++) {
      //split one line into several cells, each cell has 3 characters
      String[] oneline = sb[i].split("(?<=\\G...)");
      for (int t = 0; t < di; t++) {
        storeint[i - 1][t] = Integer.parseInt(oneline[t].trim());
      }
    }
    int total = 0;
    int zerorow = 0;
    for (int i = 0; i < di; i++) {
      for (int j = 0; j < di; j++) {
        for (int k = i * di + j; k < di * di; k++) {
          int row = k / di;
          int col = k % di;
          if (storeint[i][j] != 0 && storeint[row][col] != 0) {
            if (storeint[i][j] > storeint[row][col]) {
              total++;
            }
          } else {
            zerorow = i;
          }
        }
      }
    }
    // see https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
    isSloved = (((di % 2) != 0) && (total % 2 == 0))
        || ((di % 2 == 0) && (((zerorow % 2) != 0) == (total % 2 == 0)));
    if (isSloved) {
      bStack = new Stack<Board>();
      Node min = solve(initial);
      while (min != null) {
        bStack.push(min.getBoard());
        min = min.head;
      }
    }
  }

  /**
   * The node class to synchronize the game tree
   * @author ceclinux
   *
   */
  private class Node {
    private Board key;
    private Node head, one, two, three, four;
    private int moves;

    private Node(Board key) {
      this.moves = 0;
      this.key = key;
    }

    public int value() {
      return key.manhattan() + moves;
    }

    public Board getBoard() {
      return key;
    }

    public String toString() {
      return key.toString();
    }
  }

  private Node put(Node ro, Board key) {
    if (ro == null) {
      return new Node(key);
    }
    if (ro.one == null) {
      ro.one = new Node(key);
      ro.one.head = ro;
      ro.one.moves = ro.moves + 1;
      return ro.one;
    } else if (ro.two == null) {
      ro.two = new Node(key);
      ro.two.head = ro;
      ro.two.moves = ro.moves + 1;
      return ro.two;
    } else if (ro.three == null) {
      ro.three = new Node(key);
      ro.three.head = ro;
      ro.three.moves = ro.moves + 1;
      return ro.three;
    } else {
      ro.four = new Node(key);
      ro.four.head = ro;
      ro.four.moves = ro.moves + 1;
      return ro.four;
    }
    
  }

  private Node solve(Board initial) {

    Node i = put(null, initial);
    MinPQ<Node> open = new MinPQ<Node>(new Comparator<Node>() {
      public int compare(Node o1, Node o2) {
        if (o1.value() < o2.value())
          return -1;
        else if (o1.value() == o2.value())
          return 0;
        else
          return 1;
      }
    });
    open.insert(i);
    TreeSet<Board> close = new TreeSet<Board>(new Comparator<Board>() {
      public int compare(Board o1, Board o2) {
        if (o1.manhattan() < o2.manhattan())
          return -1;
        else if (o1.equals(o2))
          return 0;
        else
          return 1;
      }
    });
    Node min = open.min();
    Board b = min.getBoard();
    while (!b.isGoal()) {
      min = open.delMin();
      b = min.getBoard();
      close.add(b);
      for (Board neigh : b.neighbors()) {
        if (!(min.head != null && min.head.getBoard().equals(neigh))) {
          Node temp = put(min, neigh);
          open.insert(temp);
        }
      }
    }
    return min;
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return isSloved;
  }

  // Minimun number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (isSolvable()) {
      return bStack.size() - 1;
    }
    return -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (isSolvable()) {
      return bStack;
    }
    return null;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()){
            StdOut.println(board);
        }
    }

  }
}