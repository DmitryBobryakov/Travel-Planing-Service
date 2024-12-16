CREATE DATABASE auth_service;
CREATE TABLE user_data (
                           id SERIAL PRIMARY KEY,
                           username VARCHAR(50) UNIQUE NOT NULL,
                           password VARCHAR(255) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           phone VARCHAR(20)
);

CREATE INDEX idx_username ON user_data (username);
CREATE INDEX idx_email ON user_data (email);

INSERT INTO user_data (username, password, email, phone)
VALUES
    ('testuser', '$2a$10$5DwJeN9k7CM0XefYGoMR3uuvDwwTCoO9KPO1DsJ5OqDpFjNdW1q3K', 'testuser@example.com', '1234567890');
-- Пароль: password (захеширован с помощью BCrypt)

SELECT * FROM user_data WHERE username = 'testuser';

INSERT INTO user_data (username, password, email, phone)
VALUES ('newuser', '$2a$10$hashedpasswordhere', 'newuser@example.com', '0987654321');

DELETE FROM user_data WHERE id = 1;

UPDATE user_data
SET phone = '1122334455'
WHERE username = 'testuser';
