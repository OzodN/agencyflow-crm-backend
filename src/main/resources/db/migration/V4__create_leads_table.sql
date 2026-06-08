CREATE TABLE leads (
                       id BIGSERIAL PRIMARY KEY,

                       company_name VARCHAR(255) NOT NULL,
                       contact_name VARCHAR(255) NOT NULL,

                       email VARCHAR(255),
                       phone VARCHAR(50),

                       status lead_status NOT NULL,

                       assigned_sales_manager_id BIGINT NOT NULL,

                       converted_customer_id BIGINT,
                       converted_at TIMESTAMP,

                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       deleted_at TIMESTAMP,

                       created_by BIGINT NOT NULL,
                       updated_by BIGINT,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_lead_sales_manager
                           FOREIGN KEY (assigned_sales_manager_id)
                               REFERENCES users(id),

                       CONSTRAINT fk_lead_customer
                           FOREIGN KEY (converted_customer_id)
                               REFERENCES customers(id),

                       CONSTRAINT fk_lead_created_by
                           FOREIGN KEY (created_by)
                               REFERENCES users(id),

                       CONSTRAINT fk_lead_updated_by
                           FOREIGN KEY (updated_by)
                               REFERENCES users(id),

                       CONSTRAINT chk_lead_conversion
                           CHECK (
                               (
                                   status = 'CONVERTED'
                                       AND converted_customer_id IS NOT NULL
                                       AND converted_at IS NOT NULL
                                   )
                                   OR
                               (
                                   status <> 'CONVERTED'
                                       AND converted_customer_id IS NULL
                                       AND converted_at IS NULL
                                   )
                               ),

                       CONSTRAINT chk_lead_deleted
                           CHECK (
                               (deleted = TRUE AND deleted_at IS NOT NULL)
                                   OR
                               (deleted = FALSE AND deleted_at IS NULL)
                               )
);