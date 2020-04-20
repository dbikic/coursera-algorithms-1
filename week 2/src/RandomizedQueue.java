import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private int itemCount;
  private int arraySize;
  private Item[] items;

  // construct an empty randomized queue
  public RandomizedQueue() {
    arraySize = 1;
    itemCount = 0;
    items = (Item[]) new Object[arraySize];
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return itemCount == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return itemCount;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    doubleTheArraySizeIfNeeded();
    items[itemCount] = item;
    itemCount++;
  }

  private void doubleTheArraySizeIfNeeded() {
    if (items.length == itemCount) {
      Item[] copy = (Item[]) new Object[items.length * 2];
      for (int i = 0; i < items.length; i++) {
        Item item = items[i];
        if (item != null) {
          copy[i] = item;
        }
      }
      items = copy;
      arraySize = items.length;
    }
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int indexToRemove = StdRandom.uniform(size());
    Item itemToRemove = items[indexToRemove];
    Item[] copy = (Item[]) new Object[items.length];
    for (int i = 0; i < items.length; i++) {
      int copyIndex;
      if (i >= indexToRemove) {
        copyIndex = i + 1;
      } else {
        copyIndex = i;
      }
      if (copyIndex < items.length) {
        copy[i] = items[copyIndex];
      }
    }
    items = copy;
    itemCount--;
    quarterTheArraySizeIfNeeded();
    return itemToRemove;
  }

  private void quarterTheArraySizeIfNeeded() {
    if (!isEmpty() && size() == items.length / 4) {
      Item[] copy = (Item[]) new Object[size()];
      for (int i = 0; i < items.length; i++) {
        Item item = items[i];
        if (item != null) {
          copy[i] = item;
        }
      }
      items = copy;
      arraySize = items.length;
    }
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    return items[StdRandom.uniform(size())];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private final RandomizedQueue<Item> copy;

    RandomizedQueueIterator() {
      copy = new RandomizedQueue<>();
      for (int i = 0; i < size(); i++) {
        copy.enqueue(items[i]);
      }
    }

    @Override
    public boolean hasNext() {
      return copy.size() > 0;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return copy.dequeue();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
    randomizedQueue.enqueue(1);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(2);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(3);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(4);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(5);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(6);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(7);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(8);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(9);
    printQueue(randomizedQueue);
    randomizedQueue.enqueue(10);
    printQueue(randomizedQueue);
    StdOut.println("Sample: " + randomizedQueue.sample());
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
    randomizedQueue.dequeue();
    printQueue(randomizedQueue);
  }

  private static <E> void printQueue(RandomizedQueue<E> randomizedQueue) {
    StdOut.println("Printing array:");
    for (E e : randomizedQueue) {
      StdOut.print(e.toString() + " ");
    }
    StdOut.println();
  }
}