package com.agencyflow.crm.lead.controller;

import com.agencyflow.crm.lead.dto.CreateLeadRequest;
import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.dto.UpdateLeadRequest;
import com.agencyflow.crm.lead.model.LeadStatus;
import com.agencyflow.crm.lead.service.LeadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @PostMapping
    public LeadResponse create(@RequestBody @Valid CreateLeadRequest request) {
        return leadService.create(request);
    }

    @GetMapping
    public List<LeadResponse> getAll() {
        return leadService.getAll();
    }

    @GetMapping("/{id}")
    public LeadResponse getById(@PathVariable Long id) {
        return leadService.getById(id);
    }

    @PutMapping("/{id}")
    public LeadResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateLeadRequest request
    ) {
        return leadService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public LeadResponse changeStatus(
            @PathVariable Long id,
            @RequestParam LeadStatus status
    ) {
        return leadService.changeStatus(id, status);
    }

    @PatchMapping("/{id}/assign/{salesManagerId}")
    public LeadResponse assignSalesManager(
            @PathVariable Long id,
            @PathVariable Long salesManagerId
    ) {
        return leadService.assignSalesManager(id, salesManagerId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        leadService.delete(id);
    }
}