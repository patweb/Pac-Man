/*
 * PacMan.fx
 *
 * Created on 2009-1-1, 11:50:58
 */

package pacman;

import java.io.InputStream;
import javafx.animation.Animation;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;

/**
 * @author Henry Zhang
 * http://www.javafxgame.com
 */

//public class PacMan extends Parent, MovingObject {
public class PacMan extends MovingObject {

//  public var defaultImage: Image = Image {
  public Image defaultImage;
//    url: "{__DIR__}images/left1.png"
//  };

  // images for animation
//  def images = [
//    defaultImage,
//    Image {
//      url: "{__DIR__}images/left2.png"
//    },
//    defaultImage,
//    Image {
//      url: "{__DIR__}images/round.png"
//    }
//  ];
//  public Image[] images; // = {defaultImage,
//    new Image("{__DIR__}images/left2.png"),
//    defaultImage,
//    new Image("{__DIR__}images/round.png")
//  };

  // the number of dots eaten
//  public var dotEatenCount : Integer = 0;
  public int dotEatenCount;

  // scores of the game
//  public var scores: Integer = 0;
//  public int scores;
  public SimpleIntegerProperty scores;
    
  // angles of rotating the images
//  def rotationDegree = [0, 90, 180, 270];
  public static final double[] rotationDegree = new double[] {0, 90, 180, 270};

  // GUI image of the man
//  var pacmanImage : ImageView = ImageView {
//    x: bind imageX - 13
//    y: bind imageY - 13
//    image: bind images[currentImage]
//  //  cache: true
//    transforms: Rotate {
//      angle: bind rotationDegree[currentDirection]
//      pivotX: bind imageX
//      pivotY: bind imageY
//      }
//   }
  public ImageView pacmanImage;

  // buffer to keep the keyboard input
//  var keyboardBuffer: Integer = -1;
  public int keyboardBuffer;

