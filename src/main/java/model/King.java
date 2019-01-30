package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import utils.Location;

public class King extends BoundedPiece {

  private LinkedHashSet<Location> mCachedMoves;
  private LinkedHashSet<Location> mCachedAttacks;

  public King(ChessBoardBase chessBoard, Side side) {
    super(chessBoard, side);
  }

  @Override
  public void boardUpdated() {
    mCachedAttacks = null;
    mCachedMoves = null;
  }

  @Override
  public Set<Location> getLegalMoves() {
    if (mCachedMoves == null) {
      HashSet<Location> underAttack = new HashSet<>();
      for (Piece piece : getChessBoard().getOpponentPieces(getSide())) {
        underAttack.addAll(piece.getAttacks());
      }
      Set<Location> possibleMoves = getAttacks();
      mCachedMoves = new LinkedHashSet<>();
      for (Location location : possibleMoves) {
        if (!underAttack.contains(location)) {
          mCachedMoves.add(location);
        }
      }
    }
    return Collections.unmodifiableSet(mCachedMoves);
  }

  @Override
  public Set<Location> getAttacks() {
    if (mCachedAttacks == null) {
      int[][] offsets = {
          {1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, -1}, {-1, 0}, {-1, 1}};
      mCachedAttacks = getLocationsWithRelativeLocation(offsets, getLocation());
    }
    return Collections.unmodifiableSet(mCachedAttacks);
  }
}
