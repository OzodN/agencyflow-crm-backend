-- Leads
CREATE INDEX idx_leads_status
    ON leads(status);

CREATE INDEX idx_leads_sales_manager
    ON leads(assigned_sales_manager_id);

CREATE INDEX idx_leads_company
    ON leads(company_name);

-- Customers
CREATE INDEX idx_customers_company
    ON customers(company_name);

-- Deals
CREATE INDEX idx_deals_customer
    ON deals(customer_id);

CREATE INDEX idx_deals_sales_manager
    ON deals(sales_manager_id);

CREATE INDEX idx_deals_status
    ON deals(status);

-- Projects
CREATE INDEX idx_projects_team_lead
    ON projects(team_lead_id);

CREATE INDEX idx_projects_status
    ON projects(status);

-- Tasks
CREATE INDEX idx_tasks_project
    ON tasks(project_id);

CREATE INDEX idx_tasks_assignee
    ON tasks(assignee_id);

CREATE INDEX idx_tasks_status
    ON tasks(status);

CREATE INDEX idx_tasks_due_date
    ON tasks(due_date);

-- Comments
CREATE INDEX idx_comments_lead
    ON comments(lead_id);

CREATE INDEX idx_comments_deal
    ON comments(deal_id);

CREATE INDEX idx_comments_project
    ON comments(project_id);

CREATE INDEX idx_comments_task
    ON comments(task_id);

-- Audit Logs
CREATE INDEX idx_audit_entity
    ON audit_logs(entity_type, entity_id);

CREATE INDEX idx_audit_user
    ON audit_logs(user_id);

CREATE INDEX idx_audit_created_at
    ON audit_logs(created_at);

-- Soft delete tables
CREATE INDEX idx_leads_deleted
    ON leads(deleted);

CREATE INDEX idx_customers_deleted
    ON customers(deleted);

CREATE INDEX idx_deals_deleted
    ON deals(deleted);

CREATE INDEX idx_projects_deleted
    ON projects(deleted);

CREATE INDEX idx_tasks_deleted
    ON tasks(deleted);