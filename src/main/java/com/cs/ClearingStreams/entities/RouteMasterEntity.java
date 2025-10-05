package com.cs.ClearingStreams.entities;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;


@Data
@Entity
@Table(name = "route_master")
public class RouteMasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String topic;
    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private JsonNode criteria;
    private boolean isMutating;
    private boolean active;
}
