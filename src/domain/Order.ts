import OrderItem from './OrderItem';
import {OrderStatus} from './OrderStatus';

class Order {
  private readonly currency: string = 'EUR';
  private readonly items: OrderItem[] = [];
  private status: OrderStatus = OrderStatus.CREATED;
  private id: number;

  public getTotal(): number {
    return this.items.reduce((acc, item) => acc + item.getVATIncludedAmount(), 0)
  }

  public getCurrency(): string {
    return this.currency;
  }

  public getItems(): OrderItem[] {
    return this.items;
  }

  public getTax(): number {
    return this.items.reduce((acc, item) => acc + item.getTax(), 0)
  }

  public getStatus(): OrderStatus {
    return this.status;
  }

  public setStatus(status: OrderStatus): void {
    this.status = status;
  }

  public getId(): number {
    return this.id;
  }

  public setId(id: number): void {
    this.id = id;
  }

  addItem(orderItem: OrderItem) {
    this.items.push(orderItem);
  }

}

export default Order;

