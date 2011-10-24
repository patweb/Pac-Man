/*
 * WallBlackLine.fx
 *
 * Created on 2008-12-27, 17:52:58
 */

package pacman;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @author Henry Zhang
 */

public class WallBlackLine extends Line {

//  public var x1: Number;
//  public var y1: Number;
//  public var x2: Number;
//  public var y2: Number;
  public int x1;
  public int y1;
  public int x2;
  public int y2;

//  postinit {
//
//    cache = true;
//
//    strokeWidth = MazeData.GRID_STROKE + 1;
//    stroke = Color.BLACK;
//        
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
//  } // end postinit
  
  public WallBlackLine(int x1, int y1, int x2, int y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    
    // postinit
    setCache(true);
    
    setStrokeWidth(MazeData.GRID_STROKE + 1);
    setStroke(Color.BLACK);
    
    if ( x1 == x2 ) { // vertically line
      setStartX(MazeData.calcGridX(x1));
      setStartY(MazeData.calcGridY(y1) + MazeData.GRID_STROKE);
      setEndX(MazeData.calcGridX(x2));
      setEndY(MazeData.calcGridY(y2) - MazeData.GRID_STROKE);
    }
    else  { // horizontal line
      setStartX(MazeData.calcGridX(x1) + MazeData.GRID_STROKE);
      setStartY(MazeData.calcGridY(y1));
      setEndX(MazeData.calcGridX(x2) - MazeData.GRID_STROKE);
      setEndY(MazeData.calcGridY(y2));
    }
    // end postinit
  }
}
