/*
 * Ghost.fx
 *
 * Created on 2009-1-28, 14:26:09
 */

package pacman;

//import java.lang.Math;
//import javafx.scene.Parent;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import pacman.MoveDecision;

/**
 * @author Henry Zhang
 * http://www.javafxgame.com
 */

//public class Ghost extends Parent, MovingObject{
public class Ghost extends MovingObject{

  public final static int TRAPPED = 10;

  // the pacman character
  public PacMan pacMan;

//  public var hollowImage1 = Image {
//    url: "{__DIR__}images/ghosthollow2.png"
//    }
  public final static Image hollowImage1 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow2.png"));
  
//  public var hollowImage2 = Image {
//    url: "{__DIR__}images/ghosthollow3.png"
//    }
  public final static Image hollowImage2 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow3.png"));
  
//  public var hollowImage3 = Image {
//    url: "{__DIR__}images/ghosthollow1.png"
//    }
  public final static Image hollowImage3 = new Image(Ghost.class.getResourceAsStream("images/ghosthollow1.png"));
  
  // images for ghosts when they become hollow
//  public var hollowImg =
//    [ hollowImage1,
//      hollowImage2,
//      hollowImage1,
//      hollowImage2 ];
  public final static Image[] hollowImg = new Image[]{hollowImage1,
    hollowImage2,
    hollowImage1,
    hollowImage2};
    
  // images for ghosts when they become hollow and flashing
//  public var flashHollowImg =
//    [ hollowImage1,
//      hollowImage3,
//      hollowImage1,
//      hollowImage3 ];
  public final static Image[] flashHollowImg = new Image[] {hollowImage1,
    hollowImage3,
    hollowImage1,
    hollowImage3};

  // time for a ghost to stay hollow
  public final static int hollowMaxTime = 80;
  public int hollowCounter;

  // the images of animation
  public Image defaultImage1;
  public Image defaultImage2;

//  def  defaultImg  = [
//        defaultImage1,
//        defaultImage2,
//        defaultImage1,
//        defaultImage2,
//  ];
  public Image[] defaultImg; // = new Image[] {defaultImage1,
//    defaultImage2,
//    defaultImage1,
//    defaultImage2};

  // animation images
//  public Image[] images; // = defaultImg;

  // initial direction and position of a ghost, used in status reset
  public int initialLocationX;
  public int initialLocationY;
  public int initialDirectionX;
  public int initialDirectionY;

  // time to stay in the cage
  public int trapTime;
  public int trapCounter; // = 0;

  // variables to determine if a ghost should chase pacman,
  // and the probability
  public double changeFactor; // = 0.75;
  public double chaseFactor; // = 0.5;
  public int chaseCount; // = 0;

  // the flag is set if a ghost becomes hollow
  public boolean isHollow; // = false;

  // the GUI of a ghost
//  var ghostNode : ImageView = ImageView {
//    x: bind imageX  - 13
//    y: bind imageY  - 13
//    image: bind images[currentImage]
////    cache: true
//    }
  public ImageView ghostNode;

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
    
    this.defaultImage1 = defaultImage1;
    this.defaultImage2 = defaultImage2;
    this.maze = maze;
    this.pacMan = pacMan;
    this.x = x;
    this.y = y;
//    this.x = new SimpleIntegerProperty(x);
//    this.y = new SimpleIntegerProperty(y);
    this.xDirection = xDirection;
    this.yDirection = yDirection;
    this.trapTime = trapTime;
    
    defaultImg = new Image[]{
      defaultImage1,
      defaultImage2,
      defaultImage1,
      defaultImage2};
    images = defaultImg;
    changeFactor = 0.75;
    chaseFactor = 0.5;
    chaseCount = 0;
    isHollow = false;
    
    trapCounter = 0;
    
    // postinit block
    initialLocationX = x;
    initialLocationY = y;
    initialDirectionX = xDirection;
    initialDirectionY = yDirection;
//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridY(y);
    imageX = new SimpleIntegerProperty(MazeData.calcGridX(x));
    imageY = new SimpleIntegerProperty(MazeData.calcGridY(y));
    // end postinit block
//    Group group = new Group();
//    changeImage();
    ghostNode = new ImageView(defaultImage1);

    
    ghostNode.xProperty().bind(imageX.add(-13));
    ghostNode.yProperty().bind(imageY.add(-13));
    ghostNode.imageProperty().bind(imageBinding);

