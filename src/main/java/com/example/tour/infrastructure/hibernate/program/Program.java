package com.example.tour.infrastructure.hibernate.program;

import com.example.tour.infrastructure.hibernate.region.ServiceRegion;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "program")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Builder
    private Program(String name, String theme, String intro, String description, ServiceRegion serviceRegion) {
        this.name = name;
        this.theme = theme;
        this.intro = intro;
        this.description = description;
        this.serviceRegion = serviceRegion;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "code")
    private ServiceRegion serviceRegion;

}
