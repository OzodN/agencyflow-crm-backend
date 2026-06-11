package com.agencyflow.crm.lead.repository;

import com.agencyflow.crm.lead.model.Lead;
import com.agencyflow.crm.lead.model.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findByDeletedFalse();

    Optional<Lead> findByIdAndDeletedFalse(Long id);

    List<Lead> findByStatusAndDeletedFalse(LeadStatus status);

    List<Lead> findByAssignedSalesManagerIdAndDeletedFalse(Long salesManagerId);
}