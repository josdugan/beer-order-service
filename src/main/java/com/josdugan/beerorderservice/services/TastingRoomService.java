package com.josdugan.beerorderservice.services;

import com.josdugan.beerorderservice.bootstrap.BeerOrderBootstrap;
import com.josdugan.beerorderservice.domain.Customer;
import com.josdugan.beerorderservice.repositories.BeerOrderRepository;
import com.josdugan.beerorderservice.repositories.CustomerRepository;
import com.josdugan.beerworkscommon.dtos.BeerOrderDto;
import com.josdugan.beerworkscommon.dtos.BeerOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    private final BeerOrderRepository beerOrderRepository;
    private final List<String> beerUpcs = new ArrayList<>(3);

    public TastingRoomService(CustomerRepository customerRepository,
                              BeerOrderService beerOrderService,
                              BeerOrderRepository beerOrderRepository) {
        this.customerRepository = customerRepository;
        this.beerOrderService = beerOrderService;
        this.beerOrderRepository = beerOrderRepository;

        beerUpcs.add(BeerOrderBootstrap.BEER_1_UPC);
        beerUpcs.add(BeerOrderBootstrap.BEER_2_UPC);
        beerUpcs.add(BeerOrderBootstrap.BEER_3_UPC);
    }

    @Transactional
//    @Scheduled(fixedRate = 2000)
    public void placeTastingRoomOrder() {
        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(BeerOrderBootstrap.TASTING_ROOM);

        if (customerList.size() == 1) {
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few tasing room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String beerToOrder = getRandomBeerUpc();

        BeerOrderLineDto beerOrderLine = BeerOrderLineDto.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(6))
                .build();

        List<BeerOrderLineDto> beerOrderLines = new ArrayList<>();
        beerOrderLines.add(beerOrderLine);

        BeerOrderDto beerOrder = BeerOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .beerOrderLines(beerOrderLines)
                .build();

        BeerOrderDto savedOrder = beerOrderService.placeOrder(customer.getId(), beerOrder);
    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size() -0));
    }
}
