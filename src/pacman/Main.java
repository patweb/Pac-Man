package pacman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main.fx created on 2008-12-20, 12:02:26 <br>
 * Main.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class Main extends Application {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(Main.class, args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Pac-Man by Henry Zhang www.javafxgame.com and Patrick Webster");
    primaryStage.setWidth(MazeData.calcGridX(MazeData.GRID_SIZE + 2));
    primaryStage.setHeight(MazeData.calcGridY(MazeData.GRID_SIZE + 5));
//    scene: Scene{ 
//            content: [ Maze {}
//            ]
//           }

    final Group root = new Group();
    final Scene scene = new Scene(root);
    root.getChildren().add(new Maze());
    primaryStage.setScene(scene);
    primaryStage.show();
  }
  
}
