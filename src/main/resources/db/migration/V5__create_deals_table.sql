CREATE TABLE deals (
                       id BIGSERIAL PRIMARY KEY,

                       title VARCHAR(255) NOT NULL,
                       description TEXT,

                       customer_id BIGINT NOT NULL,
                       sales_manager_id BIGINT NOT NULL,

                       status deal_status NOT NULL,

                       estimated_value NUMERIC(12,2) NOT NULL,

                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       deleted_at TIMESTAMP,

                       created_by BIGINT NOT NULL,
                       updated_by BIGINT,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_deal_customer
                           FOREIGN KEY (customer_id)
                               REFERENCES customers(id),

                       CONSTRAINT fk_deal_sales_manager
                           FOREIGN KEY (sales_manager_id)
                               REFERENCES users(id),

                       CONSTRAINT fk_lead_created_by
                           FOREIGN KEY (created_by)
                               REFERENCES users(id),

                       CONSTRAINT fk_lead_updated_by
                           FOREIGN KEY (updated_by)
                               REFERENCES users(id),

                       CONSTRAINT chk_estimated_value
                           CHECK (estimated_value > 0),

                       CONSTRAINT chk_deals_deleted
                           CHECK (
                               (deleted = TRUE AND deleted_at IS NOT NULL)
                                   OR
                               (deleted = FALSE AND deleted_at IS NULL)
                               )
);