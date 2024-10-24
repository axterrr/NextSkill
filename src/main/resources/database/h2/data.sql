INSERT INTO roles (id, title) VALUES
    (1, 'STUDENT'),
    (2, 'TEACHER'),
    (3, 'ADMIN');

INSERT INTO users (uuid, username, name, surname, email, phone, avatar_link, description, created_at, is_disabled, password_hash)
VALUES
    (UUID(), 'test_user', 'User', 'Test', 'test_user@test.com', '1234567890', NULL, 'Test data.', NOW(), FALSE, '$2y$10$FMzzXACq4tAE2Bn/o.EN8eL4By9CzB3ZMH7iAGMyOvvHmFszKqym2');

INSERT INTO user_roles (user_id, role_id)
VALUES
    ((SELECT uuid FROM users WHERE username = 'test_user'), 2),
    ((SELECT uuid FROM users WHERE username = 'test_user'), 3);