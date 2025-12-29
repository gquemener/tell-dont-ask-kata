package it.gabrieletondi.telldontaskkata.domain;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.CREATED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.REJECTED;
import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.SHIPPED;

import it.gabrieletondi.telldontaskkata.service.ShipmentService;
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

  public void ship(final ShipmentService shipmentService) {
    if (status.equals(CREATED) || status.equals(REJECTED)) {
      throw new OrderCannotBeShippedException();
    }

    if (status.equals(SHIPPED)) {
      throw new OrderCannotBeShippedTwiceException();
    }
    shipmentService.ship(this);
    status = OrderStatus.SHIPPED;
  }

  public static class ApprovedOrderCannotBeRejectedException extends RuntimeException {}

  public static class RejectedOrderCannotBeApprovedException extends RuntimeException {}

  public static class ShippedOrdersCannotBeChangedException extends RuntimeException {}

  public static class OrderCannotBeShippedException extends RuntimeException {}

  public static class OrderCannotBeShippedTwiceException extends RuntimeException {}
}
