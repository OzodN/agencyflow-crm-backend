package com.agencyflow.crm.customer.repository;

import com.agencyflow.crm.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByDeletedFalse();

    Optional<Customer> findByIdAndDeletedFalse(Long id);
}