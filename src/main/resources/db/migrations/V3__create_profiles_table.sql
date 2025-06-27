
CREATE TABLE IF NOT EXISTSprofiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    bio TEXT,
    location VARCHAR(100),
    occupation VARCHAR(100),
    experience TEXT,
    visibility VARCHAR(10) NOT NULL DEFAULT 'PUBLIC',
    contact_email VARCHAR(100),
    contact_phone VARCHAR(20),
    country_id INTEGER,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_profile_pais FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE SET NULL
);


CREATE TABLE IF NOT EXISTSprofile_certifications (
    profile_id BIGINT NOT NULL,
    title VARCHAR(120) NOT NULL,
    certification_url TEXT NULL,
    PRIMARY KEY (profile_id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTSprofile_social_links (
    profile_id BIGINT NOT NULL,
    platform VARCHAR(50) NOT NULL,
    url TEXT NOT NULL,
    PRIMARY KEY (profile_id, platform),
    FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTSprofile_skills (
    profile_id BIGINT NOT NULL,
    skill VARCHAR(100) NOT NULL,
    CONSTRAINT fk_profile_skill FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);


-- Tabla para intereses
CREATE TABLE IF NOT EXISTSprofile_interests (
    profile_id BIGINT NOT NULL PRIMARY KEY,
    interest VARCHAR(255),
    CONSTRAINT fk_interest_profile FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE
);
