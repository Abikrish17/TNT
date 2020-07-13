package com.tnt.aggregator.event;

import com.tnt.aggregator.rest.model.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Class to publish events when notified
 */
@Component
public class Publisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * publish changes when notified
     * @param response response object to be published
     */
    public void publishChange(Details response) {
        DetailsResponseEvent pce = new DetailsResponseEvent(response, this);
        this.publisher.publishEvent(pce);
    }
}
