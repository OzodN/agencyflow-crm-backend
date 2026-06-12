package com.agencyflow.crm.customer.dto;

public record UpdateCustomerRequest(

        String companyName,

        String contactName,

        String email,

        String phone
) {
}
