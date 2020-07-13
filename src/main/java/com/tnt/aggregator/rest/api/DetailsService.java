package com.tnt.aggregator.rest.api;

import com.tnt.aggregator.rest.Exception.ApiException;
import com.tnt.aggregator.rest.model.Details;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This interface has methods to be implemented by Service Impl class which has the business logic
 */
@Service
public interface DetailsService {
    /**
     *
     * @param pricing list of ISO country codes .EX: "NL","IN" etc to get pricing details from Pricing API
     * @param track list  9 digit order numbers to get status from tracking API
     * @param shipments list of 9 digit order numbers to get product details from Shipments API
     * @return Details response which has consolidated response returned from  APIs mentioned in input params
     */
    public Details consolidateDetails(List<String> pricing, List<String> track, List<String> shipments)throws ApiException;
}
