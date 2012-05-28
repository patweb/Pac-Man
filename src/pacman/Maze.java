package pacman;

//import javafx.scene.CustomNode;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Maze.fx created on 2008-12-20, 20:22:15 <br>
 * Maze.java created October 2011
 *
 * @see <a href="http://www.javafxgame.com">http://www.javafxgame.com</a>
 * @author Henry Zhang
 * @author Patrick Webster
 */

public class Maze extends Parent {
//public class Maze extends CustomNode {

  public static final boolean DEBUG = false;

//  override var onMouseClicked = function ( e:MouseEvent) {
//    requestFocus();
//  }

  // counter for ghosts eaten
  private int ghostEatenCount;
//  var ghostEatenCount : Integer;

  public BooleanProperty gamePaused;
//  public var gamePaused: Boolean = false;

  // text to be displayed for score of eating a ghost
  private static final ScoreText[] SCORE_TEXT = {
//  var scoreText = [
    new ScoreText("200", false) //{
    //      content: "200"
    //      visible: false;
    //    }
    ,
    new ScoreText("400", false) //{
    //    ScoreText {
    //        content: "400"
    //        visible: false;
    //    }
    ,
    new ScoreText("800", false) //{
    //    ScoreText {
    //        content: "800"
    //        visible: false;
    //    }
    ,
    new ScoreText("1600", false) //{
  //    ScoreText {
  //        content: "1600"
  //        visible: false;
  //    }
  };

  // Pac-Man Character
  public PacMan pacMan;
//  public var pacMan : PacMan = PacMan{ maze:this x:15 y:18 } ;

  public final Ghost[] ghosts;
//  public var ghosts = [ghostBlinky, ghostPinky, ghostInky, ghostClyde];

  private final DyingPacMan dyingPacMan;
//  public var dyingPacMan =
//    DyingPacMan {
//      maze: this
//      centerX: 0
//      centerY: 0
//      radiusX: 13
//      radiusY: 13
//      startAngle: 90
//      length: 360
//      type: ArcType.ROUND
//      fill: Color.YELLOW
//      visible: false
//   } ;

  // the Pac-Man image
  private static final Image PACMAN_IMAGE = new Image(Maze.class.getResourceAsStream("images/left1.png"));
//  var PACMAN_IMAGE = Image {
//    url: "{__DIR__}images/left1.png"
//  }

  // level of the game
  private final SimpleIntegerProperty level;
//  public var level : Integer = 1;

  // flag to add a life to the player the first time score exceeds 10,000
  private boolean addLifeFlag;
//  var addLifeFlag: Boolean = true;

  // current lives of the player
  private final SimpleIntegerProperty livesCount;
//  var livesCount = 2;

  // message to start a game
  public BooleanProperty waitForStart;
//  public var waitForStart: Boolean = true;

  private final Group messageBox;
//  var messageBox = Group {
//    content: [
//      Rectangle {
//        x: MazeData.calcGridX(5)
//        width: MazeData.GRID_GAP * 19
//        y: MazeData.calcGridY(21)
//        height: MazeData.GRID_GAP *5
//        stroke: Color.RED
//        strokeWidth: 5
//
//        fill: Color.CYAN
//        opacity: 0.75
//        arcWidth: 25
//        arcHeight: 25
//      },
//      Text {
//        font: Font { size: 18 }
//        x: MazeData.calcGridX(6)
//        y: MazeData.calcGridY(24)
//
//        content: bind if ( gamePaused ) "PRESS 'P' BUTTON TO RESUME"
//                      else "   PRESS ANY KEY TO START!"
//
//        fill: Color.RED
//      }
//    ]
//  };

 // whether the last finished game is won by the player
 private final BooleanProperty lastGameResult;
// var lastGameResult: Boolean = false;

 // text of game winning
 private final Text gameResultText;
// var gameResultText =
//   Text {
////     cache: true
//     font: Font { size: 20
//                 }
//     x: MazeData.calcGridX(11)
//     y: MazeData.calcGridY(11)+8
//
//     content: bind if ( lastGameResult )
//                     " YOU WIN "
//                   else
//                     "GAME OVER";
//     fill: Color.RED
//     visible: false;
//   } ;

 private int flashingCount;
// var flashingCount: Integer = 0;

 private final Timeline flashingTimeline;
// var flashingTimeline: Timeline =
//   Timeline {
//     repeatCount: 5
//     keyFrames : [
//       KeyFrame {
//         time : 500ms
//         action: function() {
//           gameResultText.visible = not gameResultText.visible;
//           if ( ++flashingCount == 5) {
//             messageBox.visible = true;
//             waitForStart = true;
//           }
//         }
//       }
//     ]
//   };

