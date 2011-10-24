/*
 * ScoreText.fx
 *
 * Created on 2009-2-6, 17:52:42
 *
 * text object for showing scores of eating a ghost, then disappears after 2s
 *
 */
package pacman;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author Henry Zhang
 */
//public class ScoreText extends Text{
public class ScoreText extends Parent {
//  override var font = Font { size: 11 };

  private final static Font font = new Font(11);
//  override var fill = Color.YELLOW;
  private final static Color fill = Color.YELLOW;
  private final Text text;
//  var timeline= Timeline {
//    repeatCount: 1
//    keyFrames: [
//      KeyFrame {
//        time: 2s
//        action: function() {
//          visible = false;
//        }
//      }
//    ]
//    };
  private Timeline timeline;

  private void createTimeline() {
    timeline = new Timeline();
    timeline.setCycleCount(1);
    KeyFrame kf = new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

      public void handle(ActionEvent event) {
        setVisible(false);
      }
    });
    timeline.getKeyFrames().add(kf);
  }

  public void showText() {
    setVisible(true);
    timeline.playFromStart();
  }

  public void setX (double x) {
    text.setX(x);
  }

  public void setY (double y) {
    text.setY(y);
  }
  
  public ScoreText(String s, boolean isVisible) { //patweb
    text = new Text(s);
    text.setFont(font);
    text.setFill(fill);
    createTimeline();
    getChildren().add(text);
    setVisible(isVisible);
  }
}
