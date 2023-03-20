package com.software.engineering.repositories;

import com.software.engineering.entities.RestaurantBookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantBookingsRepository extends JpaRepository<RestaurantBookingsEntity, Long> {
}
