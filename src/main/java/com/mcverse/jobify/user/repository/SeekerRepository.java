package com.mcverse.jobify.user.repository;

import com.mcverse.jobify.user.model.Seeker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeekerRepository extends JpaRepository<Seeker, String> {
    Optional<Seeker> findByUsername(String username);
}
