package com.example.tour.infrastructure.remote.openAPI;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OpenAPIFallbackFactory implements FallbackFactory<OpenAPI> {

    @Override
    public OpenAPI create(Throwable cause) {
        return (schSido, numOfRows) -> new OpenAPIResponseDTO();
    }
}