  private final Group group;
//  var group : Group =
//  Group {
//    content: [
//      Rectangle {
//        x:0
//        y:0
//        width: MazeData.calcGridX(MazeData.GRID_SIZE + 2)
//        height: MazeData.calcGridY(MazeData.GRID_SIZE + 3)
//        fill: Color.BLACK
//        cache: true
//      },
//
//      WallRectangle{ x1:0 y1:0 x2:MazeData.GRID_SIZE y2:MazeData.GRID_SIZE },
//
//      WallRectangle { x1:14 y1:-0.5 x2:15 y2:4 },
//      WallBlackRectangle { x1:13.8 y1:-1 x2:15.3 y2:0 },
//
//      WallRectangle { x1:2 y1:2 x2:5 y2:4 },
//      WallRectangle { x1:7 y1:2 x2:12 y2:4 },
//      WallRectangle { x1:17 y1:2 x2:22 y2:4 },
//      WallRectangle { x1:24 y1:2 x2:27 y2:4 },
//      WallRectangle { x1:2 y1:6 x2:5 y2:7 },
//
//      WallRectangle { x1:14 y1:6.2 x2:15 y2:10 },
//      WallRectangle { x1:10 y1:6 x2:19 y2:7 },
//      WallBlackLine { x1:14 y1:7 x2:15 y2:7 },
//
//      WallRectangle { x1:7.5 y1:9 x2:12 y2:10 },
//      WallRectangle { x1:7 y1:6 x2:8 y2:13 },
//      WallBlackLine { x1:8 y1:9 x2:8 y2:10 },
//
//      WallRectangle { x1:17 y1:9 x2:21.5 y2:10 },
//      WallRectangle { x1:21 y1:6 x2:22 y2:13 },
//      WallBlackLine { x1:21 y1:9 x2:21 y2:10 },
//
//      WallRectangle { x1:24 y1:6 x2:27 y2:7 },
//
//      WallRectangle { x1:-1 y1:9 x2:5 y2:13 },
//      WallRectangle { x1:24 y1:9 x2:MazeData.GRID_SIZE + 1 y2:13 },
//      WallBlackLine { x1:0 y1:13 x2:0 y2:15  },
//      WallBlackLine { x1:MazeData.GRID_SIZE y1:13 x2:MazeData.GRID_SIZE y2:15},
//
//      //cage and the gate
//      WallRectangle { x1:10 y1:12 x2:19 y2:17 },
//      WallRectangle { x1:10.5 y1:12.5 x2:18.5 y2:16.5 },
//      Rectangle {
//        x: MazeData.calcGridX(13)
//        width: MazeData.GRID_GAP * 3
//        y: MazeData.calcGridY(12)
//        height: MazeData.GRID_GAP / 2
//        stroke: Color.GREY
//        fill: Color.GREY
//        cache: true
//      },
//
//      WallRectangle { x1:7.5 y1:19 x2:12 y2:20 },
//      WallRectangle { x1:7 y1:15 x2:8 y2:23 },
//      WallBlackLine { x1:8 y1:19 x2:8 y2:20 },
//
//      WallRectangle { x1:17 y1:19 x2:21.5 y2:20 },
//      WallRectangle { x1:21 y1:15 x2:22 y2:23 },
//      WallBlackLine { x1:21 y1:19 x2:21 y2:20 },
//
//      WallRectangle { x1:14 y1:19 x2:15 y2:27 },
//      WallRectangle { x1:10 y1:22 x2:19 y2:23 },
//      WallBlackLine { x1:14 y1:22 x2:15 y2:22 },
//      WallBlackLine { x1:14 y1:23 x2:15 y2:23 },
//
//      WallRectangle { x1:2 y1:25 x2:5 y2:27 },
//      WallRectangle { x1:17 y1:25 x2:22 y2:27 },
//
//      WallRectangle { x1:7 y1:25 x2:12 y2:27 },
//      WallRectangle { x1:24 y1:25 x2:27 y2:27 },
//
//      WallRectangle { x1:-1 y1:15 x2:5 y2:17 },
//      WallRectangle { x1:4 y1:19 x2:5 y2:23 },
//      WallRectangle { x1:2 y1:19 x2:4.5 y2:20 },
//      WallBlackRectangle { x1:4 y1:19.05 x2:5 y2:20.2 },
//      WallRectangle { x1:-1 y1:22 x2:2 y2:23 },
//
//      WallRectangle { x1:24 y1:15 x2:MazeData.GRID_SIZE + 1 y2:17 },
//      WallRectangle { x1:24 y1:19 x2:25 y2:23 },
//      WallRectangle { x1:24.5 y1:19 x2:27 y2:20 },
//      WallBlackRectangle { x1:24 y1:19.05 x2:25 y2:20.2 },
//      WallRectangle { x1:27 y1:22 x2:MazeData.GRID_SIZE + 1 y2:23 },
//
//      WallBlackRectangle { x1:-2 y1:8 x2:0 y2:MazeData.GRID_SIZE },
//      WallBlackRectangle {
//         x1:MazeData.GRID_SIZE
//         y1:8
//         x2:MazeData.GRID_SIZE + 2
//         y2:MazeData.GRID_SIZE
//      },
//
//      Rectangle {
//        x: MazeData.calcGridX(-0.5)
//        y: MazeData.calcGridY(-0.5)
//        width: (MazeData.GRID_SIZE + 1) * MazeData.GRID_GAP
//        height: (MazeData.GRID_SIZE + 1) * MazeData.GRID_GAP
//        strokeWidth: MazeData.GRID_STROKE
//        stroke: Color.BLUE
//        fill: null
//        arcWidth: 12
//        arcHeight: 12
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(-0.5)
//        endX: MazeData.calcGridX(-0.5)
//        startY: MazeData.calcGridY(13)
//        endY: MazeData.calcGridY(15)
//        stroke: Color.BLACK
//        strokeWidth: MazeData.GRID_STROKE + 1
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(MazeData.GRID_SIZE + 0.5)
//        endX: MazeData.calcGridX(MazeData.GRID_SIZE + 0.5)
//        startY: MazeData.calcGridY(13)
//        endY: MazeData.calcGridY(15)
//        stroke: Color.BLACK
//        strokeWidth: MazeData.GRID_STROKE + 1
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(-0.5)
//        endX: MazeData.calcGridX(0)
//        startY: MazeData.calcGridY(13)
//        endY: MazeData.calcGridY(13)
//        stroke: Color.BLUE
//        strokeWidth: MazeData.GRID_STROKE
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(-0.5)
//        endX: MazeData.calcGridX(0)
//        startY: MazeData.calcGridY(15)
//        endY: MazeData.calcGridY(15)
//        stroke: Color.BLUE
//        strokeWidth: MazeData.GRID_STROKE
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(MazeData.GRID_SIZE + 0.5)
//        endX: MazeData.calcGridX(MazeData.GRID_SIZE)
//        startY: MazeData.calcGridY(13)
//        endY: MazeData.calcGridY(13)
//        stroke: Color.BLUE
//        strokeWidth: MazeData.GRID_STROKE
//        cache: true
//      },
//      Line {
//        startX: MazeData.calcGridX(MazeData.GRID_SIZE + 0.5)
//        endX: MazeData.calcGridX(MazeData.GRID_SIZE)
//        startY: MazeData.calcGridY(15)
//        endY: MazeData.calcGridY(15)
//        stroke: Color.BLUE
//        strokeWidth: MazeData.GRID_STROKE
//        cache: true
//      },
//      Text {
//        font: Font {
//              size: 20
//              }
//        x: MazeData.calcGridX(0),
//        y: MazeData.calcGridY(MazeData.GRID_SIZE + 2)
//        content: bind "SCORES: {pacMan.score} "
//        fill: Color.YELLOW
//        cache: true
//      },
//      scoreText,
//      dyingPacMan,
//      livesImage,
//      gameResultText,
//      Text {
//        font: Font { size: 20
//                    }
//        x: MazeData.calcGridX(22)
//        y: MazeData.calcGridY(MazeData.GRID_SIZE + 2)
//        content: bind "LEVEL: {level}"
//        fill: Color.YELLOW
//        cache: true
//      },
//
//    ]
//  }; // end Group

