package pacman;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ghost.fx created on 2009-1-28, 14:26:09 <br>
 * Ghost.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class Ghost extends MovingObject {
//public class Ghost extends Parent, MovingObject{

  private static final int TRAPPED = 10;

  // the pacman character
  private final PacMan pacMan;

//  public var HOLLOW_IMAGE1 = Image {
//    url: "{__DIR__}images/ghosthollow2.png"
//  }
  private static final Image HOLLOW_IMAGE1 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow2.png"));

//  public var HOLLOW_IMAGE2 = Image {
//    url: "{__DIR__}images/ghosthollow3.png"
//  }
  private static final Image HOLLOW_IMAGE2 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow3.png"));

//  public var HOLLOW_IMAGE3 = Image {
//    url: "{__DIR__}images/ghosthollow1.png"
//  }
  private static final Image HOLLOW_IMAGE3 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow1.png"));

  // images for ghosts when they become hollow
//  public var HOLLOW_IMG =
//    [ HOLLOW_IMAGE1,
//      HOLLOW_IMAGE2,
//      HOLLOW_IMAGE1,
//      HOLLOW_IMAGE2 ];
  private static final Image[] HOLLOW_IMG = new Image[] {
    HOLLOW_IMAGE1,
    HOLLOW_IMAGE2,
    HOLLOW_IMAGE1,
    HOLLOW_IMAGE2
  };

  // images for ghosts when they become hollow and flashing
//  public var FLASH_HOLLOW_IMG =
//    [ HOLLOW_IMAGE1,
//      HOLLOW_IMAGE3,
//      HOLLOW_IMAGE1,
//      HOLLOW_IMAGE3 ];
  private static final Image[] FLASH_HOLLOW_IMG = new Image[] {
    HOLLOW_IMAGE1,
    HOLLOW_IMAGE3,
    HOLLOW_IMAGE1,
    HOLLOW_IMAGE3
  };

  // time for a ghost to stay hollow
  private static final int HOLLOW_MAX_TIME = 80;

  private int hollowCounter;

  // the images of animation
//  private Image defaultImage1; // unused
//  private Image defaultImage2; // unused

//  def  defaultImg  = [
//        defaultImage1,
//        defaultImage2,
//        defaultImage1,
//        defaultImage2,
//  ];
  private final Image[] defaultImg;

  // animation images
  //var images = defaultImg;

  // initial direction and position of a ghost, used in status reset
  private final int initialLocationX;
  private final int initialLocationY;
  private final int initialDirectionX;
  private final int initialDirectionY;

  // time to stay in the cage
  private final int trapTime;
  public int trapCounter; // = 0;

  // variables to determine if a ghost should chase pacman,
  // and the probability
  private static final double CHANGE_FACTOR = 0.75;
  private static final double CHASE_FACTOR = 0.5;
  private int chaseCount; // = 0;

  // the flag is set if a ghost becomes hollow
  public boolean isHollow; // = false;

  // the GUI of a ghost
//  var ghostNode : ImageView = ImageView {
//    x: bind imageX  - 13
//    y: bind imageY  - 13
//    image: bind images[currentImage]
////    cache: true
//    }
//  private ImageView ghostNode;

//  postinit {
//    initialLocationX = x;
//    initialLocationY = y;
//    initialDirectionX = xDirection;
//    initialDirectionY = yDirection;
//
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridY(y);
//
//  }

  public Ghost(Image defaultImage1,
          Image defaultImage2,
          Maze maze,
          PacMan pacMan,
          int x,
          int y,
          int xDirection,
          int yDirection,
          int trapTime) {

//    this.defaultImage1 = defaultImage1;
//    this.defaultImage2 = defaultImage2;
    this.maze = maze;
    this.pacMan = pacMan;
    this.x = x;
    this.y = y;
    this.xDirection = xDirection;
    this.yDirection = yDirection;
    this.trapTime = trapTime;

    defaultImg = new Image[] {
      defaultImage1,
      defaultImage2,
      defaultImage1,
      defaultImage2
    };
    images = defaultImg;
    chaseCount = 0;
    isHollow = false;

    trapCounter = 0;

    // postinit block
    initialLocationX = x;
    initialLocationY = y;
    initialDirectionX = xDirection;
    initialDirectionY = yDirection;
    imageX = new SimpleIntegerProperty(MazeData.calcGridX(x));
    imageY = new SimpleIntegerProperty(MazeData.calcGridY(y));
    // end postinit block

    ImageView ghostNode = new ImageView(defaultImage1);
    ghostNode.xProperty().bind(imageX.add(-13));
    ghostNode.yProperty().bind(imageY.add(-13));
    ghostNode.imageProperty().bind(imageBinding);
    ghostNode.setCache(true);

    getChildren().add(ghostNode);
  }

  // reset the status of a ghost and place it into the cage
  public void resetStatus() {
//  public function resetStatus() {
    x = initialLocationX;
    y = initialLocationY;

    xDirection = initialDirectionX;
    yDirection = initialDirectionY;

    isHollow = false;

    moveCounter = 0;
    trapCounter = 0;
    currentImage.set(0);

    imageX.set(MazeData.calcGridX(x));
    imageY.set(MazeData.calcGridY(y));

    images = defaultImg;
    state = TRAPPED;

//    timeline.keyFrames[0].time = 45ms; // Can't change time of KeyFrame
    timeline.setRate(1.0);

    setVisible(true);
    start();
  }

  public void changeToHollowGhost() {
//  public function changeToHollowGhost() {
    hollowCounter = 0;
    isHollow = true;

    // switch the animation images
    images = HOLLOW_IMG;

    // make it move slower
    timeline.stop();
//    timeline.keyFrames[0].time = 130ms; // Can't change time of KeyFrame
    timeline.setRate(0.35);
    timeline.play();
  }

  // decide whether to change the current direction of a ghost
  private void changeDirectionXtoY(boolean mustChange) {
//  public function changeDirectionXtoY(mustChange: Boolean): Void {
//    if ( not mustChange and Math.random() > changeFactor ) {
    if ( !mustChange && (Math.random() > CHANGE_FACTOR) ) {
      return; // no change of direction
    }

    // will change to a Y direction if possible
//    var goUp = MoveDecision {
//      x: this.x
//      y: this.y - 1
//    };
    MoveDecision goUp = new MoveDecision();
    goUp.x = this.x;
    goUp.y = this.y - 1;

//    var goDown = MoveDecision {
//      x: this.x
//      y: this.y + 1
//    };
    MoveDecision goDown = new MoveDecision();
    goDown.x = this.x;
    goDown.y = this.y + 1;

    // evaluate the moving choices to pick the best one
    goUp.evaluate(pacMan, isHollow);
    goDown.evaluate(pacMan, isHollow);

//    if ( goUp.score < 0 and goDown.score < 0 )
    if ( goUp.score < 0 && goDown.score < 0 ) {
      return; // no change of direction
    }

//    if ( Math.random() < chaseFactor and chaseCount == 0 )
    if ( Math.random() < CHASE_FACTOR && chaseCount == 0 ) {
//      chaseCount += (Math.random() * 10 + 3) as Integer;
      chaseCount += (int) (Math.random() * 10 + 3);
    }

//    var continueGo =  MoveDecision {
//      x: this.x + xDirection
//      y: this.y
//    };
    MoveDecision continueGo = new MoveDecision();
    continueGo.x = this.x + xDirection;
    continueGo.y = this.y;
    continueGo.evaluate(pacMan, isHollow);

//    if ( continueGo.score > 0 and continueGo.score > goUp.score
//         and continueGo.score > goDown.score and chaseCount>0) {
    if ( (continueGo.score > 0) && (continueGo.score > goUp.score)
         && (continueGo.score > goDown.score) && (chaseCount > 0) ) {
      chaseCount--;
      return;
    }

//    var decision = -1; // make it goes up first, then decide if we need to change it
    int decision = -1; // make it go up first, then decide if we need to change it
    if (goUp.score  < 0) {
      decision = 1;
    }
    else {
      if (goDown.score > 0) {
        if (chaseCount > 0) {
          if (goDown.score > goUp.score) {
            decision = 1;
            chaseCount--;
          }
        }
        else {
          // random pick
          if (Math.random() > 0.5) {
            decision = 1;
          }
        }
      }
    }

    yDirection = decision;
    xDirection = 0;
  }

  // decide whether to change the current direction of a ghost
  private void changeDirectionYtoX(boolean mustChange) {
//  public function changeDirectionYtoX(mustChange: Boolean): Void {

    if ( !mustChange && (Math.random() > CHANGE_FACTOR) ) {
      return; // no change of direction
    }

    // will change to X directions if possible
//    var goLeft = MoveDecision {
//      x: this.x - 1
//      y: this.y
//    };
    MoveDecision goLeft = new MoveDecision();
    goLeft.x = this.x - 1;
    goLeft.y = this.y;

//    var goRight = MoveDecision {
//      x: this.x + 1
//      y: this.y
//    };
    MoveDecision goRight = new MoveDecision();
    goRight.x = this.x + 1;
    goRight.y = this.y;

    // evaluate the moving choices to pick the best one
    goLeft.evaluate(pacMan, isHollow);
    goRight.evaluate(pacMan, isHollow);

    if ( (goLeft.score < 0) && (goRight.score < 0) ) {
      return; // no change of direction
    }

    if ( (Math.random() < CHASE_FACTOR) && (chaseCount == 0) ) {
//      chaseCount += (Math.random() * 10 + 3) as Integer;
      chaseCount += (int) (Math.random() * 10 + 3);
    }

//    var continueGo = MoveDecision {
//      x: this.x
//      y: this.y + yDirection
//    };
    MoveDecision continueGo = new MoveDecision();
    continueGo.x = this.x;
    continueGo.y = this.y + yDirection;
    continueGo.evaluate(pacMan, isHollow);

    if ( (continueGo.score > 0) && (continueGo.score > goLeft.score)
         && (continueGo.score > goRight.score) && (chaseCount > 0) ) {
      chaseCount--;
      return;
    }

    // make it go up first, then decide if we need to change it to down
    int decision = -1;
    if (goLeft.score  < 0) {
      decision = 1;
    }
    else {
      if (goRight.score > 0) {
        if (chaseCount > 0) {
          if (goRight.score > goLeft.score) {
            decision = 1;
            chaseCount--;
          }
        }
        else { // random pick
          if (Math.random() > 0.5) {
            decision = 1;
          }
        }
      }
    }

    xDirection = decision;
    yDirection = 0;
  }

  // move the ghost horizontally
  private void moveHorizontally() {

    moveCounter++;

    if (moveCounter > ANIMATION_STEP - 1) {
      moveCounter = 0;
      x += xDirection;
//      imageX= MazeData.calcGridX(x);
      imageX.set(MazeData.calcGridX(x));

//      var nextX = xDirection + x;
      int nextX = xDirection + x;

      if ( y == 14 && ( nextX <= 1 || nextX >= 28) ) {
        if ( nextX < - 1 && xDirection < 0 ) {
          x = MazeData.GRID_SIZE;
//          imageX= MazeData.calcGridX(x);
          imageX.set(MazeData.calcGridX(x));
        }
        else if (nextX > 30 && xDirection > 0) {
          x = 0;
//            imageX= MazeData.calcGridX(x);
          imageX.set(MazeData.calcGridX(x));
        }
      }
      else if (nextX < 0 || nextX > MazeData.GRID_SIZE) {
        changeDirectionXtoY(true);
      }
      else if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
        changeDirectionXtoY(true);
      }
      else {
        changeDirectionXtoY(false);
      }

    }
    else {
      imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
    }
  }

  // move the ghost vertically
  private void moveVertically() {

    moveCounter++;

    if (moveCounter > ANIMATION_STEP - 1) {
      moveCounter = 0;
      y += yDirection;
//      imageY = MazeData.calcGridX(y);
      imageY.set(MazeData.calcGridX(y));

      int nextY = yDirection + y;
      if (nextY < 0 || nextY > MazeData.GRID_SIZE) {
        changeDirectionYtoX(true);
      }
      else {
        if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
          changeDirectionYtoX(true);
        }
        else {
          changeDirectionYtoX(false);
        }
      }
    }
    else {
//      imageY += yDirection * MOVE_SPEED;
      imageY.set(imageY.get() + (yDirection * MOVE_SPEED));
    }
  }

  // move the ghost horizontally in the cage
  private void moveHorizontallyInCage() {

    moveCounter++;

    if (moveCounter > ANIMATION_STEP - 1) {

      moveCounter = 0;
      x += xDirection;
//      imageX = MazeData.calcGridX(x);
      imageX.set(MazeData.calcGridX(x));

      int nextX = xDirection + x;

      if (nextX < 12) {
        xDirection = 0;
        yDirection = 1;
      }
      else if (nextX > 17) {
        xDirection = 0;
        yDirection = -1;
      }
    }
    else {
//      imageX += xDirection * MOVE_SPEED;
      imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
    }
  }

  // move the ghost vertically in the cage
  private void moveVerticallyInCage() {

    moveCounter++;

    if (moveCounter > ANIMATION_STEP - 1) {
      moveCounter = 0;
      y += yDirection;
//      imageY= MazeData.calcGridX(y) + 8;
      imageY.set(MazeData.calcGridX(y) + 8);

      int nextY = yDirection + y;

      if (nextY < 13) {
        yDirection = 0;
        xDirection = -1;
      }
      else if (nextY > 15) {
        yDirection = 0;
        xDirection = 1;
      }
    }
    else {
//      imageY += yDirection * MOVE_SPEED;
      imageY.set(imageY.get() + (yDirection * MOVE_SPEED));
    }
  }

  public void hide() {
    setVisible(false);
    timeline.stop();
  }

  // move one tick
  @Override
  public void moveOneStep() {
    if (maze.gamePaused.get()) {
//      if ( timeline.paused ==  false ) {
      if (!isPaused()) {
        timeline.pause();
      }
      return;
    }

    if ( state == MOVING || state == TRAPPED ) {
      if (xDirection != 0) {
        if (state == MOVING) {
          moveHorizontally();
        }
        else {
          moveHorizontallyInCage();
        }
      }
      else {
        if (yDirection != 0) {
          if (state == MOVING) {
            moveVertically();
          }
          else {
            moveVerticallyInCage();
          }
        }
      }

//      if ( currentImage < ANIMATION_STEP - 1 )
      if ( currentImage.get() < (ANIMATION_STEP - 1) ) {
//        currentImage++;
        currentImage.set(currentImage.get() + 1);
      }
      else {
//        currentImage=0;
        currentImage.set(0);
        if (state == TRAPPED) {
          trapCounter++;

          if (trapCounter > trapTime && x == 14 && y == 13) {
            // go out of the cage
            y = 12;

            xDirection = 0;
            yDirection = -1;
            state = MOVING;
          }
        }
      }
    }

    // check to see if need to switch back to a normal status
    if (isHollow) {

      hollowCounter++;

      if (hollowCounter == HOLLOW_MAX_TIME - 30) {
        images = FLASH_HOLLOW_IMG;
      }
      else if (hollowCounter > HOLLOW_MAX_TIME) {
        isHollow = false;
        images = defaultImg;

        timeline.stop();
//        timeline.keyFrames[0].time = 45ms;
        timeline.setRate(1.0);
        timeline.play();
      }
    }

  }

}
