package it.gabrieletondi.telldontaskkata.domain.status;

import it.gabrieletondi.telldontaskkata.domain.Order.ApprovedOrderCannotBeRejectedException;

public record Approved() implements OrderStatus {

  @Override
  public it.gabrieletondi.telldontaskkata.domain.status.Approved toApproved() {
    return this;
  }

  @Override
  public Rejected toRejected() {
    throw new ApprovedOrderCannotBeRejectedException();
  }

  @Override
  public Shipped toShipped() {
    return new Shipped();
  }
}
