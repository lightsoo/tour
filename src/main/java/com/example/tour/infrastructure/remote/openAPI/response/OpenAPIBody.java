package com.example.tour.infrastructure.remote.openAPI.response;

import lombok.Data;

@Data
public class OpenAPIBody {
    private int numOfRows;
    private int pageNo;
    private int totalCount;
    private OpenAPIItems items;
}

