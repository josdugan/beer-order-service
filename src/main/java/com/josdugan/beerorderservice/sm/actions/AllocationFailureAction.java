package com.josdugan.beerorderservice.sm.actions;

import com.josdugan.beerorderservice.domain.BeerOrderEventEnum;
import com.josdugan.beerorderservice.domain.BeerOrderStatusEnum;
import com.josdugan.beerorderservice.services.BeerOrderManager;
import com.josdugan.beerworkscommon.constants.MessageQueues;
import com.josdugan.beerworkscommon.events.AllocationFailureEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManager.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(MessageQueues.ALLOCATION_FAILURE_QUEUE, AllocationFailureEvent.builder()
                .orderId(UUID.fromString(beerOrderId))
                .build());

        log.debug("Sent allocation failure message to queue for order id: " + beerOrderId);
    }
}
