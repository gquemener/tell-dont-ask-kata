package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

public final class Quantity {
  private final BigDecimal value;

  public static Quantity valueOf(final int i) {
    if (i <= 0) {
      throw new InvalidQuantityException();
    }
    return new Quantity(BigDecimal.valueOf(i));
  }

  private Quantity(final BigDecimal value) {
    this.value = value;
  }

  public BigDecimal value() {
    return value;
  }

  public static class InvalidQuantityException extends IllegalArgumentException {}
}