  // current direction of Pacman
//  var currentDirection: Integer = MOVE_LEFT;
//  public int currentDirection;
  public SimpleIntegerProperty currentDirection;

//  postinit {
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridX(y);
//    
//    xDirection = -1;
//    yDirection = 0;
//
//  }
  public PacMan(Maze maze, int x, int y) {
    this.maze = maze;
    this.x = x;
    this.y = y;
//    this.x = new SimpleIntegerProperty(x);
//    this.y = new SimpleIntegerProperty(y);
    defaultImage = new Image(getClass().getResourceAsStream("images/left1.png"));
    images = new Image[]{defaultImage,
      new Image(getClass().getResourceAsStream("images/left2.png")),
      defaultImage,
      new Image(getClass().getResourceAsStream("images/round.png"))
    };
    dotEatenCount = 0;
//    scores = 0;
    scores = new SimpleIntegerProperty(0);
//    currentDirection = MOVE_LEFT;
    currentDirection = new SimpleIntegerProperty(MOVE_LEFT);
    
  
      DoubleBinding rotationBinding = new DoubleBinding() {

          {
              super.bind(currentDirection);
          }

          @Override
          protected double computeValue() {
              return rotationDegree[currentDirection.get()];
          }
      };
      
//      ObjectBinding imageBinding = new ObjectBinding() {
//
//          {
//              super.bind(currentImage);
//          }
//
//          @Override
//          protected Image computeValue() {
//              return images[currentImage.get()];
//          }
//      };

    pacmanImage = new ImageView(defaultImage);
//    pacmanImage.setX(bind imageX - 13);
//    pacmanImage.setY(bind imageY - 13);
//    pacmanImage.setImage(bind images[currentImage]);
      //    transforms: Rotate {
      //      angle: bind rotationDegree[currentDirection]
      //      pivotX: bind imageX
      //      pivotY: bind imageY
      //      }
      //   }
    pacmanImage.rotateProperty().bind(rotationBinding);
    pacmanImage.imageProperty().bind(imageBinding);
    keyboardBuffer = -1;
    // postinit
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridX(y);
    imageX = new SimpleIntegerProperty(MazeData.calcGridX(x));
    imageY = new SimpleIntegerProperty(MazeData.calcGridX(y));
    
    xDirection = -1;
    yDirection = 0;
//    changeImage();
    
    pacmanImage.xProperty().bind(imageX.add(-13));
    pacmanImage.yProperty().bind(imageY.add(-13));
//    pacmanImage.translateXProperty().bind(imageX.add(-13));
//    pacmanImage.translateYProperty().bind(imageY.add(-13));
    pacmanImage.setCache(true);

    // end postinit
//    Circle circ = new Circle(imageX, imageY, 13, Color.YELLOW); // patweb
//    Circle circ = new Circle(imageX.get(), imageY.get(), 13, Color.YELLOW); // patweb
//    circ.centerXProperty().bind(imageX);
//    circ.centerYProperty().bind(imageY);
//    getChildren().add(circ); // patweb
    getChildren().add(pacmanImage);// patweb
  }
  
//  public void changeImage() {
////    pacmanImage.setRotate(rotationDegree[currentDirection]);
////    Rotate r = new Rotate(rotationDegree[currentDirection], imageX, imageY);
////    Rotate r = new Rotate(rotationDegree[currentDirection], imageX.get(), imageY.get());
////    Rotate r = new Rotate(rotationDegree[currentDirection], imageX.get(), imageY.get());
////    System.out.println("changing pacman image: setting (x,y,r) to ("+imageX.getValue()+", "+imageY.getValue()+", "+r+")");
////    pacmanImage.getTransforms().setAll(r); // TODO: bind
////    pacmanImage.setX(imageX - 13); // TODO: bind
////    pacmanImage.setY(imageY - 13); // TODO: bind
////    pacmanImage.setTranslateX(imageX - 13); // TODO: bind
////    pacmanImage.setTranslateY(imageY - 13); // TODO: bind
////    pacmanImage.setX(imageX.get() - 13); // TODO: bind
////    pacmanImage.setY(imageY.get() - 13); // TODO: bind
////    pacmanImage.xProperty().bind(imageX.add(-13));
////    pacmanImage.yProperty().bind(imageY.add(-13));
////    pacmanImage.setTranslateX(imageX.get() - 13); // TODO: bind
////    pacmanImage.setTranslateY(imageY.get() - 13); // TODO: bind
////    pacmanImage.translateXProperty().bind(imageX.add(-13));
////    pacmanImage.translateYProperty().bind(imageY.add(-13));
////    System.out.println("imageX is " + imageX);
////    System.out.println("x is " + x);
////    System.out.println("imageY is " + imageY);
////    System.out.println("y is " + y);
////    System.out.println("current image is " + images[currentImage]);
////    pacmanImage.setImage(images[currentImage]); // TODO: bind
////    pacmanImage.setImage(images[currentImage]); // TODO: bind
//    pacmanImage.setImage(images[currentImage.get()]);
////    maze.changeScores();
//  }
  

  // moving horizontally
  public void moveHorizontally() {

    moveCounter++;

    if ( moveCounter < ANIMATION_STEP ) {
//      imageX += xDirection * MOVE_SPEED;
      imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
    }
    else {
      moveCounter = 0;
      x += xDirection;
//      x.set(x.get() + xDirection);
      
//      imageX = MazeData.calcGridX(x);
      imageX.set(MazeData.calcGridX(x));
//      imageX = MazeData.calcGridX(x.get());

      // the X coordinate of the next point in the grid
      int nextX = xDirection + x;
//      int nextX = xDirection + x.get();

      if ( (y == 14) && ( nextX <= 1 || nextX >= 28) ) {
//      if ( (y.get() == 14) && ( nextX <= 1 || nextX >= 28) ) {
        if ( (nextX < - 1) && (xDirection < 0) ) {
          x = MazeData.GRID_SIZE;
//          x.set(MazeData.GRID_SIZE);
//          imageX = MazeData.calcGridX(x);
          imageX.set(MazeData.calcGridX(x));
//          imageX = MazeData.calcGridX(x.get());
        }
        else {
          if ( (nextX > 30) && (xDirection > 0) ) {
            x = 0;
//            x.set(0);
//            imageX = MazeData.calcGridX(x);
            imageX.set(MazeData.calcGridX(x));
//            imageX = MazeData.calcGridX(x.get());
          }
        }
      }
      else // check if the character hits a wall
      if ( MazeData.getData(nextX, y) == MazeData.BLOCK ) {
//      if ( MazeData.getData(nextX, y.get()) == MazeData.BLOCK ) {
        state = STOP;
      }
    }
//    changeImage();
  }

