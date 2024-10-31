INSERT INTO roles (id, title) VALUES
    (1, 'STUDENT'),
    (2, 'TEACHER'),
    (3, 'ADMIN');

INSERT INTO users (uuid, username, name, surname, email, phone, avatar_link, description, created_at, is_disabled, password_hash)
VALUES
    (UUID(), 'test_user', 'User', 'Test', 'test_user@test.com', '1234567890', NULL, 'Test data.', NOW(), FALSE, '$2y$10$lack10NYc8XCjDBQygtmZODE3da6yELCQof5cdifRsILycTG4ShNi');

INSERT INTO user_roles (user_id, role_id)
VALUES
    ((SELECT uuid FROM users WHERE username = 'test_user'), 2),
    ((SELECT uuid FROM users WHERE username = 'test_user'), 3);