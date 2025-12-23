// com/example/demo/repository/PolicyRuleRepository.java
package com.example.demo.repository;

import com.example.demo.model.PolicyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PolicyRuleRepository extends JpaRepository<PolicyRule, Long> {

    @Query("SELECT r FROM PolicyRule r WHERE r.active = true")
    List<PolicyRule> findByActiveTrue();

    Optional<PolicyRule> findByRuleCode(String ruleCode);

    List<PolicyRule> findAll();
}