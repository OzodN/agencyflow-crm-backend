package com.agencyflow.crm.customer.service;

import com.agencyflow.crm.common.exception.EntityNotFoundException;
import com.agencyflow.crm.customer.dto.CustomerResponse;
import com.agencyflow.crm.customer.dto.UpdateCustomerRequest;
import com.agencyflow.crm.customer.mapper.CustomerMapper;
import com.agencyflow.crm.customer.model.Customer;
import com.agencyflow.crm.customer.repository.CustomerRepository;
import com.agencyflow.crm.lead.model.Lead;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        Customer customer = getActiveCustomer(id);
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return customerRepository.findByDeletedFalse().stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse update(Long id, @NonNull UpdateCustomerRequest request) {
        Customer customer = getActiveCustomer(id);

        if (request.companyName() != null) customer.setCompanyName(request.companyName());
        if (request.contactName() != null) customer.setContactName(request.contactName());
        if (request.email() != null) customer.setEmail(request.email());
        if (request.phone() != null) customer.setPhone(request.phone());

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void delete(Long id) {
        Customer customer = getActiveCustomer(id);
        LocalDateTime now = LocalDateTime.now();

        customer.setDeleted(true);
        customer.setDeletedAt(now);

        customerRepository.save(customer);
    }

    @Override
    public Customer createFromLead(@NonNull Lead lead) {
        Customer customer = Customer.builder()
                .companyName(lead.getCompanyName())
                .contactName(lead.getContactName())
                .email(lead.getEmail())
                .phone(lead.getPhone())
                .deleted(false)
                .build();

        return customerRepository.save(customer);
    }

    private @NonNull Customer getActiveCustomer(Long id) {
        return customerRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: %d".formatted(id)));
    }
}
