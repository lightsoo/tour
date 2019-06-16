package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.regioncode.ProgramServiceRegion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Entity
@Table(name = "program")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "programServiceRegions")
public class Program {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "theme", nullable = false)
    private String theme;
    @Column(name = "intro")
    private String intro;
    @Column(name = "service_region_name")
    private String serviceRegionName;
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program")
    private List<ProgramServiceRegion> programServiceRegions;

}
