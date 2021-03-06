package com.josdugan.beerorderservice.web.model;

import com.josdugan.beerworkscommon.dtos.BaseItem;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDto extends BaseItem {

    private String customerName;

    @Builder
    public CustomerDto(UUID id,
                       Integer version,
                       OffsetDateTime createdDate,
                       OffsetDateTime lastModifiedDate,
                       String customerName) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerName = customerName;
    }
}
