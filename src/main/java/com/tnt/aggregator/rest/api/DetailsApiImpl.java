package com.tnt.aggregator.rest.api;

import com.tnt.aggregator.rest.Exception.ApiException;
import com.tnt.aggregator.rest.model.Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class has the implementation details of aggregator API
 * Aggregator API makes calls to 3 different APIs to fetch the details and
 * returns the consolidated response to the user
 */
@Controller
public class DetailsApiImpl implements DetailsApi {
    private DetailsService detailsService;

    /**
     * Autowiring constructor to inject dependencies
     * @param detailsService
     */
    @Autowired
    public DetailsApiImpl(DetailsService detailsService)
    {
        this.detailsService=detailsService;
    }
    /**
     *
     * @param pricing list of ISO country codes .EX: "NL","IN" etc to get pricing details from Pricing API
     * @param track list  9 digit order numbers to get status from tracking API
     * @param shipments list of 9 digit order numbers to get product details from Shipments API
     * @return Details response which has consolidated response returned from  APIs mentioned in input params
     */
     public ResponseEntity<Details> getPackageDetails(List<String> pricing,List<String> track,List<String> shipments) throws ApiException {
        Details response= detailsService.consolidateDetails(pricing,track,shipments);
        return new ResponseEntity<Details>(response,HttpStatus.OK);
    }
}
