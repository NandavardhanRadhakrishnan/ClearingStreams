package com.cs.ClearingStreams.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SanctionEntity {
    @Id
    private String countryCode;
    private String reason;
    private boolean active;
}
