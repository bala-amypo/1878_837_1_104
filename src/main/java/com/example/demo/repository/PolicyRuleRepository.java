// File: src/main/java/com/example/demo/repository/PolicyRuleRepository.java
package com.example.demo.repository;

import com.example.demo.model.PolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PolicyRuleRepository extends JpaRepository<PolicyRule, Long> {

    List<PolicyRule> findByActiveTrue();
    Optional<PolicyRule> findByRuleCode(String ruleCode);
    boolean existsByRuleCode(String ruleCode);
    List<PolicyRule> findAll();
}