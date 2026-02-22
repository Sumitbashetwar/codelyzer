package com.example.CompilerDemo.repository;

import com.example.CompilerDemo.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase,Integer> {
}
