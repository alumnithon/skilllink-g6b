CREATE TABLE  notifications (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL, -- receptor de la notificación
    type VARCHAR(50) NOT NULL, -- ejemplo: 'MENTOR_REQUEST', 'MESSAGE', 'COURSE_PROGRESS'
    title VARCHAR(255) NOT NULL,
    message TEXT,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP NULL,

    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id)
);