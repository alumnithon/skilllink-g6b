CREATE TABLE IF NOT EXISTSchallenges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    difficulty_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') NOT NULL,
    created_by BIGINT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deadline DATE, -- Fecha límite para completar el desafío
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTScontent_tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content_type ENUM('COURSE', 'PROJECT', 'MENTORSHIP', 'CHALLENGE') NOT NULL,
    content_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES tags(id),
    UNIQUE (content_type, content_id, tag_id)
);

ALTER TABLE IF NOT EXISTScourses
ADD COLUMN enabled BOOLEAN NOT NULL DEFAULT TRUE;