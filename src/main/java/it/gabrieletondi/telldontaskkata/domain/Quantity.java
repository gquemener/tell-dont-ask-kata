package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Quantity {
  private final BigDecimal value;

  public static Quantity valueOf(final int i) {
    if (i <= 0 || i > 100) {
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

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Quantity quantity = (Quantity) o;
    return Objects.equals(value, quantity.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  public static class InvalidQuantityException extends IllegalArgumentException {}
}
