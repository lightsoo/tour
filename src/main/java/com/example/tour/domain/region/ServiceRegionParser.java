package com.example.tour.domain.region;

import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import com.example.tour.infrastructure.hibernate.region.ServiceRegionRepository;
import com.example.tour.infrastructure.remote.openAPI.response.OpenAPIItem;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceRegionParser {
    private final RegionCode regionCode;
    private final ServiceRegionRepository regionRepository;

    public List<ServiceRegion> parse(String serviceRegionName) {
        if (StringUtils.isEmpty(serviceRegionName)) {
            throw new RuntimeException("Service region is required");
        }

        if (serviceRegionName.contains(",")) {
            return getMultipleServiceRegion(serviceRegionName);
        } else {
            return getSingleServiceRegion(serviceRegionName);
        }
    }

    private List<ServiceRegion> getMultipleServiceRegion(String serviceRegionName) {
        String localGovernmentName = serviceRegionName.split("\\s")[0];
        String[] sigunguNameCandidates = serviceRegionName.substring(localGovernmentName.length())
                                                          .split(",");

        return getServiceRegionCode(localGovernmentName, sigunguNameCandidates);
    }

    private List<ServiceRegion> getSingleServiceRegion(String serviceRegionName) {
        String[] splitServiceRegionName = serviceRegionName.split("\\s");
        String localGovernmentName = splitServiceRegionName[0];

        if (splitServiceRegionName.length == 1) {
            return getServiceRegionCode(localGovernmentName);
        } else {
            return getServiceRegionCode(localGovernmentName, splitServiceRegionName[1]);
        }
    }

    private List<ServiceRegion> getServiceRegionCode(String localGovernmentName, String... sigunguNameCandidate) {
        List<OpenAPIItem> sigunguList = regionCode.getRegionCodeMap().get(localGovernmentName);

        if (CollectionUtils.isEmpty(sigunguList)) {
            throw new RuntimeException(localGovernmentName + "is unknown service region");
        }

        List<ServiceRegion> serviceRegionList = new ArrayList<>();

        for (String candidate : sigunguNameCandidate) {
            for (OpenAPIItem sigungu : sigunguList) {
                if (sigungu.getGugunName().contains(candidate.trim())) {
                    serviceRegionList.add(regionRepository.findByCode(sigungu.getGugunCode()));
                }
            }
        }

        if (CollectionUtils.isEmpty(serviceRegionList)) {
            OpenAPIItem localGovernment = sigunguList.get(0);
            return Collections.singletonList(regionRepository.findByCode(localGovernment.getSidoCode()));
        }

        return serviceRegionList;
    }
}
