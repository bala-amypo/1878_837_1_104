package com.example.demo.service.impl;

import com.example.demo.model.PolicyRule;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.service.PolicyRuleService;
import com.example.demo.exception.BadRequestException;

import java.util.List;

public class PolicyRuleServiceImpl implements PolicyRuleService {

    private final PolicyRuleRepository repo;

    // 🔴 EXACT constructor expected by tests
    public PolicyRuleServiceImpl(PolicyRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public PolicyRule createRule(PolicyRule rule) {
        if (repo.findByRuleCode(rule.getRuleCode()).isPresent()) {
            throw new BadRequestException("Rule code already exists");
        }
        return repo.save(rule);
    }

    @Override
    public List<PolicyRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    @Override
    public List<PolicyRule> getAllRules() {
        return repo.findByActiveTrue();
    }
}
