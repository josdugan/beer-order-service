package com.josdugan.beerorderservice.bootstrap;

import com.josdugan.beerorderservice.domain.Customer;
import com.josdugan.beerorderservice.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerOrderBootstrap  implements CommandLineRunner {

    public static final String BEER_1_UPC = "012345678911";
    public static final String BEER_2_UPC = "012345678922";
    public static final String BEER_3_UPC = "012345678933";
    public static final String TASTING_ROOM = "Tasting Room";

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .customerName(TASTING_ROOM)
                    .apiKey(UUID.randomUUID())
                    .build());
        }
    }
}
