package com.example.tour.infrastructure.hibernate.regioncode;

import com.example.tour.infrastructure.hibernate.program.Program;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "region_code")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionCode {

    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Integer code;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;
}
