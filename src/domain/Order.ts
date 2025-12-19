import OrderItem from './OrderItem';
import { OrderStatus } from './OrderStatus';
import Product from './Product';

class Order {
  private total: number;
  private currency: string;
  private items: OrderItem[];
  private tax: number;
  private status: OrderStatus;
  private id: number;

  static create(id: number): Order {
    const order: Order = new Order();
    order.id = id;
    order.status = OrderStatus.CREATED;
    order.items = []
    order.currency = 'EUR';
    order.total = 0;
    order.tax = 0;

    return order;
  }

  /**
  * @deprecated
  */
  public getTotal(): number {
    return this.total;
  }

  /**
  * @deprecated
  */
  public setTotal(total: number): void {
    this.total = total;
  }

  /**
  * @deprecated
  */
  public getCurrency(): string {
    return this.currency;
  }

  /**
  * @deprecated
  */
  public setCurrency(currency: string): void {
    this.currency = currency;
  }

  /**
  * @deprecated
  */
  public getItems(): OrderItem[] {
    return this.items;
  }

  /**
  * @deprecated
  */
  public setItems(items: OrderItem[]): void {
    this.items = items;
  }

  /**
  * @deprecated
  */
  public getTax(): number {
    return this.tax;
  }

  /**
  * @deprecated
  */
  public setTax(tax: number): void {
    this.tax = tax;
  }

  /**
  * @deprecated
  */
  public getStatus(): OrderStatus {
    return this.status;
  }

  /**
  * @deprecated
  */
  public setStatus(status: OrderStatus): void {
    this.status = status;
  }

  /**
  * @deprecated
  */
  public getId(): number {
    return this.id;
  }

  /**
  * @deprecated
  */
  public setId(id: number): void {
    this.id = id;
  }

  public addProduct(product: Product, quantity: number): void {
    const unitaryTax: number = Math.round(product.getPrice() / 100 * product.getCategory().getTaxPercentage() * 100) / 100;
    const unitaryTaxedAmount: number = Math.round((product.getPrice() + unitaryTax) * 100) / 100;
    const taxedAmount: number = Math.round(unitaryTaxedAmount * quantity * 100) / 100;
    const taxAmount: number = unitaryTax * quantity;

    const orderItem: OrderItem = new OrderItem();
    orderItem.setProduct(product);
    orderItem.setQuantity(quantity);
    orderItem.setTax(taxAmount);
    orderItem.setTaxedAmount(taxedAmount);
    this.items.push(orderItem);

    this.total += taxedAmount;
    this.tax += taxAmount;
  }
}

export default Order;

