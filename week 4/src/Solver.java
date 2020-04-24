import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Collections;

public class Solver {

  private boolean solveable = true;
  private BoardQueueItem solution = null;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }
    solveTheBoard(initial);
  }

  private void solveTheBoard(Board initial) {
    MinPQ<BoardQueueItem> minPQ = new MinPQ<>();
    minPQ.insert(new BoardQueueItem(initial, null, 0));
    while (!minPQ.min().board.isGoal()) {
      BoardQueueItem minItem = minPQ.delMin();
      if (minItem.board.isGoal()) {
        break;
      }
      if (minItem.board.twin().isGoal()) {
        solveable = false;
        break;
      }
      for (Board neighbor : minItem.board.neighbors()) {
        if (canInsertBoard(neighbor, minItem)) {
          minPQ.insert(new BoardQueueItem(neighbor, minItem, minItem.moves + 1));
        }
      }
    }
    if (solveable) {
      solution = minPQ.min();
    }
  }

  private boolean canInsertBoard(Board neighbor, BoardQueueItem minBoard) {
    if (minBoard.previous != null) {
      return !neighbor.equals(minBoard.previous.board);
    }
    return true;
  }

  // is the initial board solvable? (see below)
  public boolean isSolvable() {
    return solveable;
  }

  // min number of moves to solve initial board
  public int moves() {
    if (solution != null) {
      return solution.moves;
    } else {
      return -1;
    }
  }

  // sequence of boards in a shortest solution
  public Iterable<Board> solution() {
    if (solution == null) {
      return null;
    }
    ArrayList<Board> boards = new ArrayList<>();
    BoardQueueItem item = solution;
    while (item != null) {
      boards.add(item.board);
      item = item.previous;
    }
    Collections.reverse(boards);
    return boards;
  }

  private static class BoardQueueItem implements Comparable<BoardQueueItem> {

    private final Board board;
    private final BoardQueueItem previous;
    private final int moves;

    public BoardQueueItem(Board board, BoardQueueItem previous, int moves) {
      this.board = board;
      this.previous = previous;
      this.moves = moves;
    }

    @Override
    public int compareTo(BoardQueueItem that) {
      int thisManhattanPriority = this.board.manhattan() + this.moves;
      int thatManhattanPriority = that.board.manhattan() + that.moves;
      return thisManhattanPriority - thatManhattanPriority;
    }
  }
}