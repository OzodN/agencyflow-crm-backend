INSERT INTO users (first_name,
                   last_name,
                   email,
                   password,
                   role,
                   active
)
VALUES (
        'System',
        'Administrator',
        'admin@agencyflow.com',
        '$2a$12$YiPuNatZJeu2y8t4P20ZWOzNGO5qgonx7MwW3W3Yw8Mk8I8SUgaKu',
        'ADMIN',
        true
       );