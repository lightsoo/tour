package com.example.tour.domain.tour;

import lombok.Data;

import java.io.Serializable;

@Data
public class TourInformation implements Serializable {
    private static final long serialVersionUID = -4629253652060033619L;

    private Integer no;
    private String name;
    private String theme;
    private String serviceRegionName;
    private String intro;
    private String description;
}
