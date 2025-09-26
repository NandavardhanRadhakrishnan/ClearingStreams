package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.entities.SanctionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SanctionRepository extends JpaRepository<SanctionEntity, String> {

    List<String> findCountryCodesByActiveTrue();
}
