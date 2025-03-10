-- Usuario único (fake)
INSERT INTO User (id, username, role) VALUES (1, 'bootcamp_user', 'STUDENT');

-- Recurso de ejemplo
INSERT INTO Resource (id, title, category, type, url, uploader_id)
VALUES (1, 'React Basics', 'FRONTEND', 'LINK', 'https://example.com/react', 1);