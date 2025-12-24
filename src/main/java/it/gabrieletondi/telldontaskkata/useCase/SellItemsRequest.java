package it.gabrieletondi.telldontaskkata.useCase;

import java.util.List;

public record SellItemsRequest(List<SellItemRequest> requests) {}
