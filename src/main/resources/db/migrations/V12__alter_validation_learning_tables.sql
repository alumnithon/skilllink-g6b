ALTER TABLE IF NOT EXISTSchallenges
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE IF NOT EXISTScourses
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE IF NOT EXISTSprojects
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);

ALTER TABLE IF NOT EXISTSmentorships
ADD CONSTRAINT unique_title_creator
UNIQUE (title, created_by);