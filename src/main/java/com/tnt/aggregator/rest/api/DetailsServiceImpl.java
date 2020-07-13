package com.tnt.aggregator.rest.api;

import com.tnt.aggregator.event.DetailsResponseEvent;
import com.tnt.aggregator.event.ResponseListener;
import com.tnt.aggregator.mq.Sender;
import com.tnt.aggregator.rest.Exception.ApiException;
import com.tnt.aggregator.rest.model.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class has the business logic for Aggregator API.
 * Business logic to invoke multiple APIs like TrackAPI ,ShipmentsAPI, PricingAPI
 * Retrieve details from APIs and consolidate the response
 *
 */
@Component
public class DetailsServiceImpl  extends ResponseListener implements DetailsService {


    @Value("${track.service.endpoint}")
    private String track_api_url;
    @Value("${pricing.service.endpoint}")
    private String pricing_api_url;
    @Value("${shipments.service.endpoint}")
    private String shipments_api_url;

private boolean isReady = false;
private int counter = 1;
    private Sender sender;
    private boolean flag=false;

    ResponseListener listener;
    @EventListener
    public Details returnAPIResponse(DetailsResponseEvent event)
    {

        setResponse(event.getDetails());
        return event.getDetails();
    }
    @Autowired
    public DetailsServiceImpl(Sender sender,ResponseListener listener) {
        this.sender=sender;
        this.listener=listener;
    }
    /**
     * This method parses the input and makes calls to TrackAPI ,ShipmentsAPI, PricingAPI
     * @param pricing list of ISO country codes .EX: "NL","IN" etc to get pricing details from Pricing API
     * @param track list  9 digit order numbers to get status from tracking API
     * @param shipments list of 9 digit order numbers to get product details from Shipments API
     * @return
     */
    @Override
    public Details consolidateDetails(List<String> pricing, List<String> track, List<String> shipments) throws ApiException {
        HashMap<String,List<String>> map=new HashMap<>();
        Details response=new Details();
        map.put("pricing",pricing);
        map.put("track",track);
        map.put("shipments",shipments);

        sender.send(map);
        if(listener.getResponse()==null) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return listener.getResponse();

    }

}
