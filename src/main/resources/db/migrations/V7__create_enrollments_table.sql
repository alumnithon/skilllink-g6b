-- tabla que permite unirse a tipos de contenidos distintos (PROJECT, COURSE, CHALLENGE)
CREATE TABLE enrollments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    content_type ENUM('PROJECT', 'COURSE', 'CHALLENGE') NOT NULL,
    content_id BIGINT NOT NULL,
    progress INT DEFAULT 0 CHECK (progress BETWEEN 0 AND 100),
    completed BOOLEAN DEFAULT FALSE,
    completed_at TIMESTAMP,
    UNIQUE(user_id, content_type, content_id),
    CONSTRAINT fk_enrollment_user FOREIGN KEY (user_id) REFERENCES users(id)
);