package com.tnt.aggregator.rest;

import com.tnt.aggregator.rest.model.Details;
import com.tnt.aggregator.rest.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;




import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc //need this in Spring Boot test
public class DetailsApiTest {


    @Autowired
    private MockMvc mockMvc;

    public static final String uri="http://localhost:8081/aggregation?";

    String shipmentsParam=null;
    String trackParam=null;
    String priceParam=null;
    @Before
    public void setUp()
    {

        List<String> shipList= Stream.of("12","56","66","33","12").collect(Collectors.toList());

        List<String> priceList= Stream.of("12","56","66","33","12").collect(Collectors.toList());
        List<String> trackList= Stream.of("NL","CN","IN","UK","IT").collect(Collectors.toList());
        shipmentsParam = shipList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        trackParam = trackList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));

        priceParam = priceList.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    @Test
    public void testDetailsApi() throws Exception {
        Details response=new Details();
        Track track=new Track();
        track.put("123446787","NEW");
        response.setTrack(track);

        mockMvc.perform(get("/aggregation?")
                .param("pricing", priceParam)
                .param("track", trackParam)
                .param("shipments", shipmentsParam)
        ).andDo(print())
                .andExpect(status().isOk());

    }
}
