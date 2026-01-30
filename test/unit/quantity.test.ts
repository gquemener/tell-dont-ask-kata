describe("Quantity", () => {
  it('is not negative', () => {
    expect(() => Quantity.of(-1)).toThrow("Quantity cannot be negative")
  })

  it('cannot be null', () => {
    expect(() => Quantity.of(0)).toThrow("Quantity cannot be null")
  })

  it.each(Array.from({length: 100}, (v, k) => k + 1))('holds value %d', (v: number) => {
    expect(Quantity.of(v).value).toBe(v)
  })

  it('is not higher than 100', () => {
    expect(() => Quantity.of(101)).toThrow("Quantity cannot be higher than 100")
  })
})

class Quantity {
  constructor(public readonly value: number) {
    if (this.value < 0) {
      throw new Error("Quantity cannot be negative")
    }
    if (this.value === 0) {
      throw new Error("Quantity cannot be null")
    }
    if (this.value > 100) {
      throw new Error("Quantity cannot be higher than 100")
    }
  }

  public static of(value: number): Quantity {
    return new Quantity(value);
  }
}
