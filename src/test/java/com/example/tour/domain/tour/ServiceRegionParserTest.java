package com.example.tour.domain.tour;

import com.example.tour.domain.region.ServiceRegionParser;
import com.example.tour.exception.NotFoundServiceRegionException;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
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
    public void parse__serviceRegion() {
        List<ServiceRegion> sokchoList1 = serviceRegionParser.parse("강원도 속초");
        assertEquals(1, sokchoList1.size());
        assertEquals(sokchoList1.get(0).getGugunName(), "속초시");

        List<ServiceRegion> sokchoList2 = serviceRegionParser.parse("강원도 속초시");
        assertEquals(1, sokchoList2.size());
        assertEquals(sokchoList2.get(0).getGugunName(), "속초시");

        List<ServiceRegion> gangwondo = serviceRegionParser.parse("강원도 오대산국립공원");
        assertEquals(1, gangwondo.size());
        assertEquals("강원도", gangwondo.get(0).getGugunName());

        List<ServiceRegion> gangwondo2 = serviceRegionParser.parse("강원도 원주시 소초면 학곡리 900번지");
        assertEquals(1, gangwondo2.size());
        assertEquals("원주시", gangwondo2.get(0).getGugunName());

        List<ServiceRegion> gangwondo3 = serviceRegionParser.parse("강원도");
        assertEquals(1, gangwondo3.size());
        assertEquals("강원도", gangwondo3.get(0).getGugunName());
    }

    @Test
    public void parse__if__serviceRegion__is__more__than__one() {
        List<ServiceRegion> serviceRegionList = serviceRegionParser.parse("강원도 속초, 양양, 고성");

        assertEquals(serviceRegionList.size(), 3);
    }

    @Test(expected = NotFoundServiceRegionException.class)
    public void parse__empty__region() {
        serviceRegionParser.parse("");
    }

    @Test(expected = NotFoundServiceRegionException.class)
    public void parse__unknown__region() {
        serviceRegionParser.parse("한양");
    }
}