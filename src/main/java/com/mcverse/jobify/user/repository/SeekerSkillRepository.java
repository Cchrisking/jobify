package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.SeekerSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeekerSkillRepository extends JpaRepository<SeekerSkill, String> {
    List<SeekerSkill> findBySeekerId(String seekerId);
    Optional<SeekerSkill> findByIdAndSeekerId(String id, String seekerId);
    Optional<SeekerSkill> findBySeekerIdAndSkillId(String seekerId, String skillId);
}
