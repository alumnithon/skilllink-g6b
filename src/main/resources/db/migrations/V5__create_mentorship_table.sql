CREATE TABLE IF NOT EXISTS mentorships (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    scheduled_at DATETIME NOT NULL, -- Fecha programada
    duration_minutes INT NOT NULL DEFAULT 60, -- Duración estimada
    status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mentorship_user FOREIGN KEY (created_by) REFERENCES users(id)
);

-- Tabla: preguntas de mentoría
CREATE TABLE IF NOT EXISTS mentorship_questions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    mentorship_id BIGINT NOT NULL,
    asked_by BIGINT NOT NULL,
    question TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_question_mentorship FOREIGN KEY (mentorship_id) REFERENCES mentorships(id) ON DELETE CASCADE,
    CONSTRAINT fk_question_user FOREIGN KEY (asked_by) REFERENCES users(id)
);

-- Tabla: respuestas de mentoría
CREATE TABLE IF NOT EXISTS mentorship_answers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    answered_by BIGINT NOT NULL,
    answer TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_answer_question FOREIGN KEY (question_id) REFERENCES mentorship_questions(id) ON DELETE CASCADE,
    CONSTRAINT fk_answer_user FOREIGN KEY (answered_by) REFERENCES users(id)
);