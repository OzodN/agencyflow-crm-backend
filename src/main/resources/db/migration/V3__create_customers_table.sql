CREATE TABLE customers (
                           id BIGSERIAL PRIMARY KEY,

                           company_name VARCHAR(255) NOT NULL,
                           contact_name VARCHAR(255) NOT NULL,

                           email VARCHAR(255),
                           phone VARCHAR(50),

                           created_by BIGINT NOT NULL,
                           updated_by BIGINT,

                           deleted BOOLEAN NOT NULL DEFAULT FALSE,
                           deleted_at TIMESTAMP,

                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                           CONSTRAINT fk_customer_created_by
                               FOREIGN KEY (created_by)
                                   REFERENCES users(id),

                           CONSTRAINT fk_customer_updated_by
                               FOREIGN KEY (updated_by)
                                   REFERENCES users(id),

                           CONSTRAINT chk_customer_deleted
                               CHECK (
                                   (deleted = TRUE AND deleted_at IS NOT NULL)
                                       OR
                                   (deleted = FALSE AND deleted_at IS NULL)
                                   )
);