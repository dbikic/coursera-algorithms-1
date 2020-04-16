/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private boolean[][] grid;
  private final WeightedQuickUnionUF quickUnion;
  private int openCount;
  private final int virtualTop;
  private final int virtualBottom;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int gridSize) {
    if (gridSize <= 0) {
      throw new IllegalArgumentException("N must be larger than 0!");
    }
    grid = new boolean[gridSize][gridSize];
    openCount = 0;
    virtualTop = 0;
    virtualBottom = gridSize * gridSize + 1;
    quickUnion = new WeightedQuickUnionUF(gridSize * gridSize + 2);
    makeAllThePointsBlocked(gridSize);
  }

  private void makeAllThePointsBlocked(int gridSize) {
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        grid[i][j] = true;
      }
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (invalidGridPoint(row, col)) {
      throw new IllegalArgumentException("Grid points are invalid: " + row + " " + col);
    }
    if (isOpen(row, col)) {
      return;
    }
    grid[row - 1][col - 1] = false;
    openCount++;
    connectLeftNeighbour(row, col);
    connectRightNeighbour(row, col);
    connectTopNeighbour(row, col);
    connectBottomNeighbour(row, col);
  }

  private void connectLeftNeighbour(int row, int col) {
    int neighbourCol = col - 1;
    if (invalidGridPoint(row, neighbourCol)) {
      return;
    }
    if (isOpen(row, neighbourCol)) {
      quickUnion.union(
          getRowId(row - 1, col - 1),
          getRowId(row - 1, neighbourCol - 1)
      );
    }
  }

  private void connectRightNeighbour(int row, int col) {
    int neighbourCol = col + 1;
    if (invalidGridPoint(row, neighbourCol)) {
      return;
    }
    if (isOpen(row, neighbourCol)) {
      quickUnion.union(
          getRowId(row - 1, col - 1),
          getRowId(row - 1, neighbourCol - 1)
      );
    }
  }

  private void connectTopNeighbour(int row, int col) {
    if (row == 1) {
      quickUnion.union(getRowId(row - 1, col - 1), virtualTop);
    } else {
      int neighbourRow = row - 1;
      if (!invalidGridPoint(neighbourRow, col) && isOpen(neighbourRow, col)) {
        quickUnion.union(
            getRowId(row - 1, col - 1),
            getRowId(neighbourRow - 1, col - 1)
        );
      }
    }
  }

  private void connectBottomNeighbour(int row, int col) {
    if (row == grid.length) {
      quickUnion.union(getRowId(row - 1, col - 1), virtualBottom);
    } else {
      int neighbourRow = row + 1;
      if (!invalidGridPoint(neighbourRow, col) && isOpen(neighbourRow, col)) {
        quickUnion.union(
            getRowId(row - 1, col - 1),
            getRowId(neighbourRow - 1, col - 1)
        );
      }
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (invalidGridPoint(row, col)) {
      throw new IllegalArgumentException("Grid points are invalid: " + row + " " + col);
    }
    return !grid[row - 1][col - 1];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (invalidGridPoint(row, col)) {
      throw new IllegalArgumentException("Grid points are invalid: " + row + " " + col);
    }
    return pointsConnected(getRowId(row - 1, col - 1), virtualTop);
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openCount;
  }

  // does the system percolate?
  public boolean percolates() {
    return pointsConnected(virtualTop, virtualBottom);
  }

  private boolean invalidGridPoint(int row, int col) {
    int shiftedRow = row - 1;
    int shiftedCol = col - 1;
    boolean invalid = shiftedCol >= grid.length || shiftedCol < 0 || shiftedRow >= grid.length
        || shiftedRow < 0;
    return invalid;
  }

  private int getRowId(int row, int col) {
    return row * grid.length + col + 1;
  }

  private boolean pointsConnected(int point1, int point2) {
    int findPoint1 = quickUnion.find(point1);
    int findPoint2 = quickUnion.find(point2);
    return findPoint1 == findPoint2;
  }
}