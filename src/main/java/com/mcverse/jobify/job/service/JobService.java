package com.mcverse.jobify.job.service;

import com.mcverse.jobify.common.exception.ResourceNotFoundException;
import com.mcverse.jobify.job.dto.JobPostResponse;
import com.mcverse.jobify.model.JobPost;
import com.mcverse.jobify.model.Skill;
import com.mcverse.jobify.job.repository.JobRepo;
import com.mcverse.jobify.user.model.Employer;
import com.mcverse.jobify.user.repository.EmployerRepository;
import com.mcverse.jobify.user.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepo repo;

    @Autowired
    private EmployerRepository employerRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Transactional(readOnly = true)
    public List<JobPostResponse> getJobs(Boolean available) {
        List<JobPost> posts = (available != null)
                ? repo.findAllByAvailable(available)
                : repo.findAll();
        return posts.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public JobPostResponse getJobById(Integer id) {
        return repo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost", id.toString()));
    }

    @Transactional
    public JobPostResponse addJob(JobPost jobPost, String employerUsername) {
        Employer employer = employerRepo.findByUsername(employerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Employer", employerUsername));
        jobPost.setEmployer(employer);
        jobPost.setRequiredSkills(resolveSkills(jobPost.getRequiredSkills()));
        return toResponse(repo.save(jobPost));
    }

    private List<Skill> resolveSkills(List<Skill> requestedSkills) {
        return requestedSkills.stream()
                .map(skill -> skillRepo.findByNameIgnoreCase(skill.getName())
                        .orElseGet(() -> skillRepo.save(new Skill(skill.getName(), skill.getCategory()))))
                .distinct()
                .toList();
    }

    @Transactional
    public JobPostResponse updateAvailability(Integer id, boolean available) {
        JobPost job = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost", id.toString()));
        job.setAvailable(available);
        return toResponse(repo.save(job));
    }

    private JobPostResponse toResponse(JobPost job) {
        String employerUsername = job.getEmployer() != null ? job.getEmployer().getUsername() : null;
        List<String> requiredSkills = job.getRequiredSkills().stream().map(Skill::getName).toList();
        return new JobPostResponse(job.getPostId(), job.getJobTitle(), job.getJobDescription(),
                job.getJobRating(), job.getHourlyRate(), employerUsername, job.isAvailable(),
                job.getLocation(), job.getWorkMode(), job.getEmploymentType(), job.getCreatedAt(),
                requiredSkills);
    }
}
