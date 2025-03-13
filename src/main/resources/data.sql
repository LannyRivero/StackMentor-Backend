-- Usuario único (fake)
INSERT INTO User (id, username, role) VALUES (1, 'bootcamp_user', 'STUDENT');



INSERT INTO resources (title, description, category, subcategory, url) 
VALUES 
    ('HTML Basics', 'Curso de HTML', 'Frontend', 'HTML', 'https://example.com/html'),
    ('CSS Flexbox', 'Guía de Flexbox', 'Frontend', 'CSS', 'https://example.com/css'),
    ('ES6 Features', 'Conceptos de ES6', 'Frontend', 'JavaScript', 'https://example.com/es6'),
    ('React Hooks', 'Uso de hooks en React', 'Frontend', 'React', 'https://example.com/react'),
    ('Spring Boot API', 'Creando APIs REST', 'Backend', 'Java con Spring Boot', 'https://example.com/springboot'),
    ('JUnit para Java', 'Pruebas en Spring Boot', 'Testing', 'JUnit', 'https://example.com/junit'),
    ('Hamcrest Matchers', 'Uso de matchers en JUnit', 'Testing', 'Hamcrest', 'https://example.com/hamcrest');
