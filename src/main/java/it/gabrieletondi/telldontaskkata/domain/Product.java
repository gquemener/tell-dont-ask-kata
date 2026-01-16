package it.gabrieletondi.telldontaskkata.domain;

public class Product {
  private String name;
  private Money price;
  private Category category;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Money getPriceWithoutTax() {
    return price;
  }

  public void setPriceWithoutTax(Money price) {
    this.price = price;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Money getPriceWithTax() {
    return price.add(getTax());
  }

  public Money getTax() {
    return price.getTax(this.category.getTaxPercentage());
  }
}
