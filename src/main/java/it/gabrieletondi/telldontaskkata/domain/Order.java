package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

  private String currency;
  private List<OrderItem> items;
  private OrderStatus status;
  private int id;

  public Order() {
    setStatus(OrderStatus.CREATED);
    setItems(new ArrayList<>());
    setCurrency("EUR");
  }

  public BigDecimal getTotal() {
    return items.stream()
        .map(OrderItem::getTaxedAmount)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
  }

  public BigDecimal getTax() {
    return items.stream().map(OrderItem::getTax).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void addItem(final OrderItem orderItem) {
    items.add(orderItem);
  }

  public void approve() {
    if (status.equals(OrderStatus.SHIPPED)) {
      throw new ShippedOrdersCannotBeChangedException();
    }

    if (status.equals(OrderStatus.REJECTED)) {
      throw new RejectedOrderCannotBeApprovedException();
    }

    status = OrderStatus.APPROVED;
  }

  public void reject() {
    if (status.equals(OrderStatus.SHIPPED)) {
      throw new ShippedOrdersCannotBeChangedException();
    }

    if (status.equals(OrderStatus.APPROVED)) {
      throw new ApprovedOrderCannotBeRejectedException();
    }

    status = OrderStatus.REJECTED;
  }
}
