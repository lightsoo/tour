package com.example.tour.api.v1.response;

import com.example.tour.api.v1.request.ProgramDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TourInformationResponseDTO {
    @JsonProperty("region")
    private Integer code;

    @JsonProperty("programs")
    private List<ProgramDTO> programs;

}