    ghostNode.setCache(true);
//    ghostNode.imageProperty().bind(images[currentImage.get()]);
//    group.getChildren().add(ghostNode);
//    getChildren().add(group);
    
//    Circle circ = new Circle(imageX, imageY, 13, Color.CYAN); // patweb
//    Circle circ = new Circle(imageX.get(), imageY.get(), 13, Color.CYAN); // patweb
//    circ.centerXProperty().bind(imageX);
//    circ.centerYProperty().bind(imageY);
//
//    getChildren().add(circ); // patweb
    getChildren().add(ghostNode);
  }
  
//  public void changeImage() {
//    if (ghostNode == null) {
//      ghostNode = new ImageView(defaultImage1);
//    }
////    System.out.println("changing ghost image: setting (x,y) to ("+imageX+", "+imageY+")");
////    ghostNode.setX(imageX  - 13);
////    ghostNode.setX(imageX.get()  - 13);
////    ghostNode.xProperty().bind(imageX - 13);
////    ghostNode.setY(imageY  - 13);
////    ghostNode.setY(imageY.get()  - 13);
////    ghostNode.setImage(images[currentImage]); // TODO: bind
//    ghostNode.setImage(images[currentImage.get()]); // TODO: bind
////    ghostNode.setVisible(true);// patweb
////    System.out.println("Ghost "+ghostNode.getImage().impl_getUrl()+" is at (" + ghostNode.xProperty().getValue() + ", " + ghostNode.yProperty().getValue());
//  }
  
  // reset the status of a ghost and place it into the cage
//  public function resetStatus() {
  public void resetStatus() {
    x = initialLocationX;
    y = initialLocationY;
//    x.set(initialLocationX);
//    y.set(initialLocationY);

    xDirection = initialDirectionX;
    yDirection = initialDirectionY;

    isHollow = false;

    moveCounter = 0;
    trapCounter = 0;
//    currentImage = 0;
    currentImage.set(0);

//    imageX = MazeData.calcGridX(x);
//    imageY = MazeData.calcGridY(y);
    imageX.set(MazeData.calcGridX(x));
    imageY.set(MazeData.calcGridY(y));
//    imageX = MazeData.calcGridX(x.get());
//    imageY = MazeData.calcGridY(y.get());

    images = defaultImg;
    state = TRAPPED;
        
//    timeline.keyFrames[0].time = 45ms;
//    timeline.getKeyFrames().get(0).setTime(45ms); // TODO: can't change time of KeyFrame
    timeline.setRate(1.0);

    setVisible(true);
    start();
  }

//  public function changeToHollowGhost() {
  public void changeToHollowGhost() {
    hollowCounter = 0;
    isHollow = true;

    // switch the animation images
    images = hollowImg;

    // make it moves slower
    timeline.stop();
//    timeline.keyFrames[0].time = 130ms; // TODO: can't change time of KeyFrame
//    timeline.rateProperty().set(0.35);
    timeline.setRate(0.35);
    timeline.play();
  }

  // decide whether to change the current direction of a ghost
