package com.example.tour.infrastructure.remote.openAPI;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "open-api", url = "${constants.open-api.host}", fallbackFactory = OpenAPIFallbackFactory.class)
public interface OpenAPI {

    @RequestMapping(value = "/openapi/service/rest/CodeInquiryService/getAreaCodeInquiryList?_type=json", method = RequestMethod.GET)
    OpenAPIResponseDTO getRegionCode(@RequestParam("schSido") String schSido,
                                     @RequestParam("numOfRows") int numOfRows);
}
