package pacman;

/**
 * MazeData.java <br>
 * A 2D array for the data model of the maze.
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public final class MazeData {

  public static final int BLOCK = 1;
  public static final int EMPTY = 0;
  public static final int GRID_GAP = 16;
  public static final int GRID_SIZE_X = 29;
  public static final int GRID_SIZE_Y = 31;
  public static final int GRID_STROKE = 2;
  public static final int MAGIC_DOT = 3;
  public static final int NORMAL_DOT = 2;

  private static final Object[][] DOT_POINTERS = new Object[GRID_SIZE_X + 1][GRID_SIZE_Y + 1];
  private static final int[][] MAZE_DATA = new int[GRID_SIZE_X + 1][GRID_SIZE_Y + 1];
  private static final int X_OFFSET = GRID_GAP * 2;
  private static final int Y_OFFSET = GRID_GAP * 2;

  private static int dotTotal = 0;

  /**
   * Private constructor to prevent instantiation.
   */
  private MazeData() { };

  private static int makeInRange(int a, char coordinate) {

    if (a < 0) {
      return 0;
    }
    else if ((coordinate == 'X') && (a > GRID_SIZE_X)) {
      return GRID_SIZE_X;
    }
    else if ((coordinate == 'Y') && (a > GRID_SIZE_Y)) {
      return GRID_SIZE_Y;
    }

    return a;
  }


  // set the grid of maze data to be BLOCK
  public static void setBlockMazeData(int x1, int y1, int x2, int y2) {
    x1 = makeInRange(x1, 'X');
    y1 = makeInRange(y1, 'Y');
    x2 = makeInRange(x2, 'X');
    y2 = makeInRange(y2, 'Y');

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

  // calcGridX float version
  public static float calcGridXFloat(final float x) {
    return GRID_GAP * x + X_OFFSET;
  }

  public static int calcGridY(int y) {
//  public static double calcGridY(double y) {
    return GRID_GAP * y + Y_OFFSET;
  }

  // calcGridY float version
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

  public static int getDotTotal() {
    return dotTotal;
  }

  public static void printData() {
    for (int i = 0; i <= GRID_SIZE_Y; i++) {
      for (int j = 0; j <= GRID_SIZE_X; j++) {
        System.out.print(MAZE_DATA[j][i] + " ");
      }
      System.out.println("");
    }
  }

  public static void printDots() {
    for (int i = 0; i <= GRID_SIZE_Y; i++) {
      for (int j = 0; j <= GRID_SIZE_X; j++) {
        if (null != DOT_POINTERS[j][i]) {
          System.out.print(((Dot) DOT_POINTERS[j][i]).dotType + " ");
        } else {
          System.out.print("  ");
        }
      }
      System.out.println("");
    }
  }

}
