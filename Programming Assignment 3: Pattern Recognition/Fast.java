import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Fast {
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
    HashMap<Double, HashSet<Point>> slopedict = new HashMap<Double, HashSet<Point>>();
    for (int i = 0; i < number; i++) {
      Point[] p = new Point[number - i - 1];
      for (int j = i + 1, t = 0; j < number; j++, t++) {
        p[t] = parr[j];
      }
      Arrays.sort(p, parr[i].SLOPE_ORDER);
      int s = 0;
      double d = 0;
      for (int q = 0; q < p.length; q++) {
        if (s == 0) {
          s = 1;
          d = parr[i].slopeTo(p[q]);
        } else {
          if (d == parr[i].slopeTo(p[q])) {
            s++;
          } else {
            if (s >= 3) {

              if (!slopedict.containsKey(d)) {
                slopedict.put(d, new HashSet<Point>());

              }
              if (slopedict.containsKey(d)
                  && !slopedict.get(d).contains(p[q - 1])) {
                slopedict.get(d).add(p[q - 1]);
                System.out.print(parr[i] + " -> ");
                for (int t = s; t > 1; t--) {
                  System.out.print(p[q - t] + " -> ");
                  // parr[i].drawTo(p[q + t]);
                }
                System.out.println(p[q - 1]);
                parr[i].drawTo(p[q - 1]);
              }
            }
            s = 1;
            d = parr[i].slopeTo(p[q]);
          }
        }
      }
      //there may remains items after searching all slopes
      if (s >= 3) {
        if (!slopedict.containsKey(d)) {
          slopedict.put(d, new HashSet<Point>());

        }
        if (slopedict.containsKey(d)
            && !slopedict.get(d).contains(p[p.length - 1])) {
          slopedict.get(d).add(p[p.length - 1]);
          System.out.print(parr[i] + " -> ");
          for (int t = s; t > 1; t--) {
            System.out.print(p[p.length - t] + " -> ");
            // parr[i].drawTo(parr[t]);
          }
          System.out.println(p[p.length - 1]);
          parr[i].drawTo(p[p.length - 1]);
        }
      }
    }
  }
}