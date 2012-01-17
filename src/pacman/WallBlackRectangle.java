package pacman;

//import javafx.scene.CustomNode;
//import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * WallBlackRectangle.fx created on 2008-12-27, 16:35:42 <br>
 * WallBlackRectangle.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class WallBlackRectangle extends Parent {
//public class WallBlackRectangle extends CustomNode {

//  public var x1: Number;
//  public var y1: Number;
//  public var x2: Number;
//  public var y2: Number;

  public WallBlackRectangle(float x1, float y1, float x2, float y2) {
//  public override function create(): Node {

//    Rectangle {
//      x: MazeData.calcGridX(x1) + MazeData.GRID_STROKE
//      y: MazeData.calcGridY(y1) + MazeData.GRID_STROKE
//      width: MazeData.GRID_GAP * (x2-x1) - MazeData.GRID_STROKE * 2
//      height: MazeData.GRID_GAP * (y2-y1) - MazeData.GRID_STROKE * 2
//      strokeWidth: MazeData.GRID_STROKE
//      stroke: Color.BLACK
//      arcWidth: 3
//      arcHeight: 3
//      cache: true
//    }

    Rectangle rectangle = new Rectangle();
    rectangle.setX(MazeData.calcGridXFloat(x1) + MazeData.GRID_STROKE);
    rectangle.setY(MazeData.calcGridYFloat(y1) + MazeData.GRID_STROKE);
    rectangle.setWidth(MazeData.GRID_GAP * (x2 - x1) - MazeData.GRID_STROKE * 2);
    rectangle.setHeight(MazeData.GRID_GAP * (y2 - y1) - MazeData.GRID_STROKE * 2);
    rectangle.setStrokeWidth(MazeData.GRID_STROKE);
    rectangle.setStroke(Color.BLACK);
    rectangle.setArcWidth(3);
    rectangle.setArcHeight(3);
    rectangle.setCache(true);

    getChildren().add(rectangle); // patweb
  }

}
