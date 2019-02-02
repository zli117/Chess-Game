package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Direction;
import utils.Location;
import utils.Move;

public class MockPiece extends UnboundedPiece {

  private boolean mKilled;
  private boolean mIsGhost;
  private boolean mCanKillGhost;
  private boolean mModifiedAdjustedMoves;
  private boolean mSetAdjustedMoves;

  MockPiece(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
    mKilled = false;
    mCanKillGhost = false;
    mModifiedAdjustedMoves = false;
    mSetAdjustedMoves = false;
  }

  @Override
  protected ArrayList<Direction> getDirections() {
    Direction[] directions = {
        new Direction(1, 0),
        new Direction(-1, 0),
        new Direction(0, 1),
        new Direction(0, -1)};
    return new ArrayList<>(Arrays.asList(directions));
  }

  @Override
  public Set<Move> getMovesAndAttacks() {
    Set<Move> moves = new LinkedHashSet<>(super.getMovesAndAttacks());
    Location currLocation = getLocation();
    moves.add(
        new Move(currLocation, currLocation.getIncrement(new Direction(1, 1))));
    return Collections.unmodifiableSet(moves);
  }

  @Override
  public void killed() {
    super.killed();
    mKilled = true;
  }

  boolean isAlive() {
    return !mKilled;
  }

  void setIsGhost(boolean isGhost) {
    mIsGhost = isGhost;
  }

  void setCanKillGhost(boolean canKillGhost) {
    mCanKillGhost = canKillGhost;
  }

  @Override
  void modifyAdjustedMoves() {
    super.modifyAdjustedMoves();
    mModifiedAdjustedMoves = true;
  }

  boolean hasModifiedAdjustedMoves() {
    return mModifiedAdjustedMoves;
  }

  @Override
  void setAdjustedMoves(Set<Move> adjustedMoves) {
    super.setAdjustedMoves(adjustedMoves);
    mSetAdjustedMoves = true;
  }

  boolean hasSetAdjustedMoves() {
    return mSetAdjustedMoves;
  }

  @Override
  public boolean canKillGhost() {
    return mCanKillGhost;
  }

  @Override
  public boolean isGhost() {
    return mIsGhost;
  }

}
