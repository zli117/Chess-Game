package model;

/**
 * Enum to denote the side of a piece.
 */
public enum Side {

  Black, White;

  public Side next() {
    return Side.values()[(ordinal() + 1) % Side.values().length];
  }

}

