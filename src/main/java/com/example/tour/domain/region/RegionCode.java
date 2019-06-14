package com.example.tour.domain.region;

import com.example.tour.infrastructure.remote.openAPI.OpenAPI;
import com.example.tour.infrastructure.remote.openAPI.OpenAPIResponseDTO;
import com.example.tour.infrastructure.remote.openAPI.response.OpenAPIItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegionCode implements InitializingBean {

    private final String[] regionalLocalGovernmentList = new String[]{
        "서울특별시",
        "인천광역시",
        "대전광역시",
        "대구광역시",
        "광주광역시",
        "부산광역시",
        "울산광역시",
        "경기도",
        "강원도",
        "충청북도",
        "충청남도",
        "경상북도",
        "경상남도",
        "전라북도",
        "전라남도",
        "세종특별자치시",
        "제주특별자치도"
    };

    private final OpenAPI openAPI;
    @Getter
    private Map<String, List<OpenAPIItem>> regionCodeMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        regionCodeMap = new HashMap<>();
        initRegionCode();
    }

    private void initRegionCode() {
        for (String city : regionalLocalGovernmentList) {
            OpenAPIResponseDTO regionCode = openAPI.getRegionCode(city, 100);
            regionCodeMap.put(city, regionCode.getResponse().getBody().getItems().getItemList());
        }
    }
}
