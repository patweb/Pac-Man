package pacman;

import javafx.animation.Animation;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PacMan.fx created on 2009-1-1, 11:50:58 <br>
 * PacMan.java created October 2011
 * 
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class PacMan extends MovingObject {
//public class PacMan extends CustomNode, MovingObject {

//  private Image defaultImage;
//  public var defaultImage: Image = Image {
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

 /**
  * The number of dots eaten.
  */
  public int dotEatenCount;
//  public var dotEatenCount : Integer = 0;

 /**
  * Score of the game.
  */
  public SimpleIntegerProperty score;
//  public var scores: Integer = 0;

 /**
  * Angles of rotating the images.
  */
  private static final double[] ROTATION_DEGREE = new double[] {0, 90, 180, 270};
//  def rotationDegree = [0, 90, 180, 270];

 /**
  * Buffer to keep the keyboard input.
  */
  private int keyboardBuffer;
//  var keyboardBuffer: Integer = -1;

 /**
  * Current direction of Pac-Man.
  */
  private final SimpleIntegerProperty currentDirection;
//  var currentDirection: Integer = MOVE_LEFT;

 /**
  * Constructor.
  *
  * @param maze
  * @param x
  * @param y
  */
  public PacMan(Maze maze, int x, int y) {

    this.maze = maze;
    this.x = x;
    this.y = y;

    Image defaultImage = new Image(getClass().getResourceAsStream("images/left1.png"));
    images = new Image[] {defaultImage,
      new Image(getClass().getResourceAsStream("images/left2.png")),
      defaultImage,
      new Image(getClass().getResourceAsStream("images/round.png"))
    };

    dotEatenCount = 0;
    score = new SimpleIntegerProperty(0);
    currentDirection = new SimpleIntegerProperty(MOVE_LEFT);

//  postinit {
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridX(y);
//
//    xDirection = -1;
//    yDirection = 0;
//
//  }
    imageX = new SimpleIntegerProperty(MazeData.calcGridX(x));
    imageY = new SimpleIntegerProperty(MazeData.calcGridX(y));

    xDirection = -1;
    yDirection = 0;

  // GUI image of the man
//  var PACMAN_IMAGE : ImageView = ImageView {
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
    ImageView pacmanImage = new ImageView(defaultImage);
    pacmanImage.xProperty().bind(imageX.add(-13));
    pacmanImage.yProperty().bind(imageY.add(-13));
    pacmanImage.imageProperty().bind(imageBinding);
//    pacmanImage.setCache(true);
    DoubleBinding rotationBinding = new DoubleBinding() {

      {
        super.bind(currentDirection);
      }

      @Override
      protected double computeValue() {
        return ROTATION_DEGREE[currentDirection.get()];
      }
    };
    pacmanImage.rotateProperty().bind(rotationBinding);

    keyboardBuffer = -1;

    getChildren().add(pacmanImage); // patweb
  }

 /**
  * moving horizontally.
  */
  private void moveHorizontally() {

    moveCounter++;

    if (moveCounter < ANIMATION_STEP) {
      imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
//      imageX += xDirection * MOVE_SPEED;
    }
    else {
      moveCounter = 0;
      x += xDirection;

      imageX.set(MazeData.calcGridX(x));
//      imageX = MazeData.calcGridX(x);

      // the X coordinate of the next point in the grid
      int nextX = xDirection + x;

      if ( (y == 14) && ( nextX <= 1 || nextX >= 28) ) {
        if ( (nextX < - 1) && (xDirection < 0) ) {
          x = MazeData.GRID_SIZE;
          imageX.set(MazeData.calcGridX(x));
//          imageX = MazeData.calcGridX(x);
        }
        else {
          if ( (nextX > 30) && (xDirection > 0) ) {
            x = 0;
            imageX.set(MazeData.calcGridX(x));
//            imageX = MazeData.calcGridX(x);
          }
        }
      }
      else // check if the character hits a wall
      if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
        state = STOPPED;
      }
    }
  }

 /**
  * moving vertically.
  */
  private void moveVertically() {

    moveCounter++;

    if (moveCounter < ANIMATION_STEP) {
      imageY.set(imageY.get() + (yDirection * MOVE_SPEED));
//      imageY += yDirection * MOVE_SPEED;
    }
    else {
      moveCounter = 0;
      y += yDirection;
      imageY.set(MazeData.calcGridX(y));
//      imageY = MazeData.calcGridX(y);

      // the Y coordinate of the next point in the grid
      int nextY = yDirection + y;

      // check if the character hits a wall
      if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
        state = STOPPED;
      }
    }
  }

 /**
  * Turn Pac-Man to the right.
  */
  private void moveRight() {

    if (currentDirection.get() == MOVE_RIGHT) {
//    if ( currentDirection == MOVE_RIGHT )  return;
        return;
    }

    int nextX = x + 1;

    if (nextX >= MazeData.GRID_SIZE) {
      return;
    }

    if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
      return;
    }

    xDirection = 1;
    yDirection = 0;

    keyboardBuffer = -1;
    currentDirection.set(MOVE_RIGHT);
