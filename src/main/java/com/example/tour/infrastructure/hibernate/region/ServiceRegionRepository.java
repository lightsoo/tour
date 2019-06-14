package com.example.tour.infrastructure.hibernate.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRegionRepository extends JpaRepository<ServiceRegion, String> {
}
