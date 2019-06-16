package com.example.tour.infrastructure.hibernate.region;

import com.example.tour.infrastructure.hibernate.program.Program;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(exclude = "programs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "programs")
public class ServiceRegion {

    @Id
    private Integer code;
    @Column
    private String name;

    @OneToMany(mappedBy = "serviceRegion", cascade = CascadeType.ALL)
    private List<Program> programs;
}
