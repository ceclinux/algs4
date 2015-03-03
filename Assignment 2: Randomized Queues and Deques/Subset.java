public class Subset {

  /**
   * Subset.java that takes a command-line integer k; reads in a sequence of N
   * strings from standard input using StdIn.readString(); and prints out
   * exactly k of them, uniformly at random. Each item from the sequence can be
   * printed out at most once. You may assume that 0 ≤ k ≤ N, where N is the
   * number of string on standard input.
   * 
   * @param args
   *          arguments
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    int rannum = Integer.parseInt(args[0]);
    if (rannum == 0) {
      return;
    }
    String[] sample = new String[rannum];

    int count = 0;
    while (!StdIn.isEmpty() && count < rannum) {
      sample[count++] = StdIn.readString();
    }
    while (!StdIn.isEmpty()) {
      String str = StdIn.readString();
      int index = StdRandom.uniform(++count);
      if (index < rannum) {
        sample[index] = str;
      }
    }
    RandomizedQueue<String> ran = new RandomizedQueue<String>();
    for (String str : sample) {
      ran.enqueue(str);
    }
    for (String str : ran) {
      StdOut.println(str);
    }
  }

}