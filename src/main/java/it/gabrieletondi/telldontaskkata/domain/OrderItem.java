package it.gabrieletondi.telldontaskkata.domain;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

public record OrderItem(int quantity, Product product) {

  public BigDecimal getTaxedAmount() {
    return product.getTaxedAmount().multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
  }

  public BigDecimal getTax() {
    return product.getTax().multiply(BigDecimal.valueOf(quantity));
  }

  public static class UnknownProductException extends RuntimeException {}
}
