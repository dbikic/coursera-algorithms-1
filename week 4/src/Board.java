import java.util.ArrayList;

public class Board {

  private static final int EMPTY_TILE = 0;
  private final int[][] tiles;
  private final int n;
  private int hamming = Integer.MIN_VALUE;
  private int manhattan = Integer.MIN_VALUE;
  private final int maxValue;

  // create a board from an n-by-n array of tiles, where tiles[row][col] = tile at (row, col)
  public Board(int[][] tiles) {
    this.tiles = tiles;
    n = tiles.length;
    maxValue = dimension() * dimension();
  }

  // string representation of this board
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(dimension());
    stringBuilder.append("\n");
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        stringBuilder.append(" ");
        stringBuilder.append(tiles[i][j]);
        stringBuilder.append(" ");
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  // board dimension n
  public int dimension() {
    return n;
  }

  // number of tiles out of place
  public int hamming() {
    if (hamming == Integer.MIN_VALUE) {
      calculateHamming();
    }
    return hamming;
  }

  private void calculateHamming() {
    int correctValueAtPosition = 1;
    hamming = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        if (correctValueAtPosition != tiles[i][j] && correctValueAtPosition != maxValue) {
          hamming++;
        }
        correctValueAtPosition++;
      }
    }
  }

  // sum of Manhattan distances between tiles and goal
  public int manhattan() {
    if (manhattan == Integer.MIN_VALUE) {
      calculateManhattan();
    }
    return manhattan;
  }

  private void calculateManhattan() {
    int correctValueAtPosition = 0;
    manhattan = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        correctValueAtPosition++;
        if (correctValueAtPosition == maxValue) {
          correctValueAtPosition = EMPTY_TILE;
        }
        if (tiles[i][j] == EMPTY_TILE) {
          continue;
        }
        if (correctValueAtPosition != tiles[i][j]) {
          int modDimension = tiles[i][j] % dimension();
          int correctPositionX;
          int correctPositionY;
          if (modDimension != 0) {
            correctPositionX = modDimension - 1;
            correctPositionY = tiles[i][j] / dimension();
          } else {
            correctPositionX = dimension() - 1;
            correctPositionY = tiles[i][j] / dimension() - 1;
          }
          if (correctPositionY > i) {
            manhattan += correctPositionY - i;
          } else if (i > correctPositionY) {
            manhattan += i - correctPositionY;
          }
          if (correctPositionX > j) {
            manhattan += correctPositionX - j;
          } else if (j > correctPositionX) {
            manhattan += j - correctPositionX;
          }
        }
      }
    }
  }

  // is this board the goal board?
  public boolean isGoal() {
    int correctValueAtPosition = 1;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        if (correctValueAtPosition == maxValue) {
          correctValueAtPosition = EMPTY_TILE;
        }
        if (correctValueAtPosition != tiles[i][j]) {
          return false;
        }
        correctValueAtPosition++;
      }
    }
    return true;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y instanceof Board) {
      if (((Board) y).dimension() != this.dimension()) {
        return false;
      }
      for (int i = 0; i < dimension(); i++) {
        for (int j = 0; j < dimension(); j++) {
          if (((Board) y).tiles[i][j] != this.tiles[i][j]) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    ArrayList<Board> neighbors = new ArrayList<>();
    int emptyTileX = 0, emptyTileY = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        if (tiles[i][j] == EMPTY_TILE) {
          emptyTileY = i;
          emptyTileX = j;
        }
      }
    }
    if (emptyTileY != 0) {
      neighbors.add(getTopNeighbor(emptyTileY, emptyTileX));
    }
    if (emptyTileX != 0) {
      neighbors.add(getLeftNeighbor(emptyTileY, emptyTileX));
    }
    if (emptyTileX != dimension() - 1) {
      neighbors.add(getRightNeighbor(emptyTileY, emptyTileX));
    }
    if (emptyTileY != dimension() - 1) {
      neighbors.add(getBottomNeighbor(emptyTileY, emptyTileX));
    }
    return neighbors;
  }

  private Board getTopNeighbor(int emptyTileY, int emptyTileX) {
    int[][] copy = getClonedTileArray();
    Board neighbor = new Board(copy);
    int copyValue = neighbor.tiles[emptyTileY - 1][emptyTileX];
    neighbor.tiles[emptyTileY - 1][emptyTileX] = EMPTY_TILE;
    neighbor.tiles[emptyTileY][emptyTileX] = copyValue;
    return neighbor;
  }

  private Board getLeftNeighbor(int emptyTileY, int emptyTileX) {
    int[][] copy = getClonedTileArray();
    Board neighbor = new Board(copy);
    int copyValue = neighbor.tiles[emptyTileY][emptyTileX - 1];
    neighbor.tiles[emptyTileY][emptyTileX - 1] = EMPTY_TILE;
    neighbor.tiles[emptyTileY][emptyTileX] = copyValue;
    return neighbor;
  }

  private Board getBottomNeighbor(int emptyTileY, int emptyTileX) {
    int[][] copy = getClonedTileArray();
    Board neighbor = new Board(copy);
    int copyValue = neighbor.tiles[emptyTileY + 1][emptyTileX];
    neighbor.tiles[emptyTileY + 1][emptyTileX] = EMPTY_TILE;
    neighbor.tiles[emptyTileY][emptyTileX] = copyValue;
    return neighbor;
  }

  private Board getRightNeighbor(int emptyTileY, int emptyTileX) {
    int[][] copy = getClonedTileArray();
    Board neighbor = new Board(copy);
    int copyValue = neighbor.tiles[emptyTileY][emptyTileX + 1];
    neighbor.tiles[emptyTileY][emptyTileX + 1] = EMPTY_TILE;
    neighbor.tiles[emptyTileY][emptyTileX] = copyValue;
    return neighbor;
  }

  private int[][] getClonedTileArray() {
    int[][] copy = new int[dimension()][dimension()];
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        copy[i][j] = tiles[i][j];
      }
    }
    return copy;
  }

  // a board that is obtained by exchanging any pair of tiles
  public Board twin() {
    int sourceY = Integer.MIN_VALUE;
    int sourceX = Integer.MIN_VALUE;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        if (sourceY == Integer.MIN_VALUE && tiles[i][j] != EMPTY_TILE) {
          sourceY = i;
          sourceX = j;
          break;
        }
      }
    }
    int destinationY;
    int destinationX;

    if (sourceY - 1 > 0 && tiles[sourceY - 1][sourceY] != EMPTY_TILE) {
      destinationY = sourceY - 1;
      destinationX = sourceX;
    } else if (sourceY + 1 <= dimension() - 1 && tiles[sourceY + 1][sourceX] != EMPTY_TILE) {
      destinationY = sourceY + 1;
      destinationX = sourceX;
    } else if (sourceX - 1 > 0 && tiles[sourceY][sourceY - 1] != EMPTY_TILE) {
      destinationY = sourceY;
      destinationX = sourceX - 1;
    } else {
      destinationY = sourceY;
      destinationX = sourceX + 1;
    }
    int[][] copyTiles = getClonedTileArray();
    int copy1 = copyTiles[sourceY][sourceX];
    int copy2 = copyTiles[destinationY][destinationX];
    copyTiles[sourceY][sourceX] = copy2;
    copyTiles[destinationY][destinationX] = copy1;
    return new Board(copyTiles);
  }
}