  // put dots into the maze
//  postinit {
//    putDotHorizontally(2,13,1);
//    putDotHorizontally(16,27,1);
//    putDotHorizontally(2,27,5);
//    putDotHorizontally(2,27,28);
//
//    putDotHorizontally(2,13,24);
//    putDotHorizontally(16,27,24);
//
//    putDotHorizontally(2,5,8);
//    putDotHorizontally(9,13,8);
//    putDotHorizontally(16,20,8);
//    putDotHorizontally(24,27,8);
//
//    putDotHorizontally(2,5,18);
//    putDotHorizontally(9,13,21);
//    putDotHorizontally(16,20,21);
//    putDotHorizontally(24,27,18);
//
//    putDotHorizontally(2,3,21);
//    putDotHorizontally(26,27,21);
//
//    putDotVertically(1,1,8);
//    putDotVertically(1,18,21);
//    putDotVertically(1,24,28);
//
//    putDotVertically(28,1,8);
//    putDotVertically(28,18,21);
//    putDotVertically(28,24,28);
//
//    putDotVertically(6,2,27);
//    putDotVertically(23,2,27);
//
//    putDotVertically(3,22,23);
//    putDotVertically(9,22,23);
//    putDotVertically(20,22,23);
//    putDotVertically(26,22,23);
//
//    putDotVertically(13,25,27);
//    putDotVertically(16,25,27);
//
//    putDotVertically(9,6,7);
//    putDotVertically(20,6,7);
//
//    putDotVertically(13,2,4);
//    putDotVertically(16,2,4);
//
//    insert pacMan into group.content;
//
//    insert ghosts into group.content;
//
//    insert WallBlackRectangle{ x1:-3, y1:13, x2:0, y2:15} into group.content;
//    insert WallBlackRectangle{ x1:29, y1:13, x2:31, y2:15} into group.content;
//
//    insert messageBox into group.content;
//  }


