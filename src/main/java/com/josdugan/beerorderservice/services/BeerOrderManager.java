package com.josdugan.beerorderservice.services;

import com.josdugan.beerorderservice.domain.BeerOrder;

public interface BeerOrderManager {

    String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
