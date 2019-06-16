package com.example.tour.infrastructure.hibernate.serviceregion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "service_region")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceRegion {
    @Id
    private Integer code;
    @Column
    private String name;
}
