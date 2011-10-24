/*
 * Dot.fx
 *
 * Created on 2008-12-21, 21:59:45
 */
package pacman;

/**
 * @author Henry Zhang
 */
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Dot extends Parent {

//  public Maze maze; // patweb
//  public var shouldStopAnimation : Boolean = false;
//  public boolean shouldStopAnimation;
  public BooleanProperty shouldStopAnimation;
//  public var dotType: Integer;
  public int dotType;
  // location of the dot
//  public var x : Number ;
  public int x;
//  public var y : Number ;
  public int y;
  // radius of the dot
//  public var r: Number =
//  if (  dotType == MazeData.MAGIC_DOT ) 5 else 1;
//  public int r;
  public IntegerProperty r;
  // the dot
//  var circle = Circle{
// //   cache: true
//    centerX: x
//    centerY: y
//    radius: bind r
//    fill: Color.YELLOW
////    visible: bind visible   
//    } ;
  public Circle circle;
  // variables for magic dot's growing/shrinking animation
//  public var animationRadius: Number = 3;
  public int animationRadius;
//  public var delta: Number = -1;
  public int delta;
//  var timeline: Timeline;
  public Timeline timeline;

  // create the animation timeline for magic dot
//  public function createTimeline(): Timeline {
//    Timeline {
//      interpolate: false
//      repeatCount: Timeline.INDEFINITE
//      keyFrames: [
//        KeyFrame {
//          time: 250ms
//          action: function() {
//            doOneTick();
//          }
//        }
//      ]
//        }
//  }
  private void createTimeline() {
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.seconds(0.25), new EventHandler<ActionEvent>() {

      public void handle(ActionEvent event) {
        doOneTick();
      }
    });

    timeline.getKeyFrames().add(kf);
  }

//  public function playTimeline() {
  public void playTimeline() {
    if (timeline == null) {
//    timeline = createTimeline();
      createTimeline();
    }

    timeline.play();
  }

  // do the animation
//  public function doOneTick () {
  public void doOneTick() {
//    System.out.println("calling one tick in dot with animationRadius=" + animationRadius);

//    if (maze.gamePaused || maze.waitForStart) {
//      shouldStopAnimation = true;
//    } else {
//      shouldStopAnimation = false;
//    }
    
    if (!isVisible() || shouldStopAnimation.get()) {
      return;
    }

    animationRadius += delta;
//    var x = Math.abs(animationRadius) + 3;
    int x1 = Math.abs(animationRadius) + 3;

    if (x1 > 5) {
      delta = -delta;
    }

//    r = x1;
    r.set(x1);
//    circle.setRadius(r); // patweb this works but should use binding
  }

//  public override function create(): Node {
//        return circle;
//  }


//  public Dot(Maze maze, int x, int y, int dotType) {
  public Dot(int x, int y, int dotType) {
//    this.maze = maze;
    
    //shouldStopAnimation: bind gamePaused or waitForStart
//    this.shouldStopAnimation = false;
    this.shouldStopAnimation = new SimpleBooleanProperty(false);
    this.x = x;
    this.y = y;
    this.dotType = dotType;
    if (dotType == MazeData.MAGIC_DOT) {
//      this.r = 5;
      this.r = new SimpleIntegerProperty(5);
    } else {
//      this.r = 1;
      this.r = new SimpleIntegerProperty(1);
    }
    delta = -1;
    animationRadius = 3;
//    circle = new Circle(x, y, r, Color.YELLOW);
    circle = new Circle(x, y, r.intValue(), Color.YELLOW);
//    System.out.println("radiusProperty is " + circle.radiusProperty());
    circle.radiusProperty().bind(r);
    getChildren().add(circle);
  }
}