  // moving vertically
  public void moveVertically() {
      
    moveCounter++;

    if ( moveCounter < ANIMATION_STEP ) {
//      imageY += yDirection * MOVE_SPEED;
      imageY.set(imageY.get() + (yDirection * MOVE_SPEED));
    }
    else {
      moveCounter = 0;
      y += yDirection;
//      y.set(y.get() + yDirection);//
//      imageY = MazeData.calcGridX(y);
      imageY.set(MazeData.calcGridX(y));
//      imageY = MazeData.calcGridX(y.get());

      // the Y coordinate of the next point in the grid
      int nextY = yDirection + y;
//      int nextY = yDirection + y.get();

      // check if the character hits a wall
      if ( MazeData.getData(x, nextY) == MazeData.BLOCK ) {
//      if ( MazeData.getData(x.get(), nextY) == MazeData.BLOCK ) {
        state = STOP;
      }
    }
  }

  // turn pac-man to the right
  public void moveRight() {

//    if ( currentDirection == MOVE_RIGHT )  return;
    if ( currentDirection.get() == MOVE_RIGHT ) {
        return;
    }

    int nextX = x + 1;
//    int nextX = x.get() + 1;

    if ( nextX >= MazeData.GRID_SIZE)  return;

    if ( MazeData.getData(nextX, y) == MazeData.BLOCK )   return;
//    if ( MazeData.getData(nextX, y.get()) == MazeData.BLOCK ) {
//        return;
//    }

    xDirection = 1;
    yDirection = 0;

    keyboardBuffer = -1;
//    currentDirection = MOVE_RIGHT;
    currentDirection.set(MOVE_RIGHT);

    state = MOVING;
  }

  // turn pac-man to the left
  public void moveLeft() {

//    if ( currentDirection == MOVE_LEFT )   return;
    if ( currentDirection.get() == MOVE_LEFT ) {
        return;
    }

    int nextX = x - 1;
//    int nextX = x.get() - 1;

    if ( nextX <= 1)  return;

    if ( MazeData.getData(nextX, y) == MazeData.BLOCK )   return;
//    if ( MazeData.getData(nextX, y.get()) == MazeData.BLOCK )   return;

    xDirection = -1;
    yDirection = 0;

    keyboardBuffer = -1;
//    currentDirection = MOVE_LEFT;
    currentDirection.set(MOVE_LEFT);

    state = MOVING;
  }

  // turn pac-man going up
  public void moveUp() {

//    if ( currentDirection == MOVE_UP )  return;
    if ( currentDirection.get() == MOVE_UP )  return;

    int nextY = y - 1;
//    int nextY = y.get() - 1;

    if ( nextY <= 1)   return;

    if ( MazeData.getData(x,nextY) == MazeData.BLOCK )  return;
//    if ( MazeData.getData(x.get(),nextY) == MazeData.BLOCK ){
//        return;
//    }

    xDirection = 0;
    yDirection = -1;

    keyboardBuffer = -1;
//    currentDirection = MOVE_UP;
    currentDirection.set(MOVE_UP);

    state = MOVING;
  }

