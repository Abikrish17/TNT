package com.tnt.aggregator.mq;

import com.tnt.aggregator.rest.Exception.ApiException;
import com.tnt.aggregator.rest.model.Details;
import com.tnt.aggregator.rest.model.Track;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.soap.Detail;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Sender class to send/put message to Queue to be picked up for processing
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate template;

    @Value("${tnt.rabbitmq.exchange}")
    private String exchange;

    @Value("${tnt.rabbitmq.routingkey}")
    private String routingkey;

    /**
     * Method to dsend messages to queue for processing
     * @param input input message to queue
     */
    public void send(HashMap<String, List<String>> input)throws ApiException {
      template.convertAndSend(exchange, routingkey,input);
    }
}
