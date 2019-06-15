package com.example.tour.domain.region;

import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import com.example.tour.infrastructure.hibernate.region.ServiceRegionRepository;
import com.example.tour.infrastructure.remote.openAPI.response.OpenAPIItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegionCodeTest {
    @Autowired
    private RegionCode regionCode;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;

    @Test
    public void successToInitialize__service__region() {

        Map<String, List<OpenAPIItem>> regionCodeMap = regionCode.getRegionCodeMap();

        List<ServiceRegion> serviceRegionList = serviceRegionRepository.findAllByNameContains("서울");
        List<OpenAPIItem> openAPIItems = regionCodeMap.get("서울특별시");

        assertThat(openAPIItems.size()).isGreaterThan(0);
        assertThat(serviceRegionList.size()).isGreaterThan(0);

    }
}