package it.gabrieletondi.telldontaskkata.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import it.gabrieletondi.telldontaskkata.domain.Money.InvalidMoneyException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;

public class MoneyProperties {

  @Property
  void hasCentsAndCurrency(@ForAll @BigRange(min = "0.00") BigDecimal a) {
    assertThat(Money.valueOf(a).value()).isEqualTo(a.setScale(2, RoundingMode.HALF_UP));
  }

  @Property
  void negativeValueIsInvalid(
      @ForAll @BigRange(max = "0.00", maxIncluded = false) BigDecimal value) {
    assertThatExceptionOfType(InvalidMoneyException.class).isThrownBy(() -> Money.valueOf(value));
  }

  @Property
  void canBeMultipliedByOne(@ForAll @BigRange(min = "0.00") BigDecimal moneyValue) {
    assertThat(Money.valueOf(moneyValue).multiply(Quantity.valueOf(1)).value())
        .isEqualTo(moneyValue);
  }
}
