package com.example.tour.infrastructure.hibernate.serviceregion;

import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceRegion")
    private List<ProgramServiceRegion> programServiceRegions;
}
