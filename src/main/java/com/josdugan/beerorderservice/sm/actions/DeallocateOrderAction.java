package com.josdugan.beerorderservice.sm.actions;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerorderservice.domain.BeerOrderEventEnum;
import com.josdugan.beerorderservice.domain.BeerOrderStatusEnum;
import com.josdugan.beerorderservice.mappers.BeerOrderMapper;
import com.josdugan.beerorderservice.repositories.BeerOrderRepository;
import com.josdugan.beerorderservice.services.BeerOrderManager;
import com.josdugan.beerworkscommon.constants.MessageQueues;
import com.josdugan.beerworkscommon.events.DeallocateOrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManager.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(MessageQueues.DEALLOCATE_ORDER_QUEUE,
                    DeallocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
            log.debug("Sent deallocation request for order id: " + beerOrderId);
        }, () -> log.error("Beer order not found"));
    }
}
