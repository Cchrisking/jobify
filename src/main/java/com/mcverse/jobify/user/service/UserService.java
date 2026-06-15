package com.mcverse.jobify.user.service;

import com.mcverse.jobify.auth.model.Role;
import com.mcverse.jobify.common.exception.BusinessRuleException;
import com.mcverse.jobify.common.exception.ResourceNotFoundException;
import com.mcverse.jobify.user.dto.*;
import com.mcverse.jobify.user.model.Company;
import com.mcverse.jobify.user.model.Employer;
import com.mcverse.jobify.user.model.Seeker;
import com.mcverse.jobify.user.repository.CompanyRepository;
import com.mcverse.jobify.user.repository.EmployerRepository;
import com.mcverse.jobify.user.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired private SeekerRepository seekerRepo;
    @Autowired private EmployerRepository employerRepo;
    @Autowired private CompanyRepository companyRepo;

    @Transactional
    public void createProfile(String username, String firstName, String lastName, Role role) {
        if (role == Role.SEEKER) {
            seekerRepo.save(new Seeker(firstName, lastName, username, false));
        } else {
            employerRepo.save(new Employer(firstName, lastName, username));
        }
    }

    public SeekerResponse getSeekerByUsername(String username) {
        Seeker seeker = seekerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker", username));
        return toSeekerResponse(seeker);
    }

    public SeekerResponse getSeekerById(String id) {
        Seeker seeker = seekerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker", id));
        return toSeekerResponse(seeker);
    }

    public EmployerResponse getEmployerByUsername(String username) {
        Employer employer = employerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", username));
        return toEmployerResponse(employer);
    }

    public EmployerResponse getEmployerById(String id) {
        Employer employer = employerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", id));
        return toEmployerResponse(employer);
    }

    @Transactional
    public SeekerResponse updateSeeker(String username, UpdateProfileRequest request) {
        Seeker seeker = seekerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker", username));
        seeker.setName(request.name());
        seeker.setLastName(request.lastName());
        return toSeekerResponse(seekerRepo.save(seeker));
    }

    @Transactional
    public EmployerResponse updateEmployer(String username, UpdateProfileRequest request) {
        Employer employer = employerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", username));
        employer.setName(request.name());
        employer.setLastName(request.lastName());
        return toEmployerResponse(employerRepo.save(employer));
    }

    @Transactional
    public CompanyResponse createCompany(String username, CompanyRequest request) {
        Employer employer = employerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", username));
        if (employer.getCompany() != null) {
            throw new BusinessRuleException("Employer already has a company. Update it instead.");
        }
        Company company = companyRepo.save(new Company(request.name()));
        employer.setCompany(company);
        employerRepo.save(employer);
        return toCompanyResponse(company);
    }

    @Transactional
    public CompanyResponse updateCompany(String username, String companyId, CompanyRequest request) {
        Employer employer = employerRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", username));
        Company company = employer.getCompany();
        if (company == null || !company.getId().equals(companyId)) {
            throw new BusinessRuleException("You do not own this company.");
        }
        company.setName(request.name());
        return toCompanyResponse(companyRepo.save(company));
    }

    // --- mappers ---

    private SeekerResponse toSeekerResponse(Seeker s) {
        return new SeekerResponse(s.getId(), s.getUsername(), s.getName(), s.getLastName(),
                s.getCreationDate(), s.isIndependent());
    }

    private EmployerResponse toEmployerResponse(Employer e) {
        CompanyResponse company = e.getCompany() != null ? toCompanyResponse(e.getCompany()) : null;
        return new EmployerResponse(e.getId(), e.getUsername(), e.getName(), e.getLastName(),
                e.getCreationDate(), company);
    }

    private CompanyResponse toCompanyResponse(Company c) {
        return new CompanyResponse(c.getId(), c.getName());
    }
}
