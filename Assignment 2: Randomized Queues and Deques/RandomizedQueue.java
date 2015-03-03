import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] queue;
  private int num;

  // construct an empty randomized queue
  public RandomizedQueue() {
    queue = (Item[]) new Object[2];
    num = 0;
  }

  // is the queue empty?
  public boolean isEmpty() {
    return num == 0;
  }

  // return the number of items on the queue
  public int size() {
    return num;
  }

  /**
   * Add the item to the queue.
   * @param item the item to the queue
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (num == queue.length) {
      resize(2 * num);
    }
    queue[num] = item;
    num++;
  }

  private void resize(int max) {
    Item[] temp = (Item[]) new Object[max];
    for (int i = 0; i < num; i++) {
      temp[i] = queue[i];
    }
    queue = temp;
  }

  /**
   * Remove and return a random item.
   * @return a randomized item of the queue
   */
  public Item dequeue() {
    if (num == 0) {
      throw new NoSuchElementException();
    }
    if (num == queue.length / 4) {
      resize(queue.length / 2);
    }
    int ran = StdRandom.uniform(num);
    Item ret = queue[ran];
    queue[ran] = queue[num - 1];
    queue[num - 1] = null;
    num--;
    return ret;
  }
  

  /**
   * Return (but do not remove) a random item.
   * @return a random item of the array
   */
  public Item sample() {
    if (num == 0) {
      throw new NoSuchElementException();
    }
    return queue[StdRandom.uniform(num)];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private int count = num;
    private int number = count - 1;
    private int[] shu;

    private RandomizedQueueIterator() {
      shu = new int[num];
      for (int i = 0; i < num; i++) {
        shu[i] = i;
      }
      StdRandom.shuffle(shu);
    }

    @Override
    public boolean hasNext() {
      // TODO Auto-generated method stub
      return count != 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item temp = queue[shu[number]];
      number--;
      count--;
      return temp;
    }

    @Override
    public void remove() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException();
    }
  }

  /**
   * Unit testing.
   * @param args arguments
   */
  public static void main(String[] args) {
    RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
    rq.enqueue(726);
    rq.isEmpty();
    System.out.println(rq.dequeue());

    for (int i : rq) {
      System.out.println(i);
    }

  }
  

}