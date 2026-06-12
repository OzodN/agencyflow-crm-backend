package com.agencyflow.crm.customer.mapper;

import com.agencyflow.crm.customer.dto.CustomerResponse;
import com.agencyflow.crm.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);
}