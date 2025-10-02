package com.cs.ClearingStreams.repositories;

import com.cs.ClearingStreams.constants.enums.TransactionType;
import com.cs.ClearingStreams.entities.RuleMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleMasterRepository extends JpaRepository<RuleMasterEntity, Long> {

    List<RuleMasterEntity> findRuleMasterEntitiesByTypeOrderByPriority(TransactionType type);
}
