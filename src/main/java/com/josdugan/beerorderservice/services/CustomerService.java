package com.josdugan.beerorderservice.services;

import com.josdugan.beerorderservice.web.model.CustomerPagedList;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerPagedList listCustomers(Pageable pageable);
}
