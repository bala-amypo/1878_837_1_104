package com.example.demo.service.impl;

import com.example.demo.model.PolicyRule;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.service.PolicyRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyRuleServiceImpl implements PolicyRuleService {

    private final PolicyRuleRepository repository;

    public PolicyRuleServiceImpl(PolicyRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public PolicyRule create(PolicyRule rule) {
        if (repository.existsByRuleCode(rule.getRuleCode())) {
            throw new RuntimeException("Rule with code already exists");
        }
        return repository.save(rule);
    }

    @Override
    public List<PolicyRule> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<PolicyRule> getActiveRules() {
        return repository.findByActiveTrue();
    }
}
