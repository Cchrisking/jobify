package com.mcverse.jobify.common.validation;

import com.mcverse.jobify.user.model.JobPreferences;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SalaryRangeValidator implements ConstraintValidator<ValidSalaryRange, JobPreferences> {

    @Override
    public boolean isValid(JobPreferences prefs, ConstraintValidatorContext context) {
        if (prefs == null) return true;
        return prefs.getMinSalaryExpectation() <= prefs.getMaxSalaryExpectation();
    }
}
