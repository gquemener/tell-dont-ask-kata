package it.gabrieletondi.telldontaskkata.domain.status;

import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.domain.Order.RejectedOrderCannotBeApprovedException;

public record Rejected() implements OrderStatus {

  @Override
  public Approved toApproved() {
    throw new RejectedOrderCannotBeApprovedException();
  }

  @Override
  public it.gabrieletondi.telldontaskkata.domain.status.Rejected toRejected() {
    return this;
  }

  @Override
  public Shipped toShipped() {
    throw new OrderCannotBeShippedException();
  }
}
