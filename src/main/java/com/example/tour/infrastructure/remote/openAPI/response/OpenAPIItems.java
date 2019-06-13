package com.example.tour.infrastructure.remote.openAPI.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OpenAPIItems {
    @JsonProperty("item")
    private List<OpenAPIItem> itemList;
}

