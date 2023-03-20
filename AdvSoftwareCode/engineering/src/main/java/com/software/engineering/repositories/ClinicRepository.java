package com.software.engineering.repositories;

import com.software.engineering.entities.ClinicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClinicRepository extends JpaRepository<ClinicEntity, Long> {

    List<ClinicEntity> findAllByNameLike(String name);
}
