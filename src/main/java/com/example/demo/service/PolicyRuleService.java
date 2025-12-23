package com.example.demo.service;

import com.example.demo.model.PolicyRule;

import java.util.List;

public interface PolicyRuleService {
    PolicyRule create(PolicyRule rule);

    List<PolicyRule> getAll();

    void delete(Long id);

    List<PolicyRule> getActiveRules();
}
