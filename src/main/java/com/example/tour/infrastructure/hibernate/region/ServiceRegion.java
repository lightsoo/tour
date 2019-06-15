package com.example.tour.infrastructure.hibernate.region;

import com.example.tour.infrastructure.hibernate.program.Program;
import lombok.AccessLevel;
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
@Table(name = "service_region")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ServiceRegion {

    @Id
    private String code;
    @Column
    private String name;

    @Builder
    private ServiceRegion(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @OneToMany(mappedBy = "serviceRegion", cascade = CascadeType.ALL)
    private List<Program> program;
}
