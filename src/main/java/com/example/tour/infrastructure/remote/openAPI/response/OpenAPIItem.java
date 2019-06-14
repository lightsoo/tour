package com.example.tour.infrastructure.remote.openAPI.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenAPIItem {
    @JsonProperty("gugunCd")
    private int gugunCode;
    @JsonProperty("gugunNm")
    private String gugunName;
    @JsonProperty("sidoCd")
    private int sidoCode;
    @JsonProperty("sidoNm")
    private String sidoName;
}

