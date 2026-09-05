package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, String> {
    List<Certification> findBySeekerId(String seekerId);
    Optional<Certification> findByIdAndSeekerId(String id, String seekerId);
}
