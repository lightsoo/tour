package com.example.tour.domain.tour;

import com.example.tour.domain.region.ServiceRegion;
import com.example.tour.domain.region.ServiceRegionParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceRegionParserTest {
    @Autowired
    private ServiceRegionParser serviceRegionParser;

    @Test
    public void parse() {
        List<ServiceRegion> sokchoList1 = serviceRegionParser.parse("강원도 속초");
        assertEquals(sokchoList1.size(), 1);
        List<ServiceRegion> sokchoList2 = serviceRegionParser.parse("강원도 속초시");
        assertEquals(sokchoList2.size(), 1);

        List<ServiceRegion> gangwondo = serviceRegionParser.parse("강원도 오대산국립공원");
        assertEquals(gangwondo.size(), 1);
    }

    @Test
    public void parse__if__serviceRegion__is__more__than__one() {
        List<ServiceRegion> serviceRegionList = serviceRegionParser.parse("강원도 속초, 양양, 고성");
        assertEquals(serviceRegionList.size(), 3);
    }

    @Test(expected = RuntimeException.class)
    public void parse__unknown__region() {
        serviceRegionParser.parse("한양");
    }
}