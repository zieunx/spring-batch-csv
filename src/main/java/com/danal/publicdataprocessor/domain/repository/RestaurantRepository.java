package com.danal.publicdataprocessor.domain.repository;

import com.danal.publicdataprocessor.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByManagementNumber(String managementNumber);
    @Query("SELECT r FROM Restaurant r WHERE r.managementNumber IN :managementNumbers")
    List<Restaurant> findByManagementNumbers(@Param("managementNumbers") List<String> managementNumbers);
}
