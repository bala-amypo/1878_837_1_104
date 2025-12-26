package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PolicyRule;
import com.example.demo.repository.PolicyRuleRepository;

import java.util.List;

public class PolicyRuleServiceImpl {

    private final PolicyRuleRepository repo;

    public PolicyRuleServiceImpl(PolicyRuleRepository repo) {
        this.repo = repo;
    }

    public PolicyRule createRule(PolicyRule rule) {
        if (repo.findByRuleCode(rule.getRuleCode()).isPresent())
            throw new BadRequestException("Rule code already exists");
        return repo.save(rule);
    }

    public List<PolicyRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    public List<PolicyRule> getAllRules() {
        return repo.findByActiveTrue();
    }
}
