CREATE TABLE projects (
                          id BIGSERIAL PRIMARY KEY,

                          name VARCHAR(255) NOT NULL,
                          description TEXT,

                          deal_id BIGINT NOT NULL UNIQUE,

                          sales_manager_id BIGINT NOT NULL,
                          team_lead_id BIGINT NOT NULL,

                          status project_status NOT NULL,

                          deleted BOOLEAN NOT NULL DEFAULT FALSE,
                          deleted_at TIMESTAMP,

                          created_by BIGINT NOT NULL,
                          updated_by BIGINT,

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_project_deal
                              FOREIGN KEY (deal_id)
                                  REFERENCES deals(id),

                          CONSTRAINT fk_project_sales_manager
                              FOREIGN KEY (sales_manager_id)
                                  REFERENCES users(id),

                          CONSTRAINT fk_project_team_lead
                              FOREIGN KEY (team_lead_id)
                                  REFERENCES users(id),

                          CONSTRAINT fk_lead_created_by
                              FOREIGN KEY (created_by)
                                  REFERENCES users(id),

                          CONSTRAINT fk_lead_updated_by
                              FOREIGN KEY (updated_by)
                                  REFERENCES users(id),

                          CONSTRAINT chk_lead_deleted
                              CHECK (
                                  (deleted = TRUE AND deleted_at IS NOT NULL)
                                      OR
                                  (deleted = FALSE AND deleted_at IS NULL)
                                  )
);