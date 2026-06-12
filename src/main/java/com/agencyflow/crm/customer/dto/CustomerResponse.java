package com.agencyflow.crm.customer.dto;

public record CustomerResponse(

        Long id,

        String companyName,

        String contactName,

        String email,

        String phone
) {
}