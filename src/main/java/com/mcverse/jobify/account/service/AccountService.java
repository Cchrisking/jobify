package com.mcverse.jobify.account.service;

import com.mcverse.jobify.account.dto.ChangePasswordRequest;
import com.mcverse.jobify.account.dto.DeletionRequestRequest;
import com.mcverse.jobify.account.dto.DeletionRequestResponse;
import com.mcverse.jobify.account.model.DeletionRequest;
import com.mcverse.jobify.account.model.DeletionRequestStatus;
import com.mcverse.jobify.account.repository.DeletionRequestRepository;
import com.mcverse.jobify.auth.model.AppUser;
import com.mcverse.jobify.auth.model.Role;
import com.mcverse.jobify.auth.repository.AuthUserRepository;
import com.mcverse.jobify.common.exception.BusinessRuleException;
import com.mcverse.jobify.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired private AuthUserRepository authUserRepository;
    @Autowired private DeletionRequestRepository deletionRequestRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(String username, ChangePasswordRequest request) {
        AppUser user = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account", username));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BusinessRuleException("Current password is incorrect.");
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        authUserRepository.save(user);
    }

    @Transactional
    public DeletionRequestResponse requestDeletion(String username, Role role, DeletionRequestRequest request) {
        deletionRequestRepository.findFirstByUsernameAndStatusOrderByRequestedAtDesc(username, DeletionRequestStatus.PENDING)
                .ifPresent(existing -> {
                    throw new BusinessRuleException("You already have a pending deletion request.");
                });
        DeletionRequest saved = deletionRequestRepository.save(
                new DeletionRequest(username, role, request.reason()));
        return toResponse(saved);
    }

    public DeletionRequestResponse getMyDeletionRequest(String username) {
        return deletionRequestRepository.findFirstByUsernameAndStatusOrderByRequestedAtDesc(username, DeletionRequestStatus.PENDING)
                .map(this::toResponse)
                .orElse(null);
    }

    @Transactional
    public void cancelMyDeletionRequest(String username) {
        DeletionRequest pending = deletionRequestRepository
                .findFirstByUsernameAndStatusOrderByRequestedAtDesc(username, DeletionRequestStatus.PENDING)
                .orElseThrow(() -> new ResourceNotFoundException("Deletion request", username));
        deletionRequestRepository.delete(pending);
    }

    public DeletionRequestResponse toResponse(DeletionRequest r) {
        return new DeletionRequestResponse(r.getId(), r.getUsername(), r.getRequesterRole(), r.getReason(),
                r.getStatus(), r.getRequestedAt(), r.getResolvedAt(), r.getResolutionNote());
    }
}
