/*
 * MovingObject.fx
 *
 * Created on 2009-1-1, 11:40:49
 */

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
 * @author Henry Zhang
 */

//public mixin class MovingObject {
public abstract class MovingObject extends Parent {

  // animation frames total and movement distance
  public final static int ANIMATION_STEP = 4;
  public final static int MOVE_SPEED = MazeData.GRID_GAP / ANIMATION_STEP;

  public final static int MOVING = 1;
  public final static int STOP =0;
  
  public final static int MOVE_LEFT=0;
  public final static int MOVE_UP=1;
  public final static int MOVE_RIGHT=2;
  public final static int MOVE_DOWN=3;

  public Maze maze;
  public int state;

//  public int currentImage; //=0;
  public IntegerProperty currentImage; //=0;
  public Image[] images;
  ObjectBinding imageBinding;
  public int moveCounter; //=0;

  // grid coordinates
//  public var x: Number;
//  public var y: Number;
  public int x;
  public int y;
//  public IntegerProperty x;
//  public IntegerProperty y;

  // graphical coordinates
//  public int imageX;
//  public int imageY;
  public IntegerProperty imageX;
  public IntegerProperty imageY;

  public int xDirection; // = 0;
  public int yDirection; // = 0;

  public Timeline timeline; // = createTimeline();

  public MovingObject() {
//    currentImage = 0;
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
//    System.out.println("isRunning() current rate is " + timeline.getCurrentRate());
    return timeline.getStatus() == Animation.Status.RUNNING;
  }

  public boolean isPaused() {
//    return timeline.paused;
//    System.out.println("isPaused() current rate is " + timeline.getCurrentRate());
    return timeline.getStatus() == Animation.Status.PAUSED;
  }

  // animation time line, moving the pacman
  public Timeline createTimeline() {
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    KeyFrame kf = new KeyFrame(Duration.millis(45), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              moveOneStep();
            }
        }
            );
    timeline.getKeyFrames().add(kf);
    
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
    return timeline;
  }

  public abstract void moveOneStep();
}
