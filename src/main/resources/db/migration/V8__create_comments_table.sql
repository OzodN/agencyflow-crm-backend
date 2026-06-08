CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,

                          text TEXT NOT NULL,

                          author_id BIGINT NOT NULL,

                          lead_id BIGINT,
                          deal_id BIGINT,
                          project_id BIGINT,
                          task_id BIGINT,

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_comment_author
                              FOREIGN KEY (author_id)
                                  REFERENCES users(id),

                          CONSTRAINT fk_comment_lead
                              FOREIGN KEY (lead_id)
                                  REFERENCES leads(id),

                          CONSTRAINT fk_comment_deal
                              FOREIGN KEY (deal_id)
                                  REFERENCES deals(id),

                          CONSTRAINT fk_comment_project
                              FOREIGN KEY (project_id)
                                  REFERENCES projects(id),

                          CONSTRAINT fk_comment_task
                              FOREIGN KEY (task_id)
                                  REFERENCES tasks(id),

                          CONSTRAINT chk_comment_target
                              CHECK (
                                  (
                                      CASE WHEN lead_id IS NOT NULL THEN 1 ELSE 0 END +
                                      CASE WHEN deal_id IS NOT NULL THEN 1 ELSE 0 END +
                                      CASE WHEN project_id IS NOT NULL THEN 1 ELSE 0 END +
                                      CASE WHEN task_id IS NOT NULL THEN 1 ELSE 0 END
                                      ) = 1
                                  )
);