  public Maze() {

    setFocused(true);

    gamePaused = new SimpleBooleanProperty(false);

    pacMan = new PacMan(this, 15, 24);

    final Ghost ghostBlinky = new Ghost(
            new Image(getClass().getResourceAsStream("images/ghostred1.png")),
            new Image(getClass().getResourceAsStream("images/ghostred2.png")),
            this,
            pacMan,
            15, // x
            14, // y
            0,  // x Direction
            -1, // y Direction
            1); // trap time
//    public var ghostBlinky = Ghost {
//    defaultImage1: Image {
//      url: "{__DIR__}images/ghostred1.png"
//    }
//
//    defaultImage2: Image {
//      url: "{__DIR__}images/ghostred2.png"
//    }
//
//     maze: this
//     pacMan: pacMan
//     x: 17
//     y: 15
//     xDirection: 0
//     yDirection: -1
//     trapTime: 1
//    };

    final Ghost ghostPinky = new Ghost(
            new Image(getClass().getResourceAsStream("images/ghostpink1.png")),
            new Image(getClass().getResourceAsStream("images/ghostpink2.png")),
            this,
            pacMan,
            14,
            15,
            1,  // x Direction
            0,  // y Direction
            5); // trap time
//   public var ghostPinky = Ghost {
//     defaultImage1:Image {
//        url: "{__DIR__}images/ghostpink1.png"
//     }
//
//     defaultImage2:Image {
//       url: "{__DIR__}images/ghostpink2.png"
//     }
//
//     maze: this
//     pacMan: pacMan
//     x: 12
//     y: 14
//     xDirection: 0
//     yDirection: 1
//     trapTime: 10
//   };

    final Ghost ghostInky = new Ghost(
            new Image(getClass().getResourceAsStream("images/ghostcyan1.png")),
            new Image(getClass().getResourceAsStream("images/ghostcyan2.png")),
            this,
            pacMan,
            12,
            15,
            1,   // x Direction
            0,   // y Direction
            20); // trap time
//   public var ghostInky = Ghost {
//     defaultImage1:Image {
//       url: "{__DIR__}images/ghostcyan1.png"
//     }
//     defaultImage2:Image {
//       url: "{__DIR__}images/ghostcyan2.png"
//     }
//
//     maze: this
//     pacMan: pacMan
//     x: 13
//     y: 15
//     xDirection: 1
//     yDirection: 0
//     trapTime: 20
//   };

    final Ghost ghostClyde = new Ghost(
            new Image(getClass().getResourceAsStream("images/ghostorange1.png")),
            new Image(getClass().getResourceAsStream("images/ghostorange2.png")),
            this,
            pacMan,
            16,
            15,
            1,   // x Direction
            0,   // y Direction
            30); // trap time
//   public var ghostClyde = Ghost {
//     defaultImage1:Image {
//        url: "{__DIR__}images/ghostorange1.png"
//     }
//     defaultImage2:Image {
//       url: "{__DIR__}images/ghostorange2.png"
//     }
//
//     maze: this
//     pacMan: pacMan
//     x: 15
//     y: 14
//     xDirection: -1
//     yDirection: 0
//     trapTime: 30
//   };

    ghosts = new Ghost[] {ghostBlinky, ghostPinky, ghostInky, ghostClyde};

    dyingPacMan = new DyingPacMan(this);
//    dyingPacMan.maze = this;
    dyingPacMan.setCenterX(0);
    dyingPacMan.setCenterY(0);
    dyingPacMan.setRadiusX(13);
    dyingPacMan.setRadiusY(13);
    dyingPacMan.setStartAngle(90);
    dyingPacMan.setLength(360);
    dyingPacMan.setType(ArcType.ROUND);
    dyingPacMan.setFill(Color.YELLOW);
    dyingPacMan.setVisible(false);

    livesCount = new SimpleIntegerProperty(2);

    // images showing how many lives remaining
    final ImageView livesImage1 = new ImageView(PACMAN_IMAGE);
    livesImage1.setX(MazeData.calcGridX(18));
    livesImage1.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
    livesImage1.visibleProperty().bind(livesCount.greaterThan(0));
    livesImage1.setCache(true);
    final ImageView livesImage2 = new ImageView(PACMAN_IMAGE);
    livesImage2.setX(MazeData.calcGridX(16));
    livesImage2.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
    livesImage2.visibleProperty().bind(livesCount.greaterThan(1));
    livesImage2.setCache(true);
    final ImageView livesImage3 = new ImageView(PACMAN_IMAGE);
    livesImage3.setX(MazeData.calcGridX(14));
    livesImage3.setY(MazeData.calcGridYFloat(MazeData.GRID_SIZE_Y + 0.8f));
    livesImage3.visibleProperty().bind(livesCount.greaterThan(2));
    livesImage3.setCache(true);
//  var livesImage = [
//    ImageView {
//      image: PACMAN_IMAGE
//      x: MazeData.calcGridX(18)
//      y: MazeData.calcGridY(30)
//      visible: bind livesCount > 0
//      cache: true
//    },
//    ImageView {
//      image: PACMAN_IMAGE
//      x: MazeData.calcGridX(16)
//      y: MazeData.calcGridY(30)
//      visible: bind livesCount > 1
//      cache: true
//    },
//    ImageView {
//      image: PACMAN_IMAGE
//      x: MazeData.calcGridX(14)
//      y: MazeData.calcGridY(30)
//      visible: bind livesCount > 2
//      cache: true
//    }
//  ];
    final ImageView[] livesImage = new ImageView[] {livesImage1, livesImage2, livesImage3};

    level = new SimpleIntegerProperty(1);
    addLifeFlag = true;
    waitForStart = new SimpleBooleanProperty(true);

    messageBox = new Group();
    final Rectangle rectMessage = new Rectangle(MazeData.calcGridX(5),
            MazeData.calcGridYFloat(17.5f),
            MazeData.GRID_GAP * 19,
            MazeData.GRID_GAP * 5);
    rectMessage.setStroke(Color.RED);
    rectMessage.setStrokeWidth(5);
    rectMessage.setFill(Color.CYAN);
    rectMessage.setOpacity(0.75);
    rectMessage.setArcWidth(25);
    rectMessage.setArcHeight(25);

    final StringBinding messageBinding = new StringBinding() {

            {
                super.bind(gamePaused);
            }

            @Override
            protected String computeValue() {
                if (gamePaused.get()) {
                    return " PRESS 'P' BUTTON TO RESUME";
                } else {
                    return "   PRESS ANY KEY TO START!";
                }
            }
        };

    final Text textMessage = new Text(MazeData.calcGridX(6),
            MazeData.calcGridYFloat(20.5f),
            "   PRESS ANY KEY TO START!");
    textMessage.textProperty().bind(messageBinding);
    textMessage.setFont(new Font(18));
    textMessage.setFill(Color.RED);
    messageBox.getChildren().add(rectMessage);
    messageBox.getChildren().add(textMessage);

    lastGameResult = new SimpleBooleanProperty(false);

    final StringBinding lastGameResultBinding = new StringBinding() {

        {
            super.bind(lastGameResult);
        }

        @Override
        protected String computeValue() {
            if (lastGameResult.get()) {
                return "  YOU WIN ";
            } else {
                return "GAME OVER   ";
            }
        }
    };

    gameResultText = new Text(MazeData.calcGridX(11),
            MazeData.calcGridY(11) + 8,
            " YOU WIN ");
    gameResultText.textProperty().bind(lastGameResultBinding);
    gameResultText.setFont(new Font(20));
    gameResultText.setFill(Color.RED);
    gameResultText.setVisible(false);

    flashingCount = 0;

    flashingTimeline = new Timeline();
    flashingTimeline.setCycleCount(5);
    final KeyFrame kf = new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
         gameResultText.setVisible(!gameResultText.isVisible());
         if (++flashingCount == 5) {
           messageBox.setVisible(true);
           waitForStart.set(true);
         }
      }

    });
    flashingTimeline.getKeyFrames().add(kf);

    group = new Group();

    // Make big black rectangle to cover entire background
    final Rectangle blackBackground = new Rectangle(0, 0,
            MazeData.calcGridX(MazeData.GRID_SIZE_X + 2),
            MazeData.calcGridY(MazeData.GRID_SIZE_Y + 3));
    blackBackground.setFill(Color.BLACK);
    blackBackground.setCache(true);
    group.getChildren().add(blackBackground);

    // Inner border of outside wall
    group.getChildren().add(new WallRectangle(0, 0, MazeData.GRID_SIZE_X, MazeData.GRID_SIZE_Y));

    // Top middle vertical wall
    group.getChildren().add(new WallRectangle(14, -0.5f, 15, 4));
    group.getChildren().add(new WallBlackRectangle(13.8f, -1, 15.3f, 0));

    group.getChildren().add(new WallRectangle(2, 2, 5, 4)); // upper-left rectangle
    group.getChildren().add(new WallRectangle(7, 2, 12, 4)); // upper 2nd-from-left rectangle
    group.getChildren().add(new WallRectangle(17, 2, 22, 4)); // upper 2nd-from-right rectangle
    group.getChildren().add(new WallRectangle(24, 2, 27, 4)); // upper-right rectangle
    group.getChildren().add(new WallRectangle(2, 6, 5, 7)); // left-side 2nd from top rectangle

    // middle top T
    group.getChildren().add(new WallRectangle(14, 6, 15, 10));
    group.getChildren().add(new WallRectangle(10, 6, 19, 7));
    group.getChildren().add(new WallBlackLine(14.05f, 7, 14.95f, 7));

    // Upper-left sideways T
    group.getChildren().add(new WallRectangle(7.5f, 9, 12, 10));
    group.getChildren().add(new WallRectangle(7, 6, 8, 13));
    group.getChildren().add(new WallBlackLine(8, 9, 8, 10));

    // Upper-right sideways T
    group.getChildren().add(new WallRectangle(17, 9, 21.5f, 10));
    group.getChildren().add(new WallRectangle(21, 6, 22, 13));
    group.getChildren().add(new WallBlackLine(21, 9, 21, 10));

    group.getChildren().add(new WallRectangle(24, 6, 27, 7)); // right-side 2nd from top rectangle

    //cage and the gate
    group.getChildren().add(new WallRectangle(10, 12, 19, 17));
    group.getChildren().add(new WallRectangle(10.5f, 12.5f, 18.5f, 16.5f));
    final Rectangle cageRect = new Rectangle(MazeData.calcGridX(13),
            MazeData.calcGridY(12),
            MazeData.GRID_GAP * 3,
            MazeData.GRID_GAP / 2);
    cageRect.setStroke(Color.GREY);
    cageRect.setFill(Color.GREY);
    cageRect.setCache(true);
    group.getChildren().add(cageRect);

    // Vertical rectangle below left side door
    group.getChildren().add(new WallRectangle(7, 15, 8, 20));

    // Vertical rectangle below right side door
    group.getChildren().add(new WallRectangle(21, 15, 22, 20));

    // middle middle T
    group.getChildren().add(new WallRectangle(14, 19, 15, 23));
    group.getChildren().add(new WallRectangle(10, 19, 19, 20));
    group.getChildren().add(new WallBlackLine(14.05f, 20, 14.95f, 20));

    // left L
    group.getChildren().add(new WallRectangle(4, 22, 5, 26));
    group.getChildren().add(new WallRectangle(2, 22, 5, 23));
    group.getChildren().add(new WallBlackRectangle(4, 22.05f, 5, 23.2f));

    group.getChildren().add(new WallRectangle(7, 22, 12, 23)); // left lower horizontal rectangle

    // right L
    group.getChildren().add(new WallRectangle(24, 22, 25, 26));
    group.getChildren().add(new WallRectangle(24, 22, 27, 23));
    group.getChildren().add(new WallBlackRectangle(24, 22.05f, 25, 23.2f));

    group.getChildren().add(new WallRectangle(17, 22, 22, 23)); // right lower horizontal rectangle

    group.getChildren().add(new WallRectangle(-1, 25, 2, 26)); // left horizontal wall
    group.getChildren().add(new WallRectangle(27, 25, MazeData.GRID_SIZE_X + 1, 26)); // right horizontal wall

    // left bottom upside-down T
    group.getChildren().add(new WallRectangle(7, 25, 8, 29));
    group.getChildren().add(new WallRectangle(2, 28, 12, 29));
    group.getChildren().add(new WallBlackLine(7.05f, 28, 7.95f, 28));

    // lower middle T
    group.getChildren().add(new WallRectangle(14, 25, 15, 29));
    group.getChildren().add(new WallRectangle(10, 25, 19, 26));
    group.getChildren().add(new WallBlackLine(14.05f, 26, 14.95f, 26));

    // right bottom upside-down T
    group.getChildren().add(new WallRectangle(21, 25, 22, 29));
    group.getChildren().add(new WallRectangle(17, 28, 27, 29));
    group.getChildren().add(new WallBlackLine(21.05f, 28, 21.95f, 28));

    // Outer border of outside wall
    final Rectangle outerWall = new Rectangle(MazeData.calcGridXFloat(-0.5f),
            MazeData.calcGridYFloat(-0.5f),
            (MazeData.GRID_SIZE_X + 1) * MazeData.GRID_GAP,
            (MazeData.GRID_SIZE_Y + 1) * MazeData.GRID_GAP);
    outerWall.setStrokeWidth(MazeData.GRID_STROKE);
    outerWall.setStroke(Color.BLUE);
    outerWall.setFill(null);
    outerWall.setArcWidth(12);
    outerWall.setArcHeight(12);
    outerWall.setCache(true);
    group.getChildren().add(outerWall);

    group.getChildren().add(new WallRectangle(-1, 9, 5, 13)); // outer wall above left side door
    group.getChildren().add(new WallRectangle(-1, 9.5f, 4.5f, 12.5f)); // inner wall above left side door
    group.getChildren().add(new WallRectangle(-1, 15, 5, 20)); // outer wall below left side wall
    group.getChildren().add(new WallRectangle(-1, 15.5f, 4.5f, 19.5f)); // inner wall below left side door wall

    group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 9, MazeData.GRID_SIZE_X + 1, 13)); // outer wall above right side door
    group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 9.5f, MazeData.GRID_SIZE_X + 1, 12.5f)); // inner wall above right side door
    group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 15, MazeData.GRID_SIZE_X + 1, 20)); // outer wall below right side wall
    group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 15.5f, MazeData.GRID_SIZE_X + 1, 19.5f)); // inner wall below right side door wall

    group.getChildren().add(new WallBlackRectangle(-2, 8, -0.5f, MazeData.GRID_SIZE_Y)); // black-out left garbage outside the wall
    group.getChildren().add(new WallBlackRectangle(-0.5f, 8, 0, 9.5f)); // black-out horizontal line inside outer-left wall above side door
    group.getChildren().add(new WallBlackRectangle(-0.5f, 19.5f, 0, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-left wall below side door

    group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X + 0.5f, 8, MazeData.GRID_SIZE_X + 2, MazeData.GRID_SIZE_Y)); // black-out garbage on right side of outside wall
    group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 8, MazeData.GRID_SIZE_X + 0.5f, 9.5f)); // black-out horizontal line inside outer-right wall above side door
    group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 19.5f, MazeData.GRID_SIZE_X + 0.5f, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-right wall below side door

     // black-out outer walls inside both side doors
    group.getChildren().add(new WallBlackRectangle(-1, 13, 1, 15)); // left
    group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X - 1, 13, MazeData.GRID_SIZE_X + 1, 15)); // right

    // Add back 4 blue wall segments that were deleted
    group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 9, -0.5f, 9.5f));
    group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 19.5f, -0.5f, 20));
    group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 9, MazeData.GRID_SIZE_X + 0.5f, 9.5f));
    group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 19.5f, MazeData.GRID_SIZE_X + 0.5f, 20));

    final Text textScore = new Text(MazeData.calcGridX(0),
            MazeData.calcGridY(MazeData.GRID_SIZE_Y + 2),
            "SCORE: " + pacMan.score);
    textScore.textProperty().bind(pacMan.score.asString("SCORE: %1d  "));
    textScore.setFont(new Font(20));
    textScore.setFill(Color.YELLOW);
    textScore.setCache(true);
    group.getChildren().add(textScore);

    group.getChildren().addAll(SCORE_TEXT);
    group.getChildren().add(dyingPacMan);
    group.getChildren().addAll(livesImage);
    group.getChildren().add(gameResultText);

    final Text textLevel = new Text(MazeData.calcGridX(22),
            MazeData.calcGridY(MazeData.GRID_SIZE_Y + 2),
            "LEVEL: " + level);
    textLevel.textProperty().bind(level.asString("LEVEL: %1d "));
    textLevel.setFont(new Font(20));
    textLevel.setFill(Color.YELLOW);
    textLevel.setCache(true);
    group.getChildren().add(textLevel);
    group.setFocusTraversable(true); // patweb
    group.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override public void handle(KeyEvent ke) {
        onKeyPressed(ke);
      }
    });


    // postinit
    putDotHorizontally(2,12,1);
    putDotHorizontally(17,27,1);
    
    putDotHorizontally(2,27,5);

    putDotHorizontally(2,5,8);
    putDotHorizontally(24,27,8);

    putDotHorizontally(10,13,8);
    putDotHorizontally(16,19,8);

    putDotHorizontally(2,12,21);
    putDotHorizontally(17,27,21);

    putDotHorizontally(2,2,24);
    putDotHorizontally(27,27,24);

    putDotHorizontally(7,12,24);
    putDotHorizontally(17,22,24);

    putDotHorizontally(2,5,27);
    putDotHorizontally(24,27,27);

    putDotHorizontally(10,12,27);
    putDotHorizontally(17,19,27);

    putDotHorizontally(2,27,30); // bottom row


    putDotVertically(1,1,8);
    putDotVertically(28,1,8);

    putDotVertically(1,21,24);
    putDotVertically(28,21,24);

    putDotVertically(1,27,30);
    putDotVertically(28,27,30);
    
    putDotVertically(3,24,27);
    putDotVertically(26,24,27);

    putDotVertically(6,1,27);
    putDotVertically(23,1,27);

    putDotVertically(9,5,8);
    putDotVertically(20,5,8);

    putDotVertically(9,24,27);
    putDotVertically(20,24,27);

    putDotVertically(13,1,4);
    putDotVertically(16,1,4);

    putDotVertically(13,21,24);
    putDotVertically(16,21,24);

    putDotVertically(13,27,30);
    putDotVertically(16,27,30);


    // insert pacMan into group.content;
    group.getChildren().add(pacMan);

    // insert ghosts into group.content;
    group.getChildren().addAll(ghosts);

    // Black-out side door exit points so moving objects disappear at maze borders
