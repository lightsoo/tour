package com.example.tour.infrastructure.remote.openAPI.response;

import lombok.Data;

@Data
public class OpenAPIResponse {
    private OpenAPIHeader header;
    private OpenAPIBody body;
}