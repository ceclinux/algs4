import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Item[] arr;
  private int first = 0;
  private int last = 1;
  private int num;

  // construct an empty deque
  public Deque() {
    arr = (Item[]) new Object[2];
  }

  // is the deque empty?
  public boolean isEmpty() {
    return num == 0;
  }

  // return the number of items on the deque
  public int size() {
    return num;
  }


  /**
   * Add the item to the front.
   * @param item the added item
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (num == arr.length) {
      resize(2 * num);
    }

    first = (first - 1 + arr.length) % arr.length;

    arr[first] = item;
    num++;

  }


  /**
   * Add the item to the front.
   * @param item the added item
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (num == arr.length) {
      resize(2 * num);
    }

    last = (last + 1) % arr.length;
    arr[last] = item;
    num++;
  }


  /**
   * Remove and return the item from the front.
   * @return the removed item
   */
  public Item removeFirst() {
    if (num == 0) {
      throw new NoSuchElementException();
    }
    if (num == arr.length / 4) {
      resize(arr.length / 2);
    }
    Item item = arr[first];
    arr[first] = null;
    first = (first + 1) % arr.length;
    num--;
    return item;
  }


  /**
   * Remove and return the item from the end.
   * @return the removed item
   */
  public Item removeLast() {
    if (num == 0) {
      throw new NoSuchElementException();
    }
    if (num == arr.length / 4) {
      resize(arr.length / 2);
    }
    Item item = arr[last];
    arr[last] = null;
    last = (last - 1 + arr.length) % arr.length;
    num--;
    return item;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {
    private int count = num;
    private int fir = first;

    @Override
    public boolean hasNext() {
      return count != 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      count--;

      return arr[(fir++) % arr.length];
    }

    @Override
    public void remove() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException();
    }

  }
  /**
   * Resize the array.
   * @param max the size of the new array
   */
  private void resize(int max) {
    
    Item[] newarr = (Item[]) new Object[max];
    int count = 0;
    if (first > last) {
      last = last + arr.length;
    }
    for (int i = first; i <= last; i++) {
      newarr[count++] = arr[i % arr.length];
    }
    first = 0;
    last = num - 1;
    arr = newarr;
  }

  


  /**
   * Unit testing.
   * @param args arguments
   */
  public static void main(String[] args) {
    Deque<Integer> testdeque = new Deque<Integer>();
    testdeque.addFirst(new Integer(1));
    testdeque.addFirst(new Integer(2));
    testdeque.addFirst(new Integer(3));
    testdeque.addFirst(new Integer(4));
    testdeque.addFirst(new Integer(5));
    testdeque.addFirst(new Integer(6));
    testdeque.addLast(new Integer(7));
    testdeque.addLast(new Integer(8));
    testdeque.addLast(new Integer(9));

    // testdeque.removeLast();
    // testdeque.removeFirst();
    for (int i : testdeque) {
      System.out.println(i);
    }

  }
}
