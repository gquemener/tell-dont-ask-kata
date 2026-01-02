package it.gabrieletondi.telldontaskkata.domain;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

public record OrderItem(Quantity quantity, Product product) {

  public BigDecimal getTaxedAmount() {
    return product.getTaxedAmount().multiply(quantity.value()).setScale(2, HALF_UP);
  }

  public BigDecimal getTax() {
    return product.getTax().multiply(quantity.value());
  }

  public static class UnknownProductException extends RuntimeException {}
}
