package com.mcverse.jobify.model;

public enum EmploymentType {
    FULL_TIME("Full-time"),
    PART_TIME("Part-time"),
    CONTRACT("Contract"),
    TEMPORARY("Temporary"),
    INTERNSHIP("Internship"),
    FREELANCE("Freelance");

    private final String displayName;

    EmploymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
