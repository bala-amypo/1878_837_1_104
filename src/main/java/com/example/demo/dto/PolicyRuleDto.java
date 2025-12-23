package com.example.demo.dto;
public record PolicyRuleDto(String ruleCode, String description, String appliesToRole,
                            String appliesToDepartment, Integer maxDevicesAllowed, Boolean active) {}