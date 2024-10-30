INSERT INTO users (uuid, username, name, surname, role, email, phone, avatar_link, description, created_at, is_disabled, password_hash)
VALUES (UUID(), 'test_user', 'User', 'Test', 'STUDENT', 'test_user@test.com', '1234567890', NULL, 'Test data.', NOW(), FALSE, '$2y$10$FMzzXACq4tAE2Bn/o.EN8eL4By9CzB3ZMH7iAGMyOvvHmFszKqym2');
