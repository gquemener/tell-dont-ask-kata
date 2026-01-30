import Product from './Product';

class OrderItem {
  private tax: number
  private taxedAmount: number

  constructor(private product: Product, private quantity: number) {
    this.taxedAmount = Math.round(product.getUnitaryTaxedAmount() * quantity * 100) / 100;
    this.tax = product.getUnitaryTax() * quantity;
  }

  public getProduct(): Product {
    return this.product;
  }

  public setProduct(product: Product): void {
    this.product = product;
  }

  public getQuantity(): number {
    return this.quantity;
  }

  public setQuantity(quantity: number): void {
    this.quantity = quantity;
  }

  public getVATIncludedAmount(): number {
    return this.taxedAmount;
  }

  public setTaxedAmount(taxedAmount: number): void {
    this.taxedAmount = taxedAmount;
  }

  public getTax(): number {
    return this.tax;
  }

  public setTax(tax: number): void {
    this.tax = tax;
  }
}

export default OrderItem;

