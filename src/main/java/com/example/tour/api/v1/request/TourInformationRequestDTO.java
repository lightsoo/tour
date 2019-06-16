package com.example.tour.api.v1.request;

import com.example.tour.domain.tour.TourInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourInformationRequestDTO {

    @NotNull
    private String name;
    @NotNull
    private String theme;
    @NotNull
    private String serviceRegionName;
    private String intro;
    private String description;

    public static TourInformation convert(TourInformationRequestDTO request) {
        TourInformation tourInformation = new TourInformation();
        tourInformation.setName(request.getName());
        tourInformation.setTheme(request.getTheme());
        tourInformation.setDescription(request.getDescription());
        tourInformation.setIntro(request.getIntro());
        tourInformation.setServiceRegionName(request.getServiceRegionName());

        return tourInformation;
    }
}
