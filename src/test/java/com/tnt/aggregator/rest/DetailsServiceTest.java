package com.tnt.aggregator.rest;

import com.tnt.aggregator.event.ResponseListener;
import com.tnt.aggregator.mq.Sender;
import com.tnt.aggregator.rest.api.DetailsApi;
import com.tnt.aggregator.rest.api.DetailsService;
import com.tnt.aggregator.rest.api.DetailsServiceImpl;
import com.tnt.aggregator.rest.model.Details;
import com.tnt.aggregator.rest.model.Pricing;
import com.tnt.aggregator.rest.model.Shipments;
import com.tnt.aggregator.rest.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
public class DetailsServiceTest {
    @InjectMocks
    DetailsServiceImpl service;


    @Mock
    Sender sender;

    @Mock
    ResponseListener listener;

    HashMap<String, List<String>> input= new HashMap<>();

    List<String> trackList;
    List<String> priceList;
    List<String> shipList;
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
         trackList= Stream.of("123456789", "4567890123")
                .collect(Collectors.toList());

        priceList= Stream.of("NL", "CN")
                .collect(Collectors.toList());

       shipList= Stream.of("123456789", "4567890123")
                .collect(Collectors.toList());
        input.put("track",trackList);
        input.put("pricing",priceList);
        input.put("shipments",shipList);
    }
@Test
    public void testGetPackageDetails()
    {
        Details response=new Details();
        Pricing price=new Pricing();
        Shipments ship=new Shipments();
        Track track=new Track();

        price.put("NL",23.9);
        price.put("CN",29.9);
        price.put("BP",99.9);
        price.put("IN",23.9);
        price.put("US",23.9);

        List<String> shipRspList= Stream.of("envelope",
                "box",
                "envelope",
                "box",
                "pallet",
                "pallet",
                "pallet")
                .collect(Collectors.toList());

        ship.put("123456787",shipRspList);

        track.put("34454545","NEW");

        response.setShipments(ship);
        response.setPricing(price);
        response.setTrack(track);
        doNothing().when(sender).send(input);
        sender.send(input);
        when(listener.getResponse()).thenReturn(response);
       Details serviceRsp= service.consolidateDetails(priceList,trackList,shipList);
       assertNotNull(serviceRsp);

    }
}
