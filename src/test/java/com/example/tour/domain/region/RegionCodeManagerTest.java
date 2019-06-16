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
public class RegionCodeManagerTest {
    @Autowired
    private RegionCodeManager regionCodeManager;
    @Autowired
    private ServiceRegionRepository serviceRegionRepository;

    @Test
    public void success__initialize__service__region() {

        Map<String, List<OpenAPIItem>> regionCodeMap = regionCodeManager.getRegionCodeMap();

        List<ServiceRegion> serviceRegionList = serviceRegionRepository.findAllByNameContains("서울특별시");
        List<OpenAPIItem> openAPIItems = regionCodeMap.get("서울특별시");

        assertThat(openAPIItems.size()).isGreaterThan(0);
        assertThat(serviceRegionList.size()).isGreaterThan(0);

    }
}