package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.domain.Order.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.domain.Order.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.domain.Order.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.domain.Order.ShippedOrdersCannotBeChangedException;

public interface OrderStatus {
  OrderStatus toApproved();

  OrderStatus toRejected();

  OrderStatus toShipped();

  record Created() implements OrderStatus {

    @Override
    public OrderStatus toApproved() {
      return new Approved();
    }

    @Override
    public OrderStatus toRejected() {
      return new Rejected();
    }

    @Override
    public OrderStatus toShipped() {
      throw new OrderCannotBeShippedException();
    }
  }

  record Approved() implements OrderStatus {

    @Override
    public OrderStatus toApproved() {
      return this;
    }

    @Override
    public OrderStatus toRejected() {
      throw new ApprovedOrderCannotBeRejectedException();
    }

    @Override
    public OrderStatus toShipped() {
      return new Shipped();
    }
  }

  record Rejected() implements OrderStatus {

    @Override
    public OrderStatus toApproved() {
      throw new RejectedOrderCannotBeApprovedException();
    }

    @Override
    public OrderStatus toRejected() {
      return this;
    }

    @Override
    public OrderStatus toShipped() {
      throw new OrderCannotBeShippedException();
    }
  }

  record Shipped() implements OrderStatus {

    @Override
    public OrderStatus toApproved() {
      throw new ShippedOrdersCannotBeChangedException();
    }

    @Override
    public OrderStatus toRejected() {
      throw new ShippedOrdersCannotBeChangedException();
    }

    @Override
    public OrderStatus toShipped() {
      throw new OrderCannotBeShippedTwiceException();
    }
  }
}
