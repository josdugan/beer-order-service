package com.josdugan.beerorderservice.sm.actions;

import com.josdugan.beerorderservice.domain.BeerOrder;
import com.josdugan.beerorderservice.domain.BeerOrderEventEnum;
import com.josdugan.beerorderservice.domain.BeerOrderStatusEnum;
import com.josdugan.beerorderservice.mappers.BeerOrderMapper;
import com.josdugan.beerorderservice.repositories.BeerOrderRepository;
import com.josdugan.beerorderservice.services.BeerOrderManager;
import com.josdugan.beerworkscommon.constants.MessageQueues;
import com.josdugan.beerworkscommon.events.ValidateOrderRequest;
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
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManager.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(MessageQueues.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                    .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                    .build());

            log.debug("Sent Validation request to queue for order id " + beerOrderId);

        }, () -> log.error("Order not found. Id: " + beerOrderId));
    }
}
