package it.gabrieletondi.telldontaskkata.useCase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Money;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem.UnknownProductException;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.domain.Quantity;
import it.gabrieletondi.telldontaskkata.domain.status.Created;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class OrderCreationUseCaseTest {
  private final TestOrderRepository orderRepository = new TestOrderRepository();
  private Category food =
      new Category() {
        {
          setName("food");
          setTaxPercentage(new BigDecimal("10"));
        }
      };
  ;
  private final ProductCatalog productCatalog =
      new InMemoryProductCatalog(
          Arrays.<Product>asList(
              new Product() {
                {
                  setName("salad");
                  setPriceWithoutTax(Money.valueOf(new BigDecimal("3.56")));
                  setCategory(food);
                }
              },
              new Product() {
                {
                  setName("tomato");
                  setPriceWithoutTax(Money.valueOf(new BigDecimal("4.65")));
                  setCategory(food);
                }
              }));
  private final OrderCreationUseCase useCase =
      new OrderCreationUseCase(orderRepository, productCatalog);

  @Test
  public void sellMultipleItems() throws Exception {
    OrderCreationUseCase.SellItemRequest saladRequest =
        new OrderCreationUseCase.SellItemRequest(Quantity.valueOf(2), "salad");
    OrderCreationUseCase.SellItemRequest tomatoRequest =
        new OrderCreationUseCase.SellItemRequest(Quantity.valueOf(3), "tomato");

    final int orderId = 13;
    final OrderCreationUseCase.SellItemsRequest request =
        new OrderCreationUseCase.SellItemsRequest(orderId, List.of(saladRequest, tomatoRequest));

    useCase.run(request);

    final Order insertedOrder = orderRepository.getSavedOrder();
    assertThat(insertedOrder.getId()).isEqualTo(orderId);
    assertThat(insertedOrder.getStatus()).isEqualTo(new Created());
    assertThat(insertedOrder.getTotal()).isEqualTo(Money.valueOf(new BigDecimal("23.20")));
    assertThat(insertedOrder.getTax()).isEqualTo(Money.valueOf(new BigDecimal("2.13")));
    assertThat(insertedOrder.getCurrency()).isEqualTo("EUR");
    assertThat(insertedOrder.getItems()).hasSize(2);
    assertThat(insertedOrder.getItems().get(0).product().getName()).isEqualTo("salad");
    assertThat(insertedOrder.getItems().get(0).product().getPriceWithoutTax())
        .isEqualTo(Money.valueOf(new BigDecimal("3.56")));
    assertThat(insertedOrder.getItems().get(0).quantity()).isEqualTo(Quantity.valueOf(2));
    assertThat(insertedOrder.getItems().get(0).getTaxedAmount())
        .isEqualTo(Money.valueOf(new BigDecimal("7.84")));
    assertThat(insertedOrder.getItems().get(0).getTax())
        .isEqualTo(Money.valueOf(new BigDecimal("0.72")));
    assertThat(insertedOrder.getItems().get(1).product().getName()).isEqualTo("tomato");
    assertThat(insertedOrder.getItems().get(1).product().getPriceWithoutTax())
        .isEqualTo(Money.valueOf(new BigDecimal("4.65")));
    assertThat(insertedOrder.getItems().get(1).quantity()).isEqualTo(Quantity.valueOf(3));
    assertThat(insertedOrder.getItems().get(1).getTaxedAmount())
        .isEqualTo(Money.valueOf(new BigDecimal("15.36")));
    assertThat(insertedOrder.getItems().get(1).getTax())
        .isEqualTo(Money.valueOf(new BigDecimal("1.41")));
  }

  @Test
  public void unknownProduct() throws Exception {
    OrderCreationUseCase.SellItemsRequest request =
        new OrderCreationUseCase.SellItemsRequest(
            1,
            List.of(
                new OrderCreationUseCase.SellItemRequest(Quantity.valueOf(1), "unknown product")));
    assertThatThrownBy(() -> useCase.run(request))
        .isExactlyInstanceOf(UnknownProductException.class);
  }
}
