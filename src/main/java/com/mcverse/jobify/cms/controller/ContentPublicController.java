package com.mcverse.jobify.cms.controller;

import com.mcverse.jobify.cms.service.ContentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/content")
@Tag(name = "Content", description = "Public, admin-editable front-end content (landing copy, error messages, SEO)")
public class ContentPublicController {

    @Autowired
    private ContentService contentService;

    @GetMapping
    @Operation(summary = "Get all published content as a flat key-value map")
    public Map<String, String> getContent() {
        return contentService.getPublicMap();
    }
}
