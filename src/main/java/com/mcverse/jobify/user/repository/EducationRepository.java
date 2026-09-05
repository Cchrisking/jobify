package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EducationRepository extends JpaRepository<Education, String> {
    List<Education> findBySeekerId(String seekerId);
    Optional<Education> findByIdAndSeekerId(String id, String seekerId);
}
