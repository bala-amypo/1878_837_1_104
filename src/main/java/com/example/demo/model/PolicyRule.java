package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "policy_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleCode;

    @Column
    private String description;

    @Column
    private String appliesToRole; // ADMIN / DEVELOPER / MANAGER / STAFF / null for all

    @Column
    private String appliesToDepartment; // IT / HR / SALES / FINANCE / null for all

    @Column(nullable = false)
    private Integer maxDevicesAllowed;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (this.active == null) {
            this.active = true;
        }
    }

    public PolicyRule(String ruleCode, String description, String appliesToRole, String appliesToDepartment,
            Integer maxDevicesAllowed) {
        this.ruleCode = ruleCode;
        this.description = description;
        this.appliesToRole = appliesToRole;
        this.appliesToDepartment = appliesToDepartment;
        this.maxDevicesAllowed = maxDevicesAllowed;
        this.active = true;
    }
}
