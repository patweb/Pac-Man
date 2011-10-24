/*
 * WallRectangle.fx
 *
 * Created on 2008-12-25, 16:08:28
 */

package pacman;

import javafx.scene.Parent;
//import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Henry Zhang
 */

public class WallRectangle extends Parent {

  public float x1;
  public float y1;
  public float x2;
  public float y2;

//  postinit {
//    // initialize the data model while drawing the maze
//    MazeData.setBlockMazeData(x1, y1, x2, y2);
//    }

//  public override function create(): Node {
  public void create() {
//    Rectangle {
//      x: MazeData.calcGridX(x1)
//      y: MazeData.calcGridY(y1)
//      width: MazeData.calcGridX(x2) - MazeData.calcGridX(x1)
//      height: MazeData.calcGridY(y2) - MazeData.calcGridY(y1)
//      strokeWidth: MazeData.GRID_STROKE
//      stroke: Color.BLUE
//      arcWidth: 12
//      arcHeight: 12
//      cache: true
//    }
    Rectangle rectangle = new Rectangle();
    rectangle.setX(MazeData.calcGridXFloat(x1));
    rectangle.setY(MazeData.calcGridYFloat(y1));
    rectangle.setWidth(MazeData.calcGridXFloat(x2) - MazeData.calcGridXFloat(x1));
    rectangle.setHeight(MazeData.calcGridYFloat(y2) - MazeData.calcGridYFloat(y1));
    rectangle.setStrokeWidth(MazeData.GRID_STROKE);
    rectangle.setStroke(Color.BLUE);
    rectangle.setArcWidth(12);
    rectangle.setArcHeight(12);
    rectangle.setCache(true);
    getChildren().add(rectangle);
//    return rectangle;
  }
  
  public WallRectangle(float x1, float y1, float x2, float y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    
    create();
    
    MazeData.setBlockMazeData(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2));
  }
    
}
