package com.tnt.aggregator.event;

import com.tnt.aggregator.rest.model.Details;
import org.springframework.context.ApplicationEvent;

/**
 * DetailsResponseEvent to set  events
 */

public class DetailsResponseEvent extends ApplicationEvent {

    private Details details;

    /**
     * Constructor to initialize params
     * @param details response object
     * @param source source object
     */
    public DetailsResponseEvent(Details details, Object source)
    {
        super(source);
        this.details=details;
    }

    /**
     * @return details object
     *
     */
    public Details getDetails() {
        return this.details;
    }
}
