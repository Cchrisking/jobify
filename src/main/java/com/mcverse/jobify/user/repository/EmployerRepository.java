package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerRepository extends JpaRepository<Employer, String> {
    Optional<Employer> findByUsername(String username);
}
