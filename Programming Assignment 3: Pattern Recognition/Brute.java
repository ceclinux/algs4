public class Brute {
  public static void main(String[] args) {
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    String filename = args[0];
    In in = new In(filename);
    int number = in.readInt();
    Point[] parr = new Point[number];
    for (int i = 0; i < number; i++) {
      int x = in.readInt();
      int y = in.readInt();
      parr[i] = new Point(x, y);
      parr[i].draw();
    }

    formatLine(number, parr);
    StdDraw.show(0);
  }

  private static void formatLine(int number, Point[] parr) {

    Quick3way.sort(parr);
    for (int i = 0; i < number; i++) {
      for (int j = i + 1; j < number; j++) {
        for (int k = j + 1; k < number; k++) {
          for (int l = k + 1; l < number; l++) {
            double conditionOne = parr[i].slopeTo(parr[j]);
            double conditionTwo = parr[i].slopeTo(parr[k]);
            double conditionThree = parr[i].slopeTo(parr[l]);
            if (conditionOne == conditionTwo && conditionTwo == conditionThree) {
              System.out.println(parr[i] + " -> " + parr[j] + " -> " + parr[k]
                  + " -> " + parr[l]);
              parr[i].drawTo(parr[l]);
            }
          }
        }
      }
    }

  }
}