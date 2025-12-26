package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PolicyRule;
import com.example.demo.repository.PolicyRuleRepository;
import com.example.demo.service.PolicyRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyRuleServiceImpl implements PolicyRuleService {

    private final PolicyRuleRepository policyRuleRepository;

    public PolicyRuleServiceImpl(PolicyRuleRepository policyRuleRepository) {
        this.policyRuleRepository = policyRuleRepository;
    }

    @Override
    public PolicyRule createRule(PolicyRule rule) {
        // Check duplicate ruleCode
        if (policyRuleRepository.findByRuleCode(rule.getRuleCode()).isPresent()) {
            throw new BadRequestException("Rule code already exists: " + rule.getRuleCode());
        }

        return policyRuleRepository.save(rule);
    }

    @Override
    public List<PolicyRule> getAllRules() {
        return policyRuleRepository.findAll();
    }

    @Override
    public List<PolicyRule> getActiveRules() {
        return policyRuleRepository.findByActiveTrue();
    }

    @Override
    public PolicyRule updateActiveStatus(Long id, boolean active) {
        PolicyRule rule = policyRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy rule not found with id: " + id));
        rule.setActive(active);
        return policyRuleRepository.save(rule);
    }

    @Override
    public void deleteRule(Long id) {
        PolicyRule rule = policyRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy rule not found with id: " + id));
        policyRuleRepository.delete(rule);
    }
}
