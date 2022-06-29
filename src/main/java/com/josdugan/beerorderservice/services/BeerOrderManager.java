package com.josdugan.beerorderservice.services;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerworkscommon.dtos.BeerOrderDto;

import java.util.UUID;

public interface BeerOrderManager {

    String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDto beerOrderDto);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);

    void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);

    void beerOrderPickedUp(UUID id);

    void cancelOrder(UUID id);
}
