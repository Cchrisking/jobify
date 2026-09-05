package com.mcverse.jobify.cms.repository;

import com.mcverse.jobify.cms.model.ContentEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentEntryRepository extends JpaRepository<ContentEntry, String> {
}
