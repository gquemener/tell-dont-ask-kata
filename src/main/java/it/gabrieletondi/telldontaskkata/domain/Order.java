package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.status.Created;
import it.gabrieletondi.telldontaskkata.domain.status.OrderStatus;
import it.gabrieletondi.telldontaskkata.service.ShipmentService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

  private String currency;
  private List<OrderItem> items;
  private OrderStatus status;
  private int id;

  public Order(int id) {
    this.id = id;
    this.status = new Created();
    this.items = new ArrayList<>();
    this.currency = "EUR";
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

  public List<OrderItem> getItems() {
    return items;
  }

  public BigDecimal getTax() {
    return items.stream().map(OrderItem::getTax).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
  }

  public OrderStatus getStatus() {
    return status;
  }

  @Deprecated
  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void addItem(final OrderItem orderItem) {
    items.add(orderItem);
  }

  public void approve() {
    status = status.toApproved();
  }

  public void reject() {
    status = status.toRejected();
  }

  public void ship(final ShipmentService shipmentService) {
    status = status.toShipped();
    shipmentService.ship(this);
  }

  public static class ApprovedOrderCannotBeRejectedException extends RuntimeException {}

  public static class RejectedOrderCannotBeApprovedException extends RuntimeException {}

  public static class ShippedOrdersCannotBeChangedException extends RuntimeException {}

  public static class OrderCannotBeShippedException extends RuntimeException {}

  public static class OrderCannotBeShippedTwiceException extends RuntimeException {}
}
