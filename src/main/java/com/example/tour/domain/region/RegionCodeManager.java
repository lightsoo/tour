package com.example.tour.domain.region;

import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import com.example.tour.infrastructure.hibernate.region.ServiceRegionRepository;
import com.example.tour.infrastructure.remote.openAPI.OpenAPI;
import com.example.tour.infrastructure.remote.openAPI.OpenAPIResponseDTO;
import com.example.tour.infrastructure.remote.openAPI.response.OpenAPIItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RegionCodeManager implements InitializingBean {

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
    private final ServiceRegionRepository serviceRegionRepository;

    @Getter
    private Map<String, List<OpenAPIItem>> regionCodeMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        regionCodeMap = new HashMap<>();
        initRegionCode();
    }

    private void initRegionCode() {
        final String SUCCESS_CODE = "00";

        for (String city : regionalLocalGovernmentList) {
            OpenAPIResponseDTO regionCode = openAPI.getRegionCode(city, 100);
            if (SUCCESS_CODE.equals(regionCode.getResponse().getHeader().getResultCode())) {
                regionCodeMap.put(city, regionCode.getResponse().getBody().getItems().getItemList());
                saveServiceRegions(regionCode.getResponse().getBody().getItems().getItemList());
            }
        }
    }

    private void saveServiceRegions(List<OpenAPIItem> serviceRegionList) {
        if (!CollectionUtils.isEmpty(serviceRegionList)) {
            OpenAPIItem localGovernment = serviceRegionList.get(0);
            serviceRegionRepository.save(
                ServiceRegion.builder()
                             .code(localGovernment.getSidoCode())
                             .name(localGovernment.getSidoName())
                             .build()
            );
        }

        for (OpenAPIItem city : serviceRegionList) {
            serviceRegionRepository.save(
                ServiceRegion.builder()
                             .code(city.getGugunCode())
                             .name(city.getSidoName() + city.getGugunName())
                             .build()
            );
        }

    }
}
