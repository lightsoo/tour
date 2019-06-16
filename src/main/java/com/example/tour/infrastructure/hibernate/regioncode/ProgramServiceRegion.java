package com.example.tour.infrastructure.hibernate.regioncode;

import com.example.tour.infrastructure.hibernate.program.Program;
import com.example.tour.infrastructure.hibernate.serviceregion.ServiceRegion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "program_service_region")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@IdClass(ProgramServiceRegionId.class)
public class ProgramServiceRegion {

    @Id
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Id
    @ManyToOne
    @JoinColumn(name = "service_region_code")
    private ServiceRegion serviceRegion;
}
