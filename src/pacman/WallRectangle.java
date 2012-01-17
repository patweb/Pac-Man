package pacman;

//import javafx.scene.CustomNode;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * WallRectangle.fx created on 2008-12-25, 16:08:28 <br>
 * WallRectangle.java created October 2011
 * 
 * @author Henry Zhang
 * @author Patrick Webster 
 */
public class WallRectangle extends Parent {
//public class WallRectangle extends CustomNode {

//  public var x1: Number;
//  public var y1: Number;
//  public var x2: Number;
//  public var y2: Number;

  public WallRectangle(float x1, float y1, float x2, float y2) {
//  public override function create(): Node {

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

//  postinit {
//    // initialize the data model while drawing the maze
//    MazeData.setBlockMazeData(x1, y1, x2, y2);
//    }
    MazeData.setBlockMazeData(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2));
  }

}
