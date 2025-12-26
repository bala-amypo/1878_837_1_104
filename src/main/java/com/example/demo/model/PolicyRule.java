package com.example.demo.model;

import lombok.*;

@Getter @Setter
public class PolicyRule {
    private Long id;
    private String ruleCode;
    private Boolean active = true;
    private String appliesToDepartment;
    private String appliesToRole;
    private Integer maxDevicesAllowed;
}
