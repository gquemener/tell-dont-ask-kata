import Product from './Product';

class OrderItem {
  private readonly tax: number
  private readonly taxedAmount: number

  constructor(private readonly product: Product, private readonly quantity: number) {
    this.taxedAmount = Math.round(product.getUnitaryTaxedAmount() * quantity * 100) / 100;
    this.tax = product.getUnitaryTax() * quantity;
  }

  public getProduct(): Product {
    return this.product;
  }

  public getQuantity(): number {
    return this.quantity;
  }

  public getVATIncludedAmount(): number {
    return this.taxedAmount;
  }

  public getTax(): number {
    return this.tax;
  }
}

export default OrderItem;
