package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class PolicyRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ruleCode;

    private String description;

    private String appliesToRole; // nullable = all

    private String appliesToDepartment; // nullable = all

    @Column(nullable = false)
    private int maxDevicesAllowed;

    @Column(nullable = false)
    private boolean active = true;

    // No-args constructor
    public PolicyRule() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAppliesToRole() { return appliesToRole; }
    public void setAppliesToRole(String appliesToRole) { this.appliesToRole = appliesToRole; }

    public String getAppliesToDepartment() { return appliesToDepartment; }
    public void setAppliesToDepartment(String appliesToDepartment) { this.appliesToDepartment = appliesToDepartment; }

    public int getMaxDevicesAllowed() { return maxDevicesAllowed; }
    public void setMaxDevicesAllowed(int maxDevicesAllowed) { this.maxDevicesAllowed = maxDevicesAllowed; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}