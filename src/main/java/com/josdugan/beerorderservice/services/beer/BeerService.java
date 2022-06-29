package com.josdugan.beerorderservice.services.beer;


import com.josdugan.beerworkscommon.dtos.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    String BEER_PATH_V1 = "/api/v1/beer/";
    String BEER_UPC_PATH_V1 = "/api/v1/beerUpc/";

    Optional<BeerDto> getBeerById(UUID uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}