  // turn pac-man going down
  public void moveDown() {

//    if ( currentDirection == MOVE_DOWN ) return;
    if ( currentDirection.get() == MOVE_DOWN ) {
        return;
    }

    int nextY = y + 1;
//    int nextY = y.get() + 1;

    if ( nextY >= MazeData.GRID_SIZE )  return;

    if ( MazeData.getData(x,nextY) == MazeData.BLOCK )  return;
//    if ( MazeData.getData(x.get(),nextY) == MazeData.BLOCK ){
//        return;
//    }

    xDirection = 0;
    yDirection = 1;

    keyboardBuffer = -1;
//    currentDirection = MOVE_DOWN;
    currentDirection.set(MOVE_DOWN);

    state = MOVING;
  }

  // handle keyboard input
  public void handleKeyboardInput() {
    if (keyboardBuffer < 0) {
      return;
    }

    if (keyboardBuffer == MOVE_LEFT) {
      moveLeft();
    } else if (keyboardBuffer == MOVE_RIGHT) {
      moveRight();
    } else if (keyboardBuffer == MOVE_UP) {
      moveUp();
    } else if (keyboardBuffer == MOVE_DOWN) {
      moveDown();
    }
  }

  public void setKeyboardBuffer( int k ) {
    keyboardBuffer = k;
  }

  // update scores if a dot is eaten
  public void updateScores() {
    if ( y != 14 || ( x > 0 && x < MazeData.GRID_SIZE ) ) {
//    if ( y.get() != 14 || ( x.get() > 0 && x.get() < MazeData.GRID_SIZE ) ) {
      Dot dot = (Dot) MazeData.getDot( x, y );
//      Dot dot = (Dot) MazeData.getDot( x.get(), y.get() );

      if ( dot != null && dot.isVisible() ) {
//        scores += 10;
        scores.set(scores.get() + 10);
        dot.setVisible(false);
        dotEatenCount ++;

//        if ( scores >= 10000 ) {
        if ( scores.get() >= 10000 ) {
          maze.addLife();
        }

        if ( dot.dotType == MazeData.MAGIC_DOT ) {
          maze.makeGhostsHollow();
        }

        // check if the player wins and should start a new level
        if ( dotEatenCount >= MazeData.DOT_TOTAL )
          maze.startNewLevel();
      }
    }
  }

  public void hide() {
    setVisible(false);
    timeline.stop();
  }

  // handle animation of one tick
//  public override function moveOneStep() {
  @Override
  public void moveOneStep() {
    if ( maze.gamePaused.get() ) {
//      if ( timeline.paused == false ) {
      if ( timeline.getStatus() != Animation.Status.PAUSED ) {
        timeline.pause();
      }
      return;
    }

    // handle keyboard input only when pac-man is at a point of the grid
//    if ( currentImage == 0 ) {
    if ( currentImage.get() == 0 ) {
      handleKeyboardInput();
    }

    if ( state == MOVING ) {
      if ( xDirection != 0 ) {
        moveHorizontally();
      }

      if ( yDirection != 0 ) {
        moveVertically();
      }

      // switch to the image of the next frame
//      if ( currentImage < ANIMATION_STEP - 1  ) {
      if ( currentImage.get() < ANIMATION_STEP - 1  ) {
//        currentImage++;
        currentImage.set(currentImage.get() + 1);
      }
      else {
//        currentImage=0;
        currentImage.set(0);
        updateScores();
      }
    }

//    changeImage();
    maze.pacManMeetsGhosts();    
  }

  // place Pac-Man at the startup position for a new game
  public void resetStatus() {
    state = MOVING;
//    currentDirection = MOVE_LEFT;
    currentDirection.set(MOVE_LEFT);
    xDirection = -1;
    yDirection = 0;
    
    keyboardBuffer = -1;
//    currentImage = 0;
    currentImage.set(0);
    moveCounter = 0;

    x=15;
    y=18;
//    x.set(15);
//    y.set(18);

//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridY(y);
    imageX.set(MazeData.calcGridX(x));
    imageY.set(MazeData.calcGridY(y));
//    imageX = MazeData.calcGridX(x.get());
//    imageY = MazeData.calcGridY(y.get());

    setVisible(true); // BUG: Pacman does not show-up at start
    start();
  }


}
