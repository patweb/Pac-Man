package pacman;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

/**
 * DyingPacMan.fx created on 2009-02-06, 17:52:42 <br>
 * DyingPacMan.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class DyingPacMan extends Arc {

  private final Timeline timeline;

  public DyingPacMan(final Maze maze) {

    timeline = new Timeline();
    timeline.setCycleCount(1);

    KeyFrame kf1 =
          new KeyFrame(Duration.millis(600),
            new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                maze.pacMan.setVisible(false);

                for (Ghost g : maze.ghosts) {
                  g.hide();
                }

                setVisible(true);
              }

            },
            new KeyValue(startAngleProperty(), 90),
            new KeyValue(lengthProperty(), 360)
          );

    KeyFrame kf2 =
          new KeyFrame(Duration.millis(1800),
            new EventHandler<ActionEvent>() {

              @Override
              public void handle(ActionEvent event) {
                setVisible(false);
                maze.startNewLife();
              }

            },
            new KeyValue(startAngleProperty(), 270),
            new KeyValue(lengthProperty(), 0)
          );



    timeline.getKeyFrames().addAll(kf1, kf2);
  }

//  var timeline = Timeline {
//    repeatCount: 1
//    keyFrames: [
//
//      KeyFrame {
//        time: 600ms
//        action: function() {
//          // hide the pacMan character and ghosts before the animation
//          maze.pacMan.visible = false;
//
//          for ( g in maze.ghosts ) {
//            g.hide();
//          }
//
//          visible = true;
//        }
//        values: [ startAngle => 90, length=>360 ];
//      },
//
//      KeyFrame {
//        time: 1800ms
//        action: function() {
//          visible = false;
//          maze.startNewLife();
//         }
//        values: [ startAngle => 270 tween Interpolator.LINEAR,
//                  length => 0 tween Interpolator.LINEAR ]
//      },
//    ]
//  }

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

  public void startAnimation(int x, int y) {

//    startAngle = 90;
    setStartAngle(90);
//    centerX = x;
    setCenterX(x);
//    centerY = y;
    setCenterY(y);

    timeline.playFromStart();
  }

}
