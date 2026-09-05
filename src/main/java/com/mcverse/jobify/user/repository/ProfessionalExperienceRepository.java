package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.ProfessionalExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessionalExperienceRepository extends JpaRepository<ProfessionalExperience, String> {
    List<ProfessionalExperience> findBySeekerId(String seekerId);
    Optional<ProfessionalExperience> findByIdAndSeekerId(String id, String seekerId);
}
