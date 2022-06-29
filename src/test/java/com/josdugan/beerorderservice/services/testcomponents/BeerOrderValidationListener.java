package com.josdugan.beerorderservice.services.testcomponents;

import com.josdugan.beerworkscommon.constants.MessageQueues;
import com.josdugan.beerworkscommon.events.ValidateOrderRequest;
import com.josdugan.beerworkscommon.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    public static final String FAIL_VALIDATION = "fail-validation";
    public static final String DONT_VALIDATE = "dont-validate";

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = MessageQueues.VALIDATE_ORDER_QUEUE)
    public void listen(Message msg) {
        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        if (request.getBeerOrderDto().getCustomerRef() != null &&
                request.getBeerOrderDto().getCustomerRef().equals(FAIL_VALIDATION)) {
            isValid = false;
        } else if (request.getBeerOrderDto().getCustomerRef() != null &&
                    request.getBeerOrderDto().getCustomerRef().equals(DONT_VALIDATE)) {
            sendResponse = false;
        }

        if (sendResponse) {
            jmsTemplate.convertAndSend(MessageQueues.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(isValid)
                            .orderId(request.getBeerOrderDto().getId())
                            .build());
        }
    }
}
