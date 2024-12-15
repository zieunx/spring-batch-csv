package com.danal.publicdataprocessor.domain.repository;

import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByManagementNumber(String managementNumber);
}
