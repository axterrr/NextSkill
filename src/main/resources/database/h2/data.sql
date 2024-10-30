INSERT INTO users (uuid, username, name, surname, role, email, phone, avatar_link, description, created_at, is_disabled, password_hash)
VALUES (UUID(), 'test_user', 'User', 'Test', 'STUDENT', 'test_user@test.com', '1234567890', NULL, 'Test data.', NOW(), FALSE, '$2a$10$cP/q1CED30Y3LLSpR5dlCO4hki1b5SC2QK8ONdNRsDjREal.yhSQC');
