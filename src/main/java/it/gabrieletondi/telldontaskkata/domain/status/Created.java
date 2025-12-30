package it.gabrieletondi.telldontaskkata.domain.status;

import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedException;

public record Created() implements OrderStatus {

  @Override
  public Approved toApproved() {
    return new Approved();
  }

  @Override
  public Rejected toRejected() {
    return new Rejected();
  }

  @Override
  public Shipped toShipped() {
    throw new OrderCannotBeShippedException();
  }
}
