package com.example.demo.repository;

import com.example.demo.model.PolicyRule;
import java.util.*;

public interface PolicyRuleRepository {
    Optional<PolicyRule> findByRuleCode(String code);
    List<PolicyRule> findByActiveTrue();
    Optional<PolicyRule> findById(Long id);
    PolicyRule save(PolicyRule rule);
}
