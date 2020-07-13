package com.tnt.aggregator.mq;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.tnt.aggregator.event.Publisher;
import com.tnt.aggregator.rest.Exception.ApiException;
import com.tnt.aggregator.rest.model.Details;
import com.tnt.aggregator.rest.model.Pricing;
import com.tnt.aggregator.rest.model.Shipments;
import com.tnt.aggregator.rest.model.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * MQ Receiver class to fetch messages from Queue and process
 * Reads the HashMap message and splits the message according to keys ("pricing","track","shipments")
 * Adds the list of strings from each key to a new Queue object
 * Once the size of queue has reached 5 , drain the queue to list and invoke the respective APIs and get the response.
 * Publish event once the response is received from the respective APIs
 */
@Component
public class Receiver {

    @Value("${track.service.endpoint}")
    private String track_api_url;
    @Value("${pricing.service.endpoint}")
    private String pricing_api_url;
    @Value("${shipments.service.endpoint}")
    private String shipments_api_url;
    public static final String DELIMITER=",";

    private  RestTemplate restTemplate=new RestTemplate();


    BlockingQueue trackQueue=new LinkedBlockingQueue();
    BlockingQueue shipQueue=new LinkedBlockingQueue();
    BlockingQueue priceQueue=new LinkedBlockingQueue();

    private final Publisher responsePublisher;

    /**
     * Autowiring Constructor to inject  dependencies
     * @param responsePublisher responsePublisher to publish response once received from
     *                          respective APIs
     */

    public Receiver(Publisher responsePublisher)
    {
        this.responsePublisher=responsePublisher;
    }


    /**
     *Method to fetch message from Queue and process
     * @param message HashMap with key as string and values as List of String from input
     */
    public void receiveMessage(HashMap<String,List<String>> message) {
        Track trackRsp=null;
        Pricing priceRsp=null;
        Shipments shipRsp=null;
        Track track=null;
        Details response=new Details();
        if(message!=null) {
            //get value from HashMap message
            List<String> pricingList = message.get("pricing");
            List<String> trackList =trackList = message.get("track");
            List<String> shipmentsList = message.get("shipments");

            //Add list of string to queue
                trackQueue.addAll(trackList);
                shipQueue.addAll(shipmentsList);
                priceQueue.addAll(pricingList);



            //Invoke Respective APIs when count has reached 5
            if (trackQueue.size() >= 5) {
                    trackQueue.drainTo(trackList,5);
                    trackRsp = getTrackingDetails(trackList);
            }

            if (priceQueue.size() >= 5) {
                priceQueue.drainTo(pricingList,5);
                priceRsp = getPricingDetails(pricingList);

            }
            if (shipQueue.size() >= 5)
            {
                    shipQueue.drainTo(shipmentsList, 5);
                    shipRsp = getShipmentDetails(shipmentsList);
            }


            if(trackRsp!=null&&priceRsp!=null&&shipRsp!=null)
            {
                    response.setPricing(priceRsp);
                    response.setTrack(trackRsp);
                    response.setShipments(shipRsp);
                    //publish event when response is received
                    responsePublisher.publishChange(response);

            }else
            {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR," call to one/all api failed");
            }

        }
    }


    /**
     * method to invoke tracking api
     * @param track list of track ids
     * @return Track object - response from API mapped to Track object
     */

    private Track getTrackingDetails(List<String> track)
    {
        String idsAsParam = track.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(DELIMITER));
            ResponseEntity<Map> responseEntity=restTemplate.getForEntity(track_api_url+"q=" + idsAsParam,
                    Map.class);
            Map response = responseEntity.getBody();
            Track trackObj=new Track();
            trackObj.putAll(response);
            return trackObj;
    }
    /**
     * method to invoke pricing api
     * @param pricing list of price ids
     * @return Pricing object - response from API mapped to Pricing object
     */
    public Pricing getPricingDetails(List<String> pricing)
    {
        String idsAsParam = pricing.stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
        ResponseEntity<Map> responseEntity=restTemplate.getForEntity(pricing_api_url+"q=" + idsAsParam,
                Map.class);
        Map response = responseEntity.getBody();
        Pricing pricingObj=new Pricing();
        pricingObj.putAll(response);
        return  pricingObj;
    }
    /**
     * method to invoke Shipments api
     * @param shipments list of Shipments ids
     * @return Shipments object - response from API mapped to Shipments object
     */
    public Shipments getShipmentDetails(List<String> shipments)
    {
        Shipments shipmentsObj=new Shipments();
        String idsAsParam = shipments.stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
        ResponseEntity<Map> responseEntity=restTemplate.getForEntity(shipments_api_url+"q=" + idsAsParam,
                Map.class);
        Map response = responseEntity.getBody();
        shipmentsObj.putAll(response);
        return  shipmentsObj;
    }

}