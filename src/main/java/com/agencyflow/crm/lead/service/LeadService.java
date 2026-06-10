package com.agencyflow.crm.lead.service;

import com.agencyflow.crm.lead.dto.CreateLeadRequest;
import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.dto.UpdateLeadRequest;
import com.agencyflow.crm.lead.model.LeadStatus;

import java.util.List;

public interface LeadService {

    LeadResponse create(CreateLeadRequest request);

    LeadResponse getById(Long id);

    List<LeadResponse> getAll();

    LeadResponse update(Long id, UpdateLeadRequest request);

    void delete(Long id);

    LeadResponse changeStatus(Long id, LeadStatus status);

    LeadResponse assignSalesManager(Long id, Long assignedSalesManagerId);

    LeadResponse convert(Long leadId);

}
