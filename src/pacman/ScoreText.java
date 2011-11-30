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
 * Text object for showing score for eating a ghost, then disappears after 2 seconds.
 *
 * ScoreText.fx created on 2009-2-6, 17:52:42 <br>
 * ScoreText.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class ScoreText extends Parent {
//public class ScoreText extends Text {

//  override var font = Font { size: 11 };
  private static final Font SCORE_FONT = new Font(11);
//  override var fill = Color.YELLOW;
  private static final Color SCORE_FILL = Color.YELLOW;
  private static final int DISPLAY_TIME = 2;
  private final Text text;

  private Timeline timeline;

  private void createTimeline() {
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
//  };
    timeline = new Timeline();
    timeline.setCycleCount(1);
    KeyFrame kf = new KeyFrame(Duration.seconds(DISPLAY_TIME), new EventHandler<ActionEvent>() {

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
    text.setFont(SCORE_FONT);
    text.setFill(SCORE_FILL);
    createTimeline();
    getChildren().add(text);
    setVisible(isVisible);
  }
}
