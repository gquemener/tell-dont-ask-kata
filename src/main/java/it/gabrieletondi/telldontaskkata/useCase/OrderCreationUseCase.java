package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.OrderItem.UnknownProductException;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import java.util.List;

public class OrderCreationUseCase {
  private final OrderRepository orderRepository;
  private final ProductCatalog productCatalog;

  public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
    this.orderRepository = orderRepository;
    this.productCatalog = productCatalog;
  }

  public void run(SellItemsRequest request) {
    Order order = new Order(request.orderId());
    for (SellItemRequest itemRequest : request.requests()) {
      Product product = productCatalog.getByName(itemRequest.productName());

      if (product == null) {
        throw new UnknownProductException();
      }

      order.addItem(new OrderItem(itemRequest.quantity(), product));
    }

    orderRepository.save(order);
  }

  public record SellItemRequest(int quantity, String productName) {}

  public record SellItemsRequest(int orderId, List<SellItemRequest> requests) {}
}
