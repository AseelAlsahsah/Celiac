package com.software.engineering.repositories;

import com.software.engineering.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    List<RestaurantEntity> findAllByNameLike(String name);

}
