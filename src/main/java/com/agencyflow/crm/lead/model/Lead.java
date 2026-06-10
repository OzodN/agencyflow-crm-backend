package com.agencyflow.crm.lead.model;

import com.agencyflow.crm.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeadStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "assigned_sales_manager_id",
            nullable = false
    )
    private User assignedSalesManager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "converted_customer_id")
    private Customer convertedCustomer;

    @Column(name = "converted_at")
    private LocalDateTime convertedAt;

    @Column(nullable = false)
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}