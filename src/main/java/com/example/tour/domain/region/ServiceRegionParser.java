package com.example.tour.domain.region;

import com.example.tour.infrastructure.remote.openAPI.response.OpenAPIItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceRegionParser {
    private final RegionCode regionCode;

    public List<ServiceRegion> parse(String serviceRegionName) {
        String[] splittedServiceRegion = serviceRegionName.split("\\s");
        String cityName = splittedServiceRegion[0];
        List<OpenAPIItem> sigunguList = regionCode.getRegionCodeMap().get(cityName);

        if (CollectionUtils.isEmpty(sigunguList)) {
            throw new RuntimeException("Unknown service region");
        }

        String[] sigunguNameCandidate = serviceRegionName.substring(cityName.length()).split(",");

        return getServiceRegionCode(sigunguList, sigunguNameCandidate);
    }

    private List<ServiceRegion> getServiceRegionCode(List<OpenAPIItem> sigunguList, String[] sigunguNameCandidate) {
        List<ServiceRegion> serviceRegionList = new ArrayList<>();

        for (String candidate : sigunguNameCandidate) {
            for (OpenAPIItem sigungu : sigunguList) {
                if (sigungu.getGugunName().contains(candidate.trim())) {
                    serviceRegionList.add(new ServiceRegion(sigungu.getGugunCode(), sigungu.getGugunName()));
                }
            }
        }

        if (!CollectionUtils.isEmpty(serviceRegionList)) {
            OpenAPIItem localGovernment = sigunguList.get(0);
            return Collections.singletonList(new ServiceRegion(localGovernment.getSidoCode(), localGovernment.getSidoName()));
        }

        return serviceRegionList;
    }
}
