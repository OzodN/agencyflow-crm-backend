package com.agencyflow.crm.customer.service;

import com.agencyflow.crm.customer.dto.CustomerResponse;
import com.agencyflow.crm.customer.dto.UpdateCustomerRequest;
import com.agencyflow.crm.customer.model.Customer;
import com.agencyflow.crm.lead.model.Lead;

import java.util.List;

public interface CustomerService {

    CustomerResponse getById(Long id);

    List<CustomerResponse> getAll();

    CustomerResponse update(Long id, UpdateCustomerRequest request);

    void delete(Long id);

    Customer createFromLead(Lead lead);
}
