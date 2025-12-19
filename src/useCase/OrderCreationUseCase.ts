import Order from '../domain/Order';
import Product from '../domain/Product';
import OrderRepository from '../repository/OrderRepository';
import { ProductCatalog } from '../repository/ProductCatalog';
import SellItemsRequest from './SellItemsRequest';
import UnknownProductException from './UnknownProductException';

class OrderCreationUseCase {
  private readonly orderRepository: OrderRepository;
  private readonly productCatalog: ProductCatalog;

  public constructor(orderRepository: OrderRepository, productCatalog: ProductCatalog) {
    this.orderRepository = orderRepository;
    this.productCatalog = productCatalog;
  }

  public run(request: SellItemsRequest): void {
    const order: Order = Order.create(1);

    for (const itemRequest of request.getRequests()) {
      const product: Product = this.productCatalog.getByName(itemRequest.getProductName());

      if (product === undefined) {
        throw new UnknownProductException();
      }

      order.addProduct(product, itemRequest.getQuantity());
    }

    this.orderRepository.save(order);
  }
}

export default OrderCreationUseCase;
