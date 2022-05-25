package com.josdugan.beerorderservice.mappers;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerorderservice.web.model.BeerOrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto beerOrderDto);
}
