package com.example.tour.api.v1.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ProgramDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String theme;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String serviceRegionName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String intro;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
}
