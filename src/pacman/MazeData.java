package pacman;

/**
 * MazeData.java
 *
 * @author Henry Zhang
 *
 * a 2D array for data model of the maze
 *
 */
public class MazeData {

  public final static int GRID_SIZE = 29;
  public final static int EMPTY = 0;
  public final static int BLOCK = 1;
  public final static int NORMAL_DOT = 2;
  public final static int MAGIC_DOT = 3;

  public static int DOT_TOTAL = 0;

  public static int mazeData[][] = new int[GRID_SIZE + 1][GRID_SIZE + 1];

  static Object dotPointers[][] = new Object[GRID_SIZE + 1][GRID_SIZE + 1];

  public final static int GRID_GAP = 16;
  public final static int GRID_STROKE = 2;
  final static int xoffset = GRID_GAP * 2;
  final static int yoffset = GRID_GAP * 2;

  public static int makeInRange(int a) {
    if (a < 0) {
      a = 0;
    } else if (a > GRID_SIZE) {
      a = GRID_SIZE;
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
      mazeData[i][y1] = BLOCK;
      mazeData[i][y2] = BLOCK;
    }

    for (int i = y1; i <= y2; i++) {
      mazeData[x1][i] = BLOCK;
      mazeData[x2][i] = BLOCK;
    }

  }

//  public static double calcGridX(double x) {
  public static int calcGridX(int x) {
//    System.out.println("calcGridX: (x, GRID_GAP, xoffset) = (" + x + ", " + GRID_GAP + ", " + xoffset + ") = " + (GRID_GAP * x + xoffset));
    return GRID_GAP * x + xoffset;
  }
  
  // float version
  public static float calcGridXFloat(float x) {
//    System.out.println("calcGridXFloat: (x, GRID_GAP, xoffset) = (" + x + ", " + GRID_GAP + ", " + xoffset + ") = " + (GRID_GAP * x + xoffset));
    return GRID_GAP * x + xoffset;
  }

//  public static double calcGridY(double y) {
  public static int calcGridY(int y) {
//    System.out.println("calcGridY: (y, GRID_GAP, yoffset) = (" + y + ", " + GRID_GAP + ", " + yoffset + ") = " + (GRID_GAP * y + yoffset));
    return GRID_GAP * y + yoffset;
  }
// float version
//  public static double calcGridY(double y) {
  public static float calcGridYFloat(float y) {
//    System.out.println("calcGridYFloat: (y, GRID_GAP, yoffset) = (" + y + ", " + GRID_GAP + ", " + yoffset + ") = " + (GRID_GAP * y + yoffset));
    return GRID_GAP * y + yoffset;
  }

  public static int getData(int x, int y) {
    return mazeData[x][y];
  }

  public static void setData(int x, int y, int value) {
    mazeData[x][y] = value;

    if ((value == MAGIC_DOT) || (value == NORMAL_DOT)) {
      DOT_TOTAL++;
    }
  } // end setData

  public static Object getDot(int x, int y) {
    return dotPointers[x][y];
  }

  public static void setDot(int x, int y, Object dot) {
    dotPointers[x][y] = dot;
  }
}
