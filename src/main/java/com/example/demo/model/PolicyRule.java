package com.example.demo.model;

public class PolicyRule {
    private Long id;
    private String ruleCode;
    private Boolean active = true;
    private String appliesToDepartment;
    private String appliesToRole;
    private Integer maxDevicesAllowed;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public String getAppliesToDepartment() { return appliesToDepartment; }
    public void setAppliesToDepartment(String appliesToDepartment) {
        this.appliesToDepartment = appliesToDepartment;
    }
    public String getAppliesToRole() { return appliesToRole; }
    public void setAppliesToRole(String appliesToRole) {
        this.appliesToRole = appliesToRole;
    }
    public Integer getMaxDevicesAllowed() { return maxDevicesAllowed; }
    public void setMaxDevicesAllowed(Integer maxDevicesAllowed) {
        this.maxDevicesAllowed = maxDevicesAllowed;
    }
}
