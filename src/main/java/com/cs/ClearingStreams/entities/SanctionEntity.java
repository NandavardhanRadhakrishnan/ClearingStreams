package com.cs.ClearingStreams.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rule_sanction")
public class SanctionEntity {
    @Id
    private String countryCode;
    private String reason;
    private boolean active;
}
