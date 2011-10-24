/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Owner
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
//        scene: Scene{ 
//                content: [ Maze {}
//                ]
//               }
        
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(new Maze());
        primaryStage.setScene(scene);
//        primaryStage.setVisible(true);
        primaryStage.show();
    }
}
