package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * WallBlackLine.fx created on 2008-12-27, 17:52:58 <br>
 * WallBlackLine.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class WallBlackLine extends Line {

//  public var x1: Number;
//  public var y1: Number;
//  public var x2: Number;
//  public var y2: Number;

  public WallBlackLine(int x1, int y1, int x2, int y2) {

//  postinit {
//    cache = true;
    setCache(true);

//    strokeWidth = MazeData.GRID_STROKE + 1;
    setStrokeWidth(MazeData.GRID_STROKE + 1);
//    stroke = Color.BLACK;
    setStroke(Color.BLACK);

//    if ( x1 == x2 ) { // vertically line
//      startX = MazeData.calcGridX(x1);
//      startY = MazeData.calcGridY(y1) + MazeData.GRID_STROKE;
//      endX = MazeData.calcGridX(x2);
//      endY = MazeData.calcGridY(y2) - MazeData.GRID_STROKE;
//    }
//    else  { // horizontal line
//      startX = MazeData.calcGridX(x1) + MazeData.GRID_STROKE;
//      startY = MazeData.calcGridY(y1);
//      endX = MazeData.calcGridX(x2) - MazeData.GRID_STROKE;
//      endY = MazeData.calcGridY(y2);
//    }
    if (x1 == x2) { // vertical line
      setStartX(MazeData.calcGridX(x1));
      setStartY(MazeData.calcGridY(y1) + MazeData.GRID_STROKE);
      setEndX(MazeData.calcGridX(x2));
      setEndY(MazeData.calcGridY(y2) - MazeData.GRID_STROKE);
    }
    else { // horizontal line
      setStartX(MazeData.calcGridX(x1) + MazeData.GRID_STROKE);
      setStartY(MazeData.calcGridY(y1));
      setEndX(MazeData.calcGridX(x2) - MazeData.GRID_STROKE);
      setEndY(MazeData.calcGridY(y2));
    }
//  } // end postinit
  }

}
