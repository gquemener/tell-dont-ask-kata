package it.gabrieletondi.telldontaskkata.domain.status;

import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.domain.Order.ShippedOrdersCannotBeChangedException;

public record Shipped() implements OrderStatus {

  @Override
  public Approved toApproved() {
    throw new ShippedOrdersCannotBeChangedException();
  }

  @Override
  public Rejected toRejected() {
    throw new ShippedOrdersCannotBeChangedException();
  }

  @Override
  public it.gabrieletondi.telldontaskkata.domain.status.Shipped toShipped() {
    throw new OrderCannotBeShippedTwiceException();
  }
}
