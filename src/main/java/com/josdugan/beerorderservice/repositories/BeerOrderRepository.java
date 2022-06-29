package com.josdugan.beerorderservice.repositories;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerorderservice.domain.BeerOrderStatusEnum;
import com.josdugan.beerorderservice.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(BeerOrderStatusEnum beerOrderStatusEnum);
}
