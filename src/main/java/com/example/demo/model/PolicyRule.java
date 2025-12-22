package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "policy_rules", uniqueConstraints = @UniqueConstraint(columnNames = "ruleCode"))
public class PolicyRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ruleCode;

    private String description;

    private String appliesToRole;

    private String appliesToDepartment;

    @Column(nullable = false)
    private Integer maxDevicesAllowed;

    @Column(nullable = false)
    private Boolean active = true;

    public PolicyRule() {}

    public PolicyRule(String ruleCode, String description, String appliesToRole, String appliesToDepartment, Integer maxDevicesAllowed) {
        this.ruleCode = ruleCode;
        this.description = description;
        this.appliesToRole = appliesToRole;
        this.appliesToDepartment = appliesToDepartment;
        this.maxDevicesAllowed = maxDevicesAllowed;
    }

    public Long getId() { return id; }
    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getAppliesToRole() { return appliesToRole; }
    public void setAppliesToRole(String appliesToRole) { this.appliesToRole = appliesToRole; }
    public String getAppliesToDepartment() { return appliesToDepartment; }
    public void setAppliesToDepartment(String appliesToDepartment) { this.appliesToDepartment = appliesToDepartment; }
    public Integer getMaxDevicesAllowed() { return maxDevicesAllowed; }
    public void setMaxDevicesAllowed(Integer maxDevicesAllowed) { this.maxDevicesAllowed = maxDevicesAllowed; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}