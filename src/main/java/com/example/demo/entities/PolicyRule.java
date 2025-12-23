package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "policy_rules",
       uniqueConstraints = @UniqueConstraint(columnNames = "ruleCode"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PolicyRule {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleCode;

    private String description;

    private String appliesToRole; // nullable for all

    private String appliesToDepartment; // nullable for all

    @Column(nullable = false)
    private Integer maxDevicesAllowed;

    private boolean active = true;
}