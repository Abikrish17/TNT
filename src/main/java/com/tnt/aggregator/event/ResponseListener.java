package com.tnt.aggregator.event;

import com.tnt.aggregator.rest.model.Details;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener class to listen to changes
 */
@Component
public class ResponseListener {

    private Details response;

    public Details getResponse() {
        return response;
    }

    public void setResponse(Details response) {
        this.response = response;
    }

    /**
     * Event listener to subscribe to changes
     * @param event request event when changes occur
     * @return response of the changed event
     */
    @EventListener
    public Details returnAPIResponse(DetailsResponseEvent event)
    {
        setResponse(event.getDetails());
        return event.getDetails();
    }
}
