import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int size;

  // construct an empty deque
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return first == null && last == null;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node oldFirst = first;
    first = new Node(item, oldFirst);
    if (last == null) {
      last = oldFirst;
    }
    size++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    Node oldLast = last;
    last = new Node(item, null);
    if (oldLast != null) {
      oldLast.next = last;
    } else {
      first = last;
    }
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Node itemToRemove = first;
    Node newFirst = first.next;
    if (newFirst == null) {
      first = null;
      last = null;
    } else {
      first = newFirst;
    }
    size--;
    return itemToRemove.item;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Node itemToRemove = last;
    Node newLast = first;
    if (newLast == itemToRemove) {
      newLast = null;
    } else {
      while (newLast.next != itemToRemove) {
        newLast = newLast.next;
      }
    }
    if (newLast == null) {
      first = null;
      last = null;
    } else {
      last = newLast;
      last.next = null;
    }
    size--;
    return itemToRemove.item;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeueIterator();
  }

  private class DequeueIterator implements Iterator<Item> {

    private Node current = first;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item currentItem = current.item;
      current = current.next;
      return currentItem;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private class Node {

    private final Item item;
    private Node next;

    public Node(Item item, Node next) {
      this.item = item;
      this.next = next;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(1);
    printDequeue(deque);
    deque.addFirst(2);
    printDequeue(deque);
    deque.addFirst(3);
    printDequeue(deque);
    deque.removeLast();
    printDequeue(deque);
    deque.removeFirst();
    printDequeue(deque);
    deque.addLast(9);
    printDequeue(deque);
    StdOut.println("Remove last: " + deque.removeLast().toString());
    printDequeue(deque);
    StdOut.println("Remove first: " + deque.removeFirst().toString());
    printDequeue(deque);
    StdOut.println("Size: " + deque.size());
  }

  private static <E> void printDequeue(Deque<E> deque) {
    for (E e : deque) {
      StdOut.print(e.toString() + " ");
    }
    StdOut.println();
  }
}