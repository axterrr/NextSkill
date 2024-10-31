INSERT INTO users (uuid, username, name, surname, role, email, phone, avatar_link, description, created_at, is_disabled, password_hash) VALUES
('00000000-0000-0000-0000-000000000000', 'student', 'Student', 'Test', 'STUDENT', 'student@test.com', '1234567890', NULL, 'Test student.', NOW(), FALSE, '$2a$10$cP/q1CED30Y3LLSpR5dlCO4hki1b5SC2QK8ONdNRsDjREal.yhSQC'),
('00000000-0000-0000-0000-000000000001', 'teacher', 'Teacher', 'Test', 'TEACHER', 'teacher@test.com', '1234567891', NULL, 'Test teacher.', NOW(), FALSE, '$2a$10$cP/q1CED30Y3LLSpR5dlCO4hki1b5SC2QK8ONdNRsDjREal.yhSQC'),
('00000000-0000-0000-0000-000000000002', 'admin', 'Admin', 'Test', 'ADMIN', 'admin@test.com', '1234567892', NULL, 'Test data.', NOW(), FALSE, '$2a$10$cP/q1CED30Y3LLSpR5dlCO4hki1b5SC2QK8ONdNRsDjREal.yhSQC');
