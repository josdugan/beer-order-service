package com.josdugan.beerorderservice.services;

import com.josdugan.beerorderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);
}
