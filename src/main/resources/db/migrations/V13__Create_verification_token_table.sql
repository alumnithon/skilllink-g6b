CREATE TABLE verification_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date DATETIME NOT NULL,
    CONSTRAINT fk_verification_token_user
        FOREIGN KEY (user_id) 
        REFERENCES user(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_verification_token_token ON verification_token(token);
CREATE INDEX idx_verification_token_expiry ON verification_token(expiry_date);