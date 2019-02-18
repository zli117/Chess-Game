package model;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Location;
import utils.Move;
import utils.Side;
import utils.Vector;

/**
 * Pawn with en passant.
 */
public class Pawn extends Piece {

  private boolean mMovingUp;
  private Location mGhostLocation;
  private Ghost mGhost;

  /**
   * New Create a pawn.
   *
   * @param chessBoard The chess board it belongs to
   * @param side       Which side it belongs to
   */
  public Pawn(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
    mMovingUp = true;
    mGhostLocation = null;
    mGhost = null;
  }

  /**
   * Extra logic for checking en passant. If the piece as moved from it's
   * initial two step move, remove the associated ghost. If the move is the
   * initial second step move, add the ghost.
   *
   * @param location The new location. Will be null if the piece has been
   */
  @Override
  public void setLocation(Location location) {
    ChessBoardBase chessBoard = getChessBoard();

    // Set the ghost if the pawn has not moved before and it's moving two steps
    Location currLocation = getLocation();
    // If the current location is null, this means we are adding the piece to
    // the board. Nothing should be done in terms of ghost. Since if this is
    // initializing the board, there should have no ghost. Otherwise if it's
    // restoring, then the ghost will be taken care of.
    if (currLocation != null) {
      if (location != null && !hasMoved()) {
        // If the new location is two steps away from the current one we set a
        // ghost at the place as if the pawn has only moved one step
        if (Math.abs(location.getRow() - currLocation.getRow()) == 2) {
          mGhostLocation = new Location(
              (currLocation.getRow() + location.getRow()) / 2,
              currLocation.getCol());
          mGhost = new Ghost(chessBoard, getSide(), this);
          chessBoard.setPiece(mGhost, mGhostLocation);
        }
      }
    }
    super.setLocation(location);
  }

  /**
   * Whether the pawn is moving up.
   */
  public boolean isMovingUp() {
    return mMovingUp;
  }

  /**
   * Set true if the pawn is moving up. False otherwise. Pawn could only move up
   * or down.
   */
  public void setMovingUp(boolean movingUp) {
    mMovingUp = movingUp;
  }

  /**
   * Extra logic for adding the second step move.
   */
  @Override
  public Set<Move> getMovesAndAttacks() {
    LinkedHashSet<Move> moves = new LinkedHashSet<>();
    Location current = getLocation();
    Vector moveDirection = new Vector(mMovingUp ? -1 : 1, 0);
    Location moveOnDirection = current.getIncrement(moveDirection);
    moves.add(new Move(current, moveOnDirection));
    if (!hasMoved() && getChessBoard().checkIsEmpty(moveOnDirection)) {
      moves.add(new Move(current, moveOnDirection.getIncrement(moveDirection)));
    }
    Move rightAttack = new Move(current, moveOnDirection.getRight());
    rightAttack.attack();
    moves.add(rightAttack);
    Move leftAttack = new Move(current, moveOnDirection.getLeft());
    leftAttack.attack();
    moves.add(leftAttack);
    return Collections.unmodifiableSet(moves);
  }

  /**
   * Currently only pawn has the privilege to kill a ghost.
   */
  @Override
  public boolean canKillGhost() {
    return true;
  }

  @Override
  public URL getImageResourceUrl() {
    if (getSide() == Side.White) {
      return getClass().getResource("/images/45px-Chess_plt45.svg.png");
    } else {
      return getClass().getResource("/images/45px-Chess_pdt45.svg.png");
    }
  }

}
