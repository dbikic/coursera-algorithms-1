import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  // unit testing (required)
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    for (int i = 0; i < k; i++) {
      randomizedQueue.enqueue(StdIn.readString());
    }
    for (int i = 0; i < k; i++) {
      StdOut.println(randomizedQueue.dequeue());
    }
  }
}