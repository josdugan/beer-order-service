package com.josdugan.beerorderservice.services.testcomponents;

import com.josdugan.beerworkscommon.constants.MessageQueues;
import com.josdugan.beerworkscommon.events.AllocateOrderRequest;
import com.josdugan.beerworkscommon.events.AllocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {

    public static final String FAIL_ALLOCATION = "fail-allocation";
    public static final String PARTIAL_ALLOCATION = "partial-allocation";
    public static final String DONT_ALLOCATE = "dont-allocate";

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = MessageQueues.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg) {
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();
        boolean pendingInventory = false;
        boolean allocationError = false;
        boolean sendResponse = true;

        String customerRef = request.getBeerOrderDto().getCustomerRef();
        if (customerRef != null) {
            if (customerRef.equals(PARTIAL_ALLOCATION)) {
                pendingInventory = true;
            }

            if (customerRef.equals(FAIL_ALLOCATION)) {
                allocationError = true;
            } else if (customerRef.equals(DONT_ALLOCATE)) {
                sendResponse = false;
            }
        }

        boolean finalPendingInventory = pendingInventory;
        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

        if (sendResponse) {
            jmsTemplate.convertAndSend(MessageQueues.ALLOCATE_ORDER_RESPONSE_QUEUE,
                    AllocateOrderResult.builder()
                            .beerOrderDto(request.getBeerOrderDto())
                            .pendingInventory(pendingInventory)
                            .allocationError(allocationError)
                            .build());
        }
    }
}
