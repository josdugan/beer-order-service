package com.josdugan.beerorderservice.mappers;

import com.josdugan.beerorderservice.domain.BeerOrderLine;
import com.josdugan.beerworkscommon.dtos.BeerOrderLineDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
