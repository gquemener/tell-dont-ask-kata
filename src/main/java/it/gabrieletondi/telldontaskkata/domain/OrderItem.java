package it.gabrieletondi.telldontaskkata.domain;

public record OrderItem(Quantity quantity, Product product) {

  public Money getTaxedAmount() {
    return product.getPriceWithTax();
  }

  public Money getTax() {
    return product.getTax();
  }

  public static class UnknownProductException extends RuntimeException {}
}
