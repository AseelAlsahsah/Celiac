package com.software.engineering.repositories;

import com.software.engineering.entities.ClinicBookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicBookingsRepository extends JpaRepository<ClinicBookingsEntity, Long> {
}
