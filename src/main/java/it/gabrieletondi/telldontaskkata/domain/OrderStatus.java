package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.Order.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.domain.Order.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.domain.Order.ShippedOrdersCannotBeChangedException;

public interface OrderStatus {
  Approved toApproved();

  Rejected toRejected();

  Shipped toShipped();

  record Created() implements OrderStatus {

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

  record Approved() implements OrderStatus {

    @Override
    public Approved toApproved() {
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

  record Rejected() implements OrderStatus {

    @Override
    public Approved toApproved() {
      throw new RejectedOrderCannotBeApprovedException();
    }

    @Override
    public Rejected toRejected() {
      return this;
    }

    @Override
    public Shipped toShipped() {
      throw new OrderCannotBeShippedException();
    }
  }

  record Shipped() implements OrderStatus {

    @Override
    public Approved toApproved() {
      throw new ShippedOrdersCannotBeChangedException();
    }

    @Override
    public Rejected toRejected() {
      throw new ShippedOrdersCannotBeChangedException();
    }

    @Override
    public Shipped toShipped() {
      throw new OrderCannotBeShippedTwiceException();
    }
  }
}
