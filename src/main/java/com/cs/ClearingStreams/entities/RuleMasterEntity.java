package com.cs.ClearingStreams.entities;

import com.cs.ClearingStreams.constants.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rule_master")
public class RuleMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TransactionType type;
    private String rule;
    private int priority;
    private String postValidation;

}
