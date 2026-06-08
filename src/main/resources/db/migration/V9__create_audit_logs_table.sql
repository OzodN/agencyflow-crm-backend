CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,

                            user_id BIGINT NOT NULL,

                            action VARCHAR(100) NOT NULL,

                            entity_type VARCHAR(50) NOT NULL,
                            entity_id BIGINT NOT NULL,

                            old_value JSONB,
                            new_value JSONB,

                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            CONSTRAINT fk_audit_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
);