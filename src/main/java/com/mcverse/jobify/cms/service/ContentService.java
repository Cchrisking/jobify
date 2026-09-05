package com.mcverse.jobify.cms.service;

import com.mcverse.jobify.cms.dto.ContentEntryResponse;
import com.mcverse.jobify.cms.model.ContentEntry;
import com.mcverse.jobify.cms.repository.ContentEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {

    @Autowired
    private ContentEntryRepository contentEntryRepository;

    public Map<String, String> getPublicMap() {
        return contentEntryRepository.findAll().stream()
                .collect(Collectors.toMap(ContentEntry::getKey, ContentEntry::getValue));
    }

    public List<ContentEntryResponse> getAllForAdmin() {
        return contentEntryRepository.findAll().stream()
                .map(e -> new ContentEntryResponse(e.getKey(), e.getCategory(), e.getValue(), e.getUpdatedAt()))
                .toList();
    }

    @Transactional
    public List<ContentEntryResponse> updateEntries(Map<String, String> updates) {
        updates.forEach((key, value) ->
                contentEntryRepository.findById(key).ifPresent(entry -> entry.setValue(value)));
        return getAllForAdmin();
    }
}
