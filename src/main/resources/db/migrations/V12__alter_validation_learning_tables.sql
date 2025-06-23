ALTER TABLE challenges
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE courses
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE projects
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE mentorships
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);