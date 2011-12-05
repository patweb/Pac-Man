package pacman;

/**
 * MazeData.java <br>
 * A 2D array for the data model of the maze.
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public final class MazeData {

  public static final int GRID_SIZE = 29;
  public static final int EMPTY = 0;
  public static final int BLOCK = 1;
  public static final int NORMAL_DOT = 2;
  public static final int MAGIC_DOT = 3;

  public static int dotTotal = 0;

  private static final int MAZE_DATA[][] = new int[GRID_SIZE + 1][GRID_SIZE + 1];

  private static final Object DOT_POINTERS[][] = new Object[GRID_SIZE + 1][GRID_SIZE + 1];

  public static final int GRID_GAP = 16;
  public static final int GRID_STROKE = 2;
  private static final int X_OFFSET = GRID_GAP * 2;
  private static final int Y_OFFSET = GRID_GAP * 2;

  private MazeData() { };

  private static int makeInRange(int a) {

    if (a < 0) {
      return 0;
    }
    else if (a > GRID_SIZE) {
      return GRID_SIZE;
    }

    return a;
  }
    

  // set the grid of maze data to be BLOCK
  public static void setBlockMazeData(int x1, int y1, int x2, int y2) {
    x1 = makeInRange(x1);
    y1 = makeInRange(y1);
    x2 = makeInRange(x2);
    y2 = makeInRange(y2);

    for (int i = x1; i <= x2; i++) {
      MAZE_DATA[i][y1] = BLOCK;
      MAZE_DATA[i][y2] = BLOCK;
    }

    for (int i = y1; i <= y2; i++) {
      MAZE_DATA[x1][i] = BLOCK;
      MAZE_DATA[x2][i] = BLOCK;
    }

  }

  public static int calcGridX(int x) {
//  public static double calcGridX(double x) {
    return GRID_GAP * x + X_OFFSET;
  }
  
  // float version
  public static float calcGridXFloat(final float x) {
    return GRID_GAP * x + X_OFFSET;
  }

  public static int calcGridY(int y) {
//  public static double calcGridY(double y) {
    return GRID_GAP * y + Y_OFFSET;
  }
  
  // float version
  public static float calcGridYFloat(final float y) {
//  public static double calcGridY(double y) {
    return GRID_GAP * y + Y_OFFSET;
  }

  public static int getData(int x, int y) {
    return MAZE_DATA[x][y];
  }

  public static void setData(int x, int y, int value) {
    MAZE_DATA[x][y] = value;

    if ((value == MAGIC_DOT) || (value == NORMAL_DOT)) {
      dotTotal++;
    }
  } // end setData

  public static Object getDot(int x, int y) {
    return DOT_POINTERS[x][y];
  }

  public static void setDot(int x, int y, Object dot) {
    DOT_POINTERS[x][y] = dot;
  }

}
