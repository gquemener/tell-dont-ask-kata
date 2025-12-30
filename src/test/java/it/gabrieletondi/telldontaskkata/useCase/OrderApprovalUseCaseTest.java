package it.gabrieletondi.telldontaskkata.useCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.Order.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.domain.Order.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.domain.Order.ShippedOrdersCannotBeChangedException;
import it.gabrieletondi.telldontaskkata.domain.status.Approved;
import it.gabrieletondi.telldontaskkata.domain.status.Rejected;
import it.gabrieletondi.telldontaskkata.domain.status.Shipped;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import org.junit.jupiter.api.Test;

public class OrderApprovalUseCaseTest {
  private final TestOrderRepository orderRepository = new TestOrderRepository();
  private final OrderApprovalUseCase useCase = new OrderApprovalUseCase(orderRepository);

  @Test
  public void approvedExistingOrder() throws Exception {
    Order initialOrder = new Order(1);
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, true);

    useCase.run(request);

    final Order savedOrder = orderRepository.getSavedOrder();
    assertThat(savedOrder.getStatus()).isEqualTo(new Approved());
  }

  @Test
  public void rejectedExistingOrder() throws Exception {
    Order initialOrder = new Order(1);
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, false);

    useCase.run(request);

    final Order savedOrder = orderRepository.getSavedOrder();
    assertThat(savedOrder.getStatus()).isEqualTo(new Rejected());
  }

  @Test
  public void cannotApproveRejectedOrder() throws Exception {
    Order initialOrder = new Order(1);
    initialOrder.setStatus(new Rejected());
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, true);

    assertThatThrownBy(() -> useCase.run(request))
        .isExactlyInstanceOf(RejectedOrderCannotBeApprovedException.class);
    assertThat(orderRepository.getSavedOrder()).isNull();
  }

  @Test
  public void cannotRejectApprovedOrder() throws Exception {
    Order initialOrder = new Order(1);
    initialOrder.setStatus(new Approved());
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, false);

    assertThatThrownBy(() -> useCase.run(request))
        .isExactlyInstanceOf(ApprovedOrderCannotBeRejectedException.class);
    assertThat(orderRepository.getSavedOrder()).isNull();
  }

  @Test
  public void shippedOrdersCannotBeApproved() throws Exception {
    Order initialOrder = new Order(1);
    initialOrder.setStatus(new Shipped());
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, true);

    assertThatThrownBy(() -> useCase.run(request))
        .isExactlyInstanceOf(ShippedOrdersCannotBeChangedException.class);
    assertThat(orderRepository.getSavedOrder()).isNull();
  }

  @Test
  public void shippedOrdersCannotBeRejected() throws Exception {
    Order initialOrder = new Order(1);
    initialOrder.setStatus(new Shipped());
    orderRepository.addOrder(initialOrder);

    OrderApprovalUseCase.OrderApprovalRequest request =
        new OrderApprovalUseCase.OrderApprovalRequest(1, false);

    assertThatThrownBy(() -> useCase.run(request))
        .isExactlyInstanceOf(ShippedOrdersCannotBeChangedException.class);
    assertThat(orderRepository.getSavedOrder()).isNull();
  }
}
