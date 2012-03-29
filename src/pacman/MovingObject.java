package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * MovingObject.fx created on 2009-1-1, 11:40:49 <br>
 * MovingObject.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public abstract class MovingObject extends Parent {
//public mixin class MovingObject {

  // animation frames total and movement distance
  protected static final int ANIMATION_STEP = 4;
  protected static final int MOVE_SPEED = MazeData.GRID_GAP / ANIMATION_STEP;

  protected static final int MOVING = 1;
  protected static final int STOPPED = 0;

  protected static final int MOVE_LEFT = 0;
  protected static final int MOVE_UP = 1;
  protected static final int MOVE_RIGHT = 2;
  protected static final int MOVE_DOWN = 3;

  protected Maze maze;
  protected int state;

//  public var currentImage=0;
  protected IntegerProperty currentImage;
  protected Image[] images;
  protected ObjectBinding imageBinding;
//  public var moveCounter: Integer=0;
  protected int moveCounter;

  // grid coordinates
//  public var x: Number;
//  public var y: Number;
  protected int x;
  protected int y;

  // graphical coordinates
//  public var imageX: Number ;
//  public var imageY: Number ;
  public IntegerProperty imageX;
  public IntegerProperty imageY;

//  public var xDirection: Number = 0;
//  public var yDirection: Number = 0;
  protected int xDirection;
  protected int yDirection;

//  public var timeline: Timeline =  createTimeline();
  protected Timeline timeline;

  public MovingObject() {
    currentImage = new SimpleIntegerProperty(0);

    imageBinding = new ObjectBinding() {

      {
        super.bind(currentImage);
      }

      @Override
      protected Image computeValue() {
        return images[currentImage.get()];
      }
    };

    moveCounter = 0;
    xDirection = 0;
    yDirection = 0;
    timeline = createTimeline();
  }

  public abstract void moveOneStep();

  // animation time line, moving the pacman
//    Timeline {
//     // interpolate: false
//      repeatCount: Timeline.INDEFINITE
//      keyFrames: [
//        KeyFrame {
//          time: 45ms
//          action: function() {
//            moveOneStep();
//          }
//        }
//      ]
//     }
  private Timeline createTimeline() {
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        moveOneStep();
      }

    });
    timeline.getKeyFrames().add(kf);

    return timeline;
  }

  public void stop() {
    timeline.stop();
  }

  public void pause() {
    timeline.pause();
  }

  public void start() {
    timeline.play();
  }

  public boolean isRunning() {
//    return timeline.running;
    return timeline.getStatus() == Animation.Status.RUNNING;
  }

  public boolean isPaused() {
//    return timeline.paused;
    return timeline.getStatus() == Animation.Status.PAUSED;
  }

}
