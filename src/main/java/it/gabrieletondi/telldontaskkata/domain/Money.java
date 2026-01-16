package it.gabrieletondi.telldontaskkata.domain;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.util.Objects;

public final class Money {

  public static final Money FREE = new Money(BigDecimal.ZERO);
  private final BigDecimal value;

  private Money(final BigDecimal value) {
    this.value = value;
  }

  public static Money valueOf(final BigDecimal value) {
    if (!value.abs().equals(value)) {
      throw new InvalidMoneyException();
    }
    return new Money(value);
  }

  public BigDecimal value() {
    return value;
  }

  public Money add(final Money other) {
    return new Money(value.add(other.value).setScale(2, HALF_UP));
  }

  public Money divide(final Money other) {
    return new Money(value.divide(other.value).setScale(2, HALF_UP));
  }

  public Money multiply(final Quantity quantity) {
    return new Money(value.multiply(quantity.value()).setScale(2, HALF_UP));
  }

  public Money getTax(final BigDecimal taxPercentage) {
    return new Money(
        value.divide(BigDecimal.valueOf(100)).multiply(taxPercentage).setScale(2, HALF_UP));
  }

  @Override
  public boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Objects.equals(value, money.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "Money{" + "value=" + value + '}';
  }

  public static class InvalidMoneyException extends IllegalArgumentException {}
}
