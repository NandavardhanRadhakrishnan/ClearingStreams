package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.entities.RouteMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RouteMasterRepository extends JpaRepository<RouteMasterEntity, Long> {
    List<RouteMasterEntity> findByActiveTrue();
}