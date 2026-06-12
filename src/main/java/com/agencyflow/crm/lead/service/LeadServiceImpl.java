package com.agencyflow.crm.lead.service;

import com.agencyflow.crm.common.exception.BusinessException;
import com.agencyflow.crm.common.exception.EntityNotFoundException;
import com.agencyflow.crm.customer.model.Customer;
import com.agencyflow.crm.customer.service.CustomerService;
import com.agencyflow.crm.lead.dto.CreateLeadRequest;
import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.dto.UpdateLeadRequest;
import com.agencyflow.crm.lead.dto.UpdateLeadStatusRequest;
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
    private final CustomerService customerService;

    @Override
    public LeadResponse create(@NonNull CreateLeadRequest request) {

        User assignedSalesManager = getSalesManager(request.assignedSalesManagerId());

        Lead lead = Lead.builder()
                .companyName(request.companyName())
                .contactName(request.contactName())
                .email(request.email())
                .phone(request.phone())
                .status(LeadStatus.NEW)
                .assignedSalesManager(assignedSalesManager)
                .deleted(false)
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
    public LeadResponse update(Long id, @NonNull UpdateLeadRequest request) {
        Lead lead = getActiveLead(id);

        if (request.companyName() != null) lead.setCompanyName(request.companyName());
        if (request.contactName() != null) lead.setContactName(request.contactName());
        if (request.email() != null) lead.setEmail(request.email());
        if (request.phone() != null) lead.setPhone(request.phone());

        return leadMapper.toResponse(leadRepository.save(lead));
    }

    @Override
    public void delete(Long id) {
        Lead lead = getActiveLead(id);

        lead.setDeleted(true);
        lead.setDeletedAt(LocalDateTime.now());

        leadRepository.save(lead);
    }

    @Override
    public LeadResponse changeStatus(Long id, @NonNull UpdateLeadStatusRequest request) {
        Lead lead = getActiveLead(id);
        LeadStatus newStatus = request.status();

        validateTransition(lead.getStatus(), newStatus);

        lead.setStatus(newStatus);

        return leadMapper.toResponse(leadRepository.save(lead));
    }

    @Override
    public LeadResponse assignSalesManager(Long id, Long assignedSalesManagerId) {
        Lead lead = getActiveLead(id);
        User salesManager = getSalesManager(assignedSalesManagerId);

        lead.setAssignedSalesManager(salesManager);

        return leadMapper.toResponse(leadRepository.save(lead));
    }

    @Override
    public LeadResponse convert(Long leadId) {
        Lead lead = getActiveLead(leadId);

        if (lead.getStatus() != LeadStatus.QUALIFIED) {
            throw new BusinessException("Lead must be qualified before conversion");
        }

        Customer customer = customerService.createFromLead(lead);

        lead.setStatus(LeadStatus.CONVERTED);

        lead.setConvertedCustomer(customer);
        lead.setConvertedAt(LocalDateTime.now());

        return leadMapper.toResponse(leadRepository.save(lead));
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

    private @NonNull Lead getActiveLead(Long id) {
        return leadRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found with id: " + id));
    }

    private void validateTransition(@NonNull LeadStatus currentStatus, LeadStatus nextStatus) {
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
}
