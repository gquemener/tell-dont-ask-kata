package it.gabrieletondi.telldontaskkata.domain.status;

public sealed interface OrderStatus permits Created, Approved, Rejected, Shipped {
  Approved toApproved();

  Rejected toRejected();

  Shipped toShipped();
}
