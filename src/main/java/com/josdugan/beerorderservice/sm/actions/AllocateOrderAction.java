package com.josdugan.beerorderservice.sm.actions;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerorderservice.domain.BeerOrderEventEnum;
import com.josdugan.beerorderservice.domain.BeerOrderStatusEnum;
import com.josdugan.beerorderservice.mappers.BeerOrderMapper;
import com.josdugan.beerorderservice.repositories.BeerOrderRepository;
import com.josdugan.beerorderservice.services.BeerOrderManager;
import com.josdugan.beerworkscommon.constants.MessageQueues;
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
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManager.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(MessageQueues.ALLOCATE_ORDER_QUEUE,
                beerOrderMapper.beerOrderToDto(beerOrder));

        log.debug("Sent allocation request for order id: " + beerOrderId);
    }
}