//    insert WallBlackRectangle{ x1:-3, y1:13, x2:0, y2:15} into group.content;
//    insert WallBlackRectangle{ x1:29, y1:13, x2:31, y2:15} into group.content;
    group.getChildren().add(new WallBlackRectangle(-2, 13, -0.5f, 15));
    group.getChildren().add(new WallBlackRectangle(29.5f, 13, 31, 15));

    // insert messageBox into group.content;
    group.getChildren().add(messageBox);

    // end postinit

    getChildren().add(group);

    if (DEBUG) {
      MazeData.printData();
      MazeData.printDots();
    }
  }


  public void onKeyPressed(KeyEvent e) {
//  public override var onKeyPressed = function ( e: KeyEvent ) : Void {
//  @Override

    // wait for the player's keyboard input to start the game
    if (waitForStart.get()) {
      waitForStart.set(false);
      startNewGame();
      return;
    }

    if (e.getCode() == KeyCode.P) {
//    if ( e.code == KeyCode.VK_P ) {
      if (gamePaused.get()) {
        resumeGame();
      } else {
        pauseGame();
      }

      return;
    }

    if (gamePaused.get()) {
      return;
    }

    if (e.getCode() == KeyCode.DOWN) {
//    if ( e.getCode() == KeyCode.VK_DOWN ) {
      pacMan.setKeyboardBuffer(MovingObject.MOVE_DOWN);
    } else if (e.getCode() == KeyCode.UP) {
      pacMan.setKeyboardBuffer(MovingObject.MOVE_UP);
    } else if (e.getCode() == KeyCode.RIGHT) {
      pacMan.setKeyboardBuffer(MovingObject.MOVE_RIGHT);
    } else if (e.getCode() == KeyCode.LEFT) {
      pacMan.setKeyboardBuffer(MovingObject.MOVE_LEFT);
    }

  }


  // create a Dot GUI object
  public final Dot createDot(int x1, int y1, int type) {
//  public function createDot( x1: Number,  y1:Number, type:Integer ): Dot {
    Dot d = new Dot(MazeData.calcGridX(x1), MazeData.calcGridY(y1), type);

    if (type == MazeData.MAGIC_DOT) {
//      d = Dot {
//        x: MazeData.calcGridX(x1)
//        y: MazeData.calcGridY(y1)
//        dotType: type
//        visible: true
//        shouldStopAnimation: bind gamePaused or waitForStart
//      }
      d.playTimeline();

      d.shouldStopAnimation.bind(gamePaused.or(waitForStart)); // patweb
    }
//    else {
//       d = Dot {
//        x: MazeData.calcGridX(x1)
//        y: MazeData.calcGridY(y1)
//        dotType: type
//        visible: true
//       }
//    }

    // set the dot type in data model
    MazeData.setData(x1, y1, type);

    // set dot reference
    MazeData.setDot(x1, y1, d);

    return d;
  }

  // put dots into the maze as a horizontal line
  public final void putDotHorizontally(int x1, int x2, int y) {

    Dot dot;
//    var dots =
//    for ( x in [ x1..x2] )
    for (int x = x1; x <= x2; x++) {
      if (MazeData.getData(x, y) == MazeData.EMPTY) {
        int dotType;
//      var dotType: Integer;

        if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
          dotType = MazeData.MAGIC_DOT;
        } else {
          dotType = MazeData.NORMAL_DOT;
        }

        dot = createDot(x, y, dotType);

        // insert dots into group
        group.getChildren().add(dot);
      }
      else {
        if (DEBUG) {
          System.out.println("!! WARNING: Trying to place horizontal dots at occupied position (" + x + ", " + y + ")");
        }
      }
    }
  }

  // put dots into the maze as a vertical line
  public final void putDotVertically(int x, int y1, int y2) {
    Dot dot;
    for (int y = y1; y <= y2; y++) {
      if (MazeData.getData(x, y) == MazeData.EMPTY) {
        int dotType;

        if ( (x == 28 || x == 1) && (y == 3 || y == 24) ) {
          dotType = MazeData.MAGIC_DOT;
        }
        else {
          dotType = MazeData.NORMAL_DOT;
        }

        dot = createDot(x, y, dotType);
        group.getChildren().add(dot);
      }
      else {
        if (DEBUG) {
          System.out.println("!! WARNING: Trying to place vertical   dots at occupied position (" + x + ", " + y + ")");
        }
      }
    }
  }


  public void makeGhostsHollow() {

    ghostEatenCount = 0;

    for (Ghost g : ghosts) {
      g.changeToHollowGhost();
    }
  }


  // determine if pacman meets a ghost
  public boolean hasMet(Ghost g) {

    int distanceThreshold = 22;

    int x1 = g.imageX.get();
    int x2 = pacMan.imageX.get();

    int diffX = Math.abs(x1 - x2);

    if (diffX >= distanceThreshold) {
      return false;
    }

    int y1 = g.imageY.get();
    int y2 = pacMan.imageY.get();
    int diffY = Math.abs(y1 - y2);

    if (diffY >= distanceThreshold) {
      return false;
    }

    // calculate the distance to see if pacman touches the ghost
    if (diffY * diffY + diffX * diffX <= distanceThreshold * distanceThreshold) {
      return true;
    }

    return false;
  }

  public void pacManMeetsGhosts() {

    for (Ghost g : ghosts) {
      if (hasMet(g)) {
        if (g.isHollow) {
          pacManEatsGhost(g);
        }
        else {
          for (Ghost ghost : ghosts) {
            ghost.stop();
          }
          pacMan.stop();

          dyingPacMan.startAnimation(pacMan.imageX.get(), pacMan.imageY.get());
          break;
        }
      }
    }
  }

  public void pacManEatsGhost(Ghost g) {
//  public function pacManEatsGhost(g: Ghost) {

    ghostEatenCount++;

    int multiplier = 1;
//    var s = 1;
    for (int i = 1; i <= ghostEatenCount; i++) {
//    for ( i in [1..ghostEatenCount] ) s = s + s;
      multiplier += multiplier;
    }

    pacMan.score.set(pacMan.score.get() + multiplier * 100);
    if ( addLifeFlag && (pacMan.score.get() >= 10000) ) {
//    if ( addLifeFlag and pacMan.score >= 10000 ) {
      addLife();
    }

    ScoreText st = SCORE_TEXT[ghostEatenCount - 1];
//    var st = scoreText[ghostEatenCount-1];
//    st.x = g.imageX - 10;
    st.setX(g.imageX.get() - 10);
//    st.y = g.imageY;
    st.setY(g.imageY.get());

    g.stop();
    g.resetStatus();
    g.trapCounter = -10;

    st.showText();

  }

  public void resumeGame() {

    if (!gamePaused.get()) {
      return;
    }

    messageBox.setVisible(false);

    for (Ghost g : ghosts) {
      if (g.isPaused()) {
        g.start();
      }
    }

    if (pacMan.isPaused()) {
      pacMan.start();
    }

    if (dyingPacMan.isPaused()) {
      dyingPacMan.start();
    }

    if (flashingTimeline.getStatus() == Animation.Status.PAUSED) {
//    if ( flashingTimeline.paused ) {
      flashingTimeline.play();
    }

    gamePaused.set(false);

  }

  public void pauseGame() {

    if ( waitForStart.get() || gamePaused.get() ) {
      return;
    }

    messageBox.setVisible(true);

    for (Ghost g : ghosts) {
      if (g.isRunning()) {
        g.pause();
      }
    }

    if (pacMan.isRunning()) {
      pacMan.pause();
    }

    if (dyingPacMan.isRunning()) {
      dyingPacMan.pause();
    }

    if (flashingTimeline.getStatus() == Animation.Status.RUNNING) {
//    if ( flashingTimepacmanline.running ) {
      flashingTimeline.pause();
    }
    gamePaused.set(true);
  }


  // reset status and start a new game
  public void startNewGame() {

    messageBox.setVisible(false);
    pacMan.resetStatus();

    gameResultText.setVisible(false);

    if (!lastGameResult.get()) {
//    if ( lastGameResult == false ) {
      level.set(1);
      addLifeFlag = true;
      pacMan.score.set(0);
      pacMan.dotEatenCount = 0;

      livesCount.set(2);
    }
    else {
      lastGameResult.set(false);
      level.set(level.get() + 1);
//      level ++;
    }

    for (int x = 1; x <= MazeData.GRID_SIZE_X; x++) {
//    for ( x in [1..MazeData.GRID_SIZE] )
      for (int y = 1; y <= MazeData.GRID_SIZE_Y; y++) {
//      for ( y in [1..MazeData.GRID_SIZE] ) {
        Dot dot = (Dot) MazeData.getDot(x, y);
//        var dot : Dot = MazeData.getDot( x, y ) as Dot ;

        if ( (dot != null) && !dot.isVisible() ) {
          dot.setVisible(true);
        }
      }
    }
    for (Ghost g : ghosts) {
      g.resetStatus();
    }

}

  // reset status and start a new level
  public void startNewLevel() {

    lastGameResult.set(true);

    pacMan.hide();
    pacMan.dotEatenCount = 0;

    for (Ghost g : ghosts) {
      g.hide();
    }

    flashingCount = 0;
    flashingTimeline.playFromStart();
  }

  // reset status and start a new life
  public void startNewLife() {

    // reduce a life of Pac-Man
    if (livesCount.get() > 0) {
//    if ( livesCount > 0 ) {
      livesCount.set(livesCount.get() - 1);
//      livesCount--;
    }
    else {
      lastGameResult.set(false);
      flashingCount = 0;
      flashingTimeline.playFromStart();
      return;
    }

    pacMan.resetStatus();

    for (Ghost g : ghosts) {
      g.resetStatus();
    }
  }

  public void addLife() {

    if (addLifeFlag) {
      livesCount.set(livesCount.get() + 1);
//      livesCount ++;
      addLifeFlag = false;
    }
  }

}
