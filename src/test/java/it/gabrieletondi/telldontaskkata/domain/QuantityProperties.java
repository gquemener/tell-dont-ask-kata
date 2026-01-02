package it.gabrieletondi.telldontaskkata.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

class QuantityProperties {

  @Property
  void quantityBetweenOneAndOneHundred(@ForAll @IntRange(min = 1, max = 100) Integer i) {
    assertThat(Quantity.valueOf(i).value()).isEqualTo(BigDecimal.valueOf(i));
  }

  @Property
  void quantityGreaterThanOneHundred(@ForAll @IntRange(min = 101) Integer i) {
    assertThatExceptionOfType(Quantity.InvalidQuantityException.class)
        .isThrownBy(() -> Quantity.valueOf(i));
  }

  @Test
  void quantityOfZero() {
    assertThatExceptionOfType(Quantity.InvalidQuantityException.class)
        .isThrownBy(() -> Quantity.valueOf(0));
  }

  @Property
  void negativeQuantity(@ForAll @IntRange(min = Integer.MIN_VALUE, max = -1) Integer i) {
    assertThatExceptionOfType(Quantity.InvalidQuantityException.class)
        .isThrownBy(() -> Quantity.valueOf(i));
  }
}
