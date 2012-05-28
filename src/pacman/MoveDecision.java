package pacman;

/**
 * MoveDecision.fx created on 2009-1-28, 14:42:00 <br>
 * MoveDecision.java created October 2011
 *
 * @author Henry Zhang
 * @author Patrick Webster
 */
public class MoveDecision {

  // x and y of an intended move
//  public var x: Number;
  public int x;
//  public var y: Number;
  public int y;

//  public var score: Number;
  public int score;

  // evaluate if the move is valid,
  // if it is invalid, returns -1;
  // if it is valid, compute its score for ranking the final decision
//  public function evaluate( pacMan: PacMan, isHollow: Boolean ): Void {
  public void evaluate(PacMan pacMan, boolean isHollow) {
//    if ( x < 1 or y < 1 or y >= MazeData.GRID_SIZE or x >= MazeData.GRID_SIZE){
    if (x < 1 || y < 1 || (y >= MazeData.GRID_SIZE_Y) || (x >= MazeData.GRID_SIZE_X)) {
      score = -1;
      return;
    }

//    var status = MazeData.getData(x, y);
    int status = MazeData.getData(x, y);
    if (status == MazeData.BLOCK) {
      score = -1;
      return;
    }

    int distance = Math.abs(x - pacMan.x) + Math.abs(y - pacMan.y);
//    int distance = Math.abs( x - pacMan.x.get() ) + Math.abs( y - pacMan.y.get() );

    if (isHollow) {
      score = 500 + distance; // mode to run away from Pac-Man
    }
    else {
      score = 500 - distance; // mode to chase Pac-Man
    }

  }

}
