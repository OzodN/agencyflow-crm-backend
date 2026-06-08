CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,

                       title VARCHAR(255) NOT NULL,
                       description TEXT,

                       project_id BIGINT NOT NULL,
                       assignee_id BIGINT NOT NULL,

                       status task_status NOT NULL,
                       priority task_priority NOT NULL,

                       due_date DATE,

                       deleted BOOLEAN NOT NULL DEFAULT FALSE,
                       deleted_at TIMESTAMP,

                       created_by BIGINT NOT NULL,
                       updated_by BIGINT,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_task_project
                           FOREIGN KEY (project_id)
                               REFERENCES projects(id),

                       CONSTRAINT fk_task_assignee
                           FOREIGN KEY (assignee_id)
                               REFERENCES users(id),

                       CONSTRAINT fk_task_created_by
                           FOREIGN KEY (created_by)
                               REFERENCES users(id),

                       CONSTRAINT fk_task_updated_by
                           FOREIGN KEY (updated_by)
                               REFERENCES users(id),

                       CONSTRAINT chk_task_deleted
                           CHECK (
                               (deleted = TRUE AND deleted_at IS NOT NULL)
                                   OR
                               (deleted = FALSE AND deleted_at IS NULL)
                               )
);