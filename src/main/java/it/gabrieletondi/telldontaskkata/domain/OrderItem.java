package it.gabrieletondi.telldontaskkata.domain;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;

public class OrderItem {
  private Product product;
  private int quantity;

  public OrderItem(final int quantity, final Product product) {
    this.quantity = quantity;
    this.product = product;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public BigDecimal getTaxedAmount() {
    return product.getTaxedAmount().multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
  }

  public BigDecimal getTax() {
    return product.getTax().multiply(BigDecimal.valueOf(quantity));
  }

  public static class UnknownProductException extends RuntimeException {}
}
