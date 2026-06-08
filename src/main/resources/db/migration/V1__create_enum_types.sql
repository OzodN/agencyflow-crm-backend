CREATE TYPE role_type AS ENUM (
    'ADMIN',
    'SALES_MANAGER',
    'TEAM_LEAD'
);

CREATE TYPE lead_status AS ENUM (
    'NEW',
    'CONTACTED',
    'QUALIFIED',
    'CONVERTED',
    'REJECTED'
);

CREATE TYPE deal_status AS ENUM (
    'PROPOSAL',
    'NEGOTIATION',
    'WON',
    'LOST'
);

CREATE TYPE project_status AS ENUM (
    'PLANNING',
    'IN_PROGRESS',
    'ON_HOLD',
    'COMPLETED',
    'CANCELLED'
);

CREATE TYPE task_status AS ENUM (
    'TODO',
    'IN_PROGRESS',
    'DONE'
);

CREATE TYPE task_priority AS ENUM (
    'LOW',
    'MEDIUM',
    'HIGH'
);