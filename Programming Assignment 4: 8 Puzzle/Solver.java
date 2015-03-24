import java.util.Comparator;
import java.util.TreeSet;

public class Solver {
  private boolean isSloved;
  private Stack<Board> bq;

  private class Node {
    private Board key;
    private Node head, one, two, three, four;
    private int moves;

    public Node(Board key) {
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

  private class SloveTree {
    private Node root;

    public Node root() {
      return root;
    }

    public Node put(Node root, Board key) {
      if (root == null) {
        return new Node(key);
      }
      if (root.one == null) {
        root.one = new Node(key);
        root.one.head = root;
        root.one.moves = root.moves + 1;
        return root.one;
      } else if (root.two == null) {
        root.two = new Node(key);
        root.two.head = root;
        root.two.moves = root.moves + 1;
        return root.two;
      } else if (root.three == null) {
        root.three = new Node(key);
        root.three.head = root;
        root.three.moves = root.moves + 1;
        return root.three;
      } else {
        root.four = new Node(key);
        root.four.head = root;
        root.four.moves = root.moves + 1;
        return root.four;
      }
    }
  }

  private Comparator<Node> c = new Comparator<Node>() {
    public int compare(Node o1, Node o2) {
      if (o1.value() < o2.value())
        return -1;
      else if (o1.value() == o2.value())
        return 0;
      else
        return 1;
    }
  };
  private Comparator<Board> cc = new Comparator<Board>() {
    public int compare(Board o1, Board o2) {
      if (o1.manhattan() < o2.manhattan())
        return -1;
      else if (o1.equals(o2))
        return 0;
      else
        return 1;
    }
  };

  public Solver(Board initial) {
    String[] sb = initial.toString().split("\n");
    int di = initial.dimension();
    int[][] storeint = new int[di][di];
    for (int i = 1; i <= di; i++) {
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
    isSloved = ((di % 2 == 1) && (total % 2 == 0))
        || ((di % 2 == 0) && ((zerorow % 2 == 1) == (total % 2 == 0)));
    if (isSloved) {
      bq = new Stack<Board>();
      Node min = solve(initial);
      System.out.println(min);
      while (min != null) {
        bq.push(min.getBoard());
        min = min.head;
      }
    }
  }

  private Node solve(Board initial) {
    Solver.SloveTree tree = new SloveTree();
    Node i = tree.put(tree.root(), initial);
    MinPQ<Node> open = new MinPQ<Node>(c);
    open.insert(i);
    TreeSet<Board> close = new TreeSet<Board>(cc);
    Node min = open.min();
    Board b = min.getBoard();
    while (!(open.isEmpty() || b.isGoal())) {
      min = open.delMin();
      b = min.getBoard();
      close.add(b);
      for (Board neigh : b.neighbors()) {
        if (!close.contains(neigh)) {
          Node temp = tree.put(min, neigh);
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

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (isSolvable()) {
      return bq.size() - 1;
    }
    return -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (isSolvable()) {
      return bq;
    }
    return null;
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    int[][] n = new int[3][];
    n[0] = new int[] { 1, 2, 3 };
    n[1] = new int[] { 4, 6, 5 };
    n[2] = new int[] { 8, 7, 0 };

    Board bb = new Board(n);

    Solver s = new Solver(bb);
    System.out.println(s.isSolvable());
    System.out.println(s.moves());
    System.out.println(s.solution());
  }
}