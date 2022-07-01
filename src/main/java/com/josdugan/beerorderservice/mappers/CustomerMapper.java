package com.josdugan.beerorderservice.mappers;

import com.josdugan.beerorderservice.domain.Customer;
import com.josdugan.beerorderservice.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(CustomerDto customerDto);
}
