package com.agencyflow.crm.lead.repository;

import com.agencyflow.crm.lead.model.Lead;
import com.agencyflow.crm.lead.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findByDeletedFalse();

    List<Lead> findByStatusAndDeletedFalse(LeadStatus status);

    List<Lead> findByAssignedSalesManagerIdAndDeletedFalse(Long salesManagerId);
}