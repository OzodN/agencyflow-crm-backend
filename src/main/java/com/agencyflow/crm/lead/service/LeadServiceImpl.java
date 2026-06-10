package com.agencyflow.crm.lead.service;

import com.agencyflow.crm.common.exception.BusinessException;
import com.agencyflow.crm.common.exception.EntityNotFoundException;
import com.agencyflow.crm.common.util.CurrentUserResolver;
import com.agencyflow.crm.lead.dto.CreateLeadRequest;
import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.dto.UpdateLeadRequest;
import com.agencyflow.crm.lead.mapper.LeadMapper;
import com.agencyflow.crm.lead.model.Lead;
import com.agencyflow.crm.lead.model.LeadStatus;
import com.agencyflow.crm.lead.repository.LeadRepository;
import com.agencyflow.crm.user.model.Role;
import com.agencyflow.crm.user.model.User;
import com.agencyflow.crm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final UserRepository userRepository;
    private final LeadMapper leadMapper;
    private final CurrentUserResolver currentUserResolver;

    private final User currentUser = getCurrentUser();
    private final LocalDateTime now = LocalDateTime.now();

    @Override
    public LeadResponse create(CreateLeadRequest request) {

        User assignedSalesManager = getSalesManager(request.assignedSalesManagerId());

        Lead lead = Lead.builder()
                .companyName(request.companyName())
                .contactName(request.contactName())
                .email(request.email())
                .phone(request.phone())
                .status(LeadStatus.NEW)
                .assignedSalesManager(assignedSalesManager)
                .deleted(false)
                .createdBy(currentUser)
                .updatedBy(currentUser)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Lead savedLead = leadRepository.save(lead);

        return leadMapper.toResponse(savedLead);
    }

    @Override
    @Transactional(readOnly = true)
    public LeadResponse getById(Long id) {
        Lead lead = getActiveLead(id);
        return leadMapper.toResponse(lead);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeadResponse> getAll() {
        return leadRepository.findByDeletedFalse().stream()
                .map(leadMapper::toResponse)
                .toList();
    }

    @Override
    public LeadResponse update(Long id, UpdateLeadRequest request) {
        Lead lead = getActiveLead(id);

        if (request.companyName() != null) {
            lead.setCompanyName(request.companyName());
        }
        if (request.contactName() != null) {
            lead.setContactName(request.contactName());
        }
        if (request.email() != null) {
            lead.setEmail(request.email());
        }
        if (request.phone() != null) {
            lead.setPhone(request.phone());
        }

        lead.setUpdatedBy(currentUser);
        lead.setUpdatedAt(now);

        Lead savedLead = leadRepository.save(lead);

        return leadMapper.toResponse(savedLead);
    }

    @Override
    public void delete(Long id) {
        Lead lead = getActiveLead(id);

        lead.setDeleted(true);
        lead.setDeletedAt(now);

        lead.setUpdatedBy(currentUser);
        lead.setUpdatedAt(now);

        leadRepository.save(lead);
    }

    @Override
    public LeadResponse changeStatus(Long id, LeadStatus status) {
        Lead lead = getActiveLead(id);

        validateTransition(lead.getStatus(), status);

        lead.setStatus(status);

        lead.setUpdatedAt(now);
        lead.setUpdatedBy(currentUser);

        Lead savedLead = leadRepository.save(lead);

        return leadMapper.toResponse(savedLead);
    }

    @Override
    public LeadResponse assignSalesManager(Long id, Long assignedSalesManagerId) {
        Lead lead = getActiveLead(id);
        User salesManager = getSalesManager(assignedSalesManagerId);

        lead.setAssignedSalesManager(salesManager);

        lead.setUpdatedAt(now);
        lead.setUpdatedBy(currentUser);

        Lead savedLead = leadRepository.save(lead);

        return leadMapper.toResponse(savedLead);    }

    @Override
    public LeadResponse convert(Long leadId) {
        Lead lead = getActiveLead(leadId);

        if (lead.getStatus() != LeadStatus.QUALIFIED) {
            throw new BusinessException("Lead must be qualified before conversion");
        }

        throw new UnsupportedOperationException(
                "Conversion implementation will be added in Customer module"
        );
    }

    private @NonNull User getSalesManager(Long assignedSalesManagerId) {
        if (assignedSalesManagerId == null) {
            throw new BusinessException("Assigned sales manager id is required");
        }

        User assignedSalesManager = userRepository.findById(assignedSalesManagerId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Sales manager with id %d not found".formatted(assignedSalesManagerId)
                        )
                );

        if (assignedSalesManager.getRole() != Role.SALES_MANAGER) {
            throw new BusinessException(
                    "User with id %d is not sales manager".formatted(assignedSalesManagerId)
            );
        }

        return assignedSalesManager;
    }

    private Lead getActiveLead(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with id: " + id));

        if (lead.isDeleted()) {
            throw new EntityNotFoundException("Lead not found with id: " + id);
        }

        return lead;
    }

    private void validateTransition(LeadStatus currentStatus, LeadStatus nextStatus) {
        boolean valid = switch (currentStatus) {
            case NEW -> nextStatus == LeadStatus.CONTACTED || nextStatus == LeadStatus.REJECTED;
            case CONTACTED -> nextStatus == LeadStatus.QUALIFIED || nextStatus == LeadStatus.REJECTED;
            case QUALIFIED -> nextStatus == LeadStatus.CONVERTED;
            case CONVERTED, REJECTED -> false;
        };

        if (!valid) {
            throw new BusinessException(
                    "Invalid lead status transition from " + currentStatus + " to " + nextStatus
            );
        }
    }

    private User getCurrentUser() {
        return currentUserResolver.getCurrentUser();
    }
}
