package com.josdugan.beerorderservice.domain;

public enum BeerOrderStatusEnum {
    NEW, VALIDATED, VALIDATION_PENDING, VALIDATION_EXCEPTION, ALLOCATED, ALLOCATION_PENDING,
    ALLOCATION_EXCEPTION, PENDING_INVENTORY, PICKED_UP, DELIVERED, DELIVERY_EXCEPTION
}