//  public function changeDirectionXtoY(mustChange: Boolean): Void {
  public void changeDirectionXtoY(boolean mustChange) {
//    if ( not mustChange and Math.random() > changeFactor ) {
    if ( !mustChange && (Math.random() > changeFactor) ) {
      return;  // no change of direction
    }

    // will change to a Y direction if possible
//    var goUp = MoveDecision {
//      x: this.x
//      y: this.y - 1 };
    MoveDecision goUp = new MoveDecision();
    goUp.x = this.x;
    goUp.y = this.y - 1;
//    goUp.x = this.x.get();
//    goUp.y = this.y.get() - 1;
//    var goDown = MoveDecision {
//      x: this.x
//      y: this.y + 1
//    };
    MoveDecision goDown = new MoveDecision();
    goDown.x = this.x;
    goDown.y = this.y + 1;
//    goDown.x = this.x.get();
//    goDown.y = this.y.get() + 1;

    // evaluate the moving choices to pick the best one
    goUp.evaluate(pacMan, isHollow);
    goDown.evaluate(pacMan, isHollow);

//    if ( goUp.score < 0 and goDown.score < 0 )
    if ( goUp.score < 0 && goDown.score < 0 )
      return;  // no change of direction

//    if ( Math.random() < chaseFactor and chaseCount == 0 )
    if ( Math.random() < chaseFactor && chaseCount == 0 ) {
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
//    continueGo.x = this.x.get() + xDirection;
//    continueGo.y = this.y.get();

    continueGo.evaluate(pacMan, isHollow);

//    if ( continueGo.score > 0 and continueGo.score > goUp.score
//         and continueGo.score > goDown.score and chaseCount>0) {
    if ( (continueGo.score > 0) && (continueGo.score > goUp.score)
         && (continueGo.score > goDown.score) && (chaseCount>0) ) {
      chaseCount--;
      return;
    }

//    var decision = -1; // make it goes up first, then decide if we need to change it
    int decision = -1; // make it goes up first, then decide if we need to change it
    if ( goUp.score  < 0 )
      decision = 1;
    else
      if ( goDown.score > 0 ) {
        if ( chaseCount > 0 ) {
          if ( goDown.score > goUp.score) {
            decision = 1;
            chaseCount-- ;
          }
        }
        else {
          // random pick
          if ( Math.random() > 0.5 )
            decision = 1;
        }
      }

    yDirection = decision;
    xDirection = 0;
  }

  // decide whether to change the current direction of a ghost
//  public function changeDirectionYtoX(mustChange: Boolean): Void {
  public void changeDirectionYtoX(boolean mustChange) {

    if ( !mustChange && (Math.random() > changeFactor) )
      return;  // no change of direction

    // will change to X directions if possible
//    var goLeft = MoveDecision {
//      x: this.x - 1
//      y: this.y
//    };
    MoveDecision goLeft = new MoveDecision();
    goLeft.x = this.x - 1;
    goLeft.y = this.y;
//    goLeft.x = this.x.get() - 1;
//    goLeft.y = this.y.get();

//    var goRight = MoveDecision {
//      x: this.x + 1
//      y: this.y
//    };
    MoveDecision goRight = new MoveDecision();
    goRight.x = this.x + 1;
    goRight.y = this.y;
//    goRight.x = this.x.get() + 1;
//    goRight.y = this.y.get();

    // evaluate the moving choices to pick the best one
    goLeft.evaluate(pacMan, isHollow);
    goRight.evaluate(pacMan, isHollow);

    if ( (goLeft.score < 0) && (goRight.score < 0) ) {
      return;  // no change of direction
    }

    if ( (Math.random() < chaseFactor) && (chaseCount == 0) ) {
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
//    continueGo.x = this.x.get();
//    continueGo.y = this.y.get() + yDirection;

    continueGo.evaluate(pacMan, isHollow);

    if ( (continueGo.score > 0) && (continueGo.score > goLeft.score)
         && (continueGo.score > goRight.score) && (chaseCount>0) ) {
      chaseCount --;
      return;
    }

    // make it goes up first, then decide if we need to change it to down
    int decision = -1;
    if ( goLeft.score  < 0 )
      decision = 1;
    else
      if ( goRight.score > 0 ) {
        if ( chaseCount > 0 ) {
          if ( goRight.score > goLeft.score) {
            decision = 1;
            chaseCount--;
          }
        }
        else { // random pick
          if ( Math.random() > 0.5 )
            decision = 1;
        }
      }

    xDirection=decision;
    yDirection = 0;
  }

  // move the ghost horizontally
  public void moveHorizontally() {

    moveCounter++;

    if ( moveCounter > ANIMATION_STEP - 1) {
      moveCounter=0;
      x += xDirection;
//      x.set(x.get() + xDirection);
//      imageX= MazeData.calcGridX(x);
      imageX.set(MazeData.calcGridX(x));
//      imageX= MazeData.calcGridX(x.get());

//      var nextX = xDirection + x;
      int nextX = xDirection + x;
//      int nextX = xDirection + x.get();

      if ( y == 14 && ( nextX <= 1 || nextX >= 28) ) {
//      if ( y.get() == 14 && ( nextX <= 1 || nextX >= 28) ) {
        if ( nextX < - 1 && xDirection < 0 ) {
          x=MazeData.GRID_SIZE;
//          x.set(MazeData.GRID_SIZE);
//          imageX= MazeData.calcGridX(x);
          imageX.set(MazeData.calcGridX(x));
//          imageX= MazeData.calcGridX(x.get());
        }
        else
          if ( nextX > 30 && xDirection > 0) {
            x=0;
//            x.set(0);
//            imageX= MazeData.calcGridX(x);
            imageX.set(MazeData.calcGridX(x));
//            imageX= MazeData.calcGridX(x.get());
          }
      }
      else
        if (nextX < 0 || nextX > MazeData.GRID_SIZE) {
          changeDirectionXtoY(true);
        }
      else
        if ( MazeData.getData(nextX, y) == MazeData.BLOCK ) {
//        if ( MazeData.getData(nextX, y.get()) == MazeData.BLOCK ) {
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
  public void moveVertically() {
      
    moveCounter++;

    if ( moveCounter > ANIMATION_STEP - 1) {
      moveCounter = 0;
      y += yDirection;
//      y.set(y.get() + yDirection);
//      imageY = MazeData.calcGridX(y);
      imageY.set(MazeData.calcGridX(y));
//      imageY = MazeData.calcGridX(y.get());

      int nextY= yDirection + y;
//      int nextY= yDirection + y.get();
      if ( nextY < 0 || nextY > MazeData.GRID_SIZE) {
        changeDirectionYtoX(true);
      }
      else {
        if ( MazeData.getData(x, nextY) == MazeData.BLOCK ) {
//        if ( MazeData.getData(x.get(), nextY) == MazeData.BLOCK ) {
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
  public void moveHorizontallyInCage() {
    
    moveCounter++;

    if ( moveCounter > ANIMATION_STEP - 1) {

      moveCounter=0;
      x += xDirection;
//      x.set(x.get() + xDirection);
//      imageX = MazeData.calcGridX(x);
      imageX.set(MazeData.calcGridX(x));
//      imageX = MazeData.calcGridX(x.get());

      int nextX = xDirection + x;
//      int nextX = xDirection + x.get();

      if ( nextX < 12 ) {
        xDirection = 0;
        yDirection = 1;
      }
      else
        if ( nextX > 17) {
          xDirection = 0;
          yDirection = -1;
        }
    }
    else {
//      imageX += xDirection * MOVE_SPEED;
      imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
    }
  }

  // move the ghost vertically in a cage
  public void moveVerticallyInCage() {

    moveCounter++;

    if ( moveCounter > ANIMATION_STEP - 1) {
      moveCounter=0;
      y += yDirection;
//      y.set(x.get() + yDirection);
//      imageY= MazeData.calcGridX(y) + 8;
      imageY.set(MazeData.calcGridX(y) + 8);
//      imageY= MazeData.calcGridX(y.get()) + 8;

      int nextY = yDirection + y;
//      int nextY = yDirection + y.get();

      if ( nextY < 13 ) {
        yDirection = 0;
        xDirection = -1;
      }
      else
        if ( nextY > 15) {
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
    if ( maze.gamePaused.get() ) {
//      if ( timeline.paused ==  false ) {
      if ( !isPaused() ) {
        timeline.pause();
      }
      return;
    }

    if ( state == MOVING || state == TRAPPED ) {
      if ( xDirection != 0 ) {
        if ( state == MOVING ) {
          moveHorizontally();
        }
        else {
          moveHorizontallyInCage();
        }
      }
      else {
        if ( yDirection != 0 ) {
          if ( state == MOVING ) {
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
        if ( state == TRAPPED ) { 
          trapCounter++;

          if ( trapCounter > trapTime && x == 14 && y == 13) {
//          if ( (trapCounter > trapTime) && (x.get() == 14) && (y.get() == 13) ) {
            // go out of the cage
            y = 12;
//            y.set(12);

            xDirection = 0;
            yDirection = -1;
            state = MOVING;
          }
        }
      }
    }

    // check to see if need to switch back to a normal status
    if ( isHollow ) {
    
      hollowCounter++;

      if ( hollowCounter == hollowMaxTime - 30 )
        images = flashHollowImg;
      else
        if ( hollowCounter > hollowMaxTime ) {
          isHollow = false;
          images = defaultImg;
                
          timeline.stop();
//          timeline.keyFrames[0].time = 45ms;
//          timeline.getKeyFrames().get(0).getTime() = 45ms;
          timeline.setRate(1.0);
          timeline.play();
        }
    }
//    System.out.println("changing ghost image");
//    changeImage(); // patweb
  }


}
