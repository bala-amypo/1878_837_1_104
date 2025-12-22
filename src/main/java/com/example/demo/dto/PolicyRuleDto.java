package com.example.demo.dto;

public class PolicyRuleDto {
    private String ruleCode;
    private String description;
    private String appliesToRole;
    private String appliesToDepartment;
    private Integer maxDevicesAllowed;
    private Boolean active;

    // getters/setters
    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String v) { this.ruleCode = v; }
    public String getDescription() { return description; }
    public void setDescription(String v) { this.description = v; }
    public String getAppliesToRole() { return appliesToRole; }
    public void setAppliesToRole(String v) { this.appliesToRole = v; }
    public String getAppliesToDepartment() { return appliesToDepartment; }
    public void setAppliesToDepartment(String v) { this.appliesToDepartment = v; }
    public Integer getMaxDevicesAllowed() { return maxDevicesAllowed; }
    public void setMaxAllowedPerEmployee(Integer v) { this.maxDevicesAllowed = v; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean v) { this.active = v; }
}