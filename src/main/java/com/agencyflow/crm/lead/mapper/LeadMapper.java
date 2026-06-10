package com.agencyflow.crm.lead.mapper;

import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.model.Lead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeadMapper {

    @Mapping(
            target = "assignedSalesManagerId",
            source = "assignedSalesManager.id"
    )
    @Mapping(
            target = "convertedCustomerId",
            source = "convertedCustomer.id"
    )
    LeadResponse toResponse(Lead lead);
}