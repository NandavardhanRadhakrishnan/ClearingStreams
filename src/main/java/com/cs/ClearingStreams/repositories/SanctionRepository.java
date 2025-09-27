package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.entities.SanctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanctionRepository extends JpaRepository<SanctionEntity, String> {

    @Query(value = "select countryCode from SanctionEntity where active = true")
    List<String> findCountryCodesByActiveTrue();
}