//    currentDirection = MOVE_RIGHT;

    state = MOVING;
  }

 /**
  * Turn Pac-Man to the left.
  */
  private void moveLeft() {

    if (currentDirection.get() == MOVE_LEFT) {
//    if ( currentDirection == MOVE_LEFT )   return;
        return;
    }

    int nextX = x - 1;

    if (nextX <= 1) {
      return;
    }

    if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
      return;
    }

    xDirection = -1;
    yDirection = 0;

    keyboardBuffer = -1;
    currentDirection.set(MOVE_LEFT);
//    currentDirection = MOVE_LEFT;

    state = MOVING;
  }

 /**
  * Turn Pac-Man up.
  */
  private void moveUp() {

    if (currentDirection.get() == MOVE_UP) {
//    if ( currentDirection == MOVE_UP )  return;
      return;
    }

    int nextY = y - 1;

    if (nextY <= 1) {
      return;
    }

    if (MazeData.getData(x,nextY) == MazeData.BLOCK) {
      return;
    }

    xDirection = 0;
    yDirection = -1;

    keyboardBuffer = -1;
    currentDirection.set(MOVE_UP);
//    currentDirection = MOVE_UP;

    state = MOVING;
  }

 /**
  * Turn Pac-Man down.
  */
  private void moveDown() {

    if (currentDirection.get() == MOVE_DOWN) {
//    if ( currentDirection == MOVE_DOWN ) return;
        return;
    }

    int nextY = y + 1;

    if (nextY >= MazeData.GRID_SIZE) {
      return;
    }

    if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
      return;
    }

    xDirection = 0;
    yDirection = 1;

    keyboardBuffer = -1;
    currentDirection.set(MOVE_DOWN);
//    currentDirection = MOVE_DOWN;

    state = MOVING;
  }

 /**
  * Handle keyboard input.
  */
  private void handleKeyboardInput() {

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


  public void setKeyboardBuffer(int k) {
    keyboardBuffer = k;
  }

 /**
  * Update score if a dot is eaten.
  */
  private void updateScore() {
    if ( y != 14 || ( x > 0 && x < MazeData.GRID_SIZE ) ) {
      Dot dot = (Dot) MazeData.getDot(x, y);

      if ( dot != null && dot.isVisible() ) {
        score.set(score.get() + 10);
//        score += 10;
        dot.setVisible(false);
        dotEatenCount++;

        if (score.get() >= 10000) {
//        if ( score >= 10000 ) {
          maze.addLife();
        }

        if (dot.dotType == MazeData.MAGIC_DOT) {
          maze.makeGhostsHollow();
        }

        // check if the player wins and should start a new level
        if (dotEatenCount >= MazeData.dotTotal) {
          maze.startNewLevel();
        }
      }
    }
  }

  public void hide() {
    setVisible(false);
    timeline.stop();
  }

 /**
  * Handle animation of one tick.
  */
  @Override
  public void moveOneStep() {
//  public override function moveOneStep() {
    if (maze.gamePaused.get()) {

//      if ( timeline.paused == false ) {
      if (timeline.getStatus() != Animation.Status.PAUSED) {
        timeline.pause();
      }

      return;
    }

    // handle keyboard input only when Pac-Man is at a point on the grid
    if (currentImage.get() == 0) {
//    if ( currentImage == 0 ) {
      handleKeyboardInput();
    }

    if (state == MOVING) {

      if (xDirection != 0) {
        moveHorizontally();
      }

      if (yDirection != 0) {
        moveVertically();
      }

      // switch to the image of the next frame
      if (currentImage.get() < ANIMATION_STEP - 1) {
//      if ( currentImage < ANIMATION_STEP - 1  ) {
        currentImage.set(currentImage.get() + 1);
//        currentImage++;
      }
      else {
        currentImage.set(0);
//        currentImage=0;
        updateScore();
      }

    }

    maze.pacManMeetsGhosts();
  }

 /**
  * Place Pac-Man at the startup position for a new game.
  */
  public void resetStatus() {
    state = MOVING;
    currentDirection.set(MOVE_LEFT);
//    currentDirection = MOVE_LEFT;
    xDirection = -1;
    yDirection = 0;
    
    keyboardBuffer = -1;
    currentImage.set(0);
//    currentImage = 0;
    moveCounter = 0;

    x = 15;
    y = 18;

    imageX.set(MazeData.calcGridX(x));
    imageY.set(MazeData.calcGridY(y));
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridY(y);

    setVisible(true); // patweb: Added because Pac-Man is invisible at start of new life.
    start();
  }

}
