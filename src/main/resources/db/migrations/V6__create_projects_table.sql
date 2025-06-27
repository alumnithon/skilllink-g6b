CREATE TABLE IF NOT EXISTSprojects (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_by BIGINT NOT NULL,
    status ENUM('OPEN', 'IN_PROGRESS', 'COMPLETED', 'ARCHIVED') DEFAULT 'OPEN',
    difficulty_level ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BEGINNER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_project_user FOREIGN KEY (created_by) REFERENCES users(id)
);


CREATE TABLE IF NOT EXISTSproject_contributions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    contribution_type ENUM('TASK', 'CODE', 'DOCUMENTATION', 'REVIEW', 'IDEA', 'BUGFIX') DEFAULT 'TASK',
    progress_contributed INT DEFAULT 0 CHECK (progress_contributed BETWEEN 0 AND 100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_contribution_project FOREIGN KEY (project_id) REFERENCES projects(id),
    CONSTRAINT fk_contribution_user FOREIGN KEY (user_id) REFERENCES users(id)
);