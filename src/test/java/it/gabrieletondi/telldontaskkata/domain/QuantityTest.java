package it.gabrieletondi.telldontaskkata.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class QuantityTest {

  @Test
  void quantityOfOne() {
    assertThat(Quantity.valueOf(1).value()).isEqualTo(BigDecimal.valueOf(1));
  }

  @Test
  void quantityOfZero() {
    assertThatExceptionOfType(Quantity.InvalidQuantityException.class)
        .isThrownBy(() -> Quantity.valueOf(0));
  }

  @Test
  void negativeQuantity() {
    assertThatExceptionOfType(Quantity.InvalidQuantityException.class)
        .isThrownBy(() -> Quantity.valueOf(-1));
  }
}
