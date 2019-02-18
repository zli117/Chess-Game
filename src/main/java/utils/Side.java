package utils;

/**
 * Enum to denote the side of a piece.
 */
public enum Side {

  White, Black;

  public Side next() {
    return Side.values()[(ordinal() + 1) % Side.values().length];
  }

}
