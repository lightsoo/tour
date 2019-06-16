package com.example.tour.infrastructure.hibernate.regioncode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramServiceRegionId implements Serializable {
    private static final long serialVersionUID = -3202920032789958074L;

    private Integer program;
    private Integer serviceRegion;
}
