package com.example.tour.infrastructure.hibernate.region;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "service_region")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceRegion {

    @Id
    private String code;
    private String name;

    @Builder
    public ServiceRegion(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
