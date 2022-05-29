CREATE TABLE companies  (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    description VARCHAR(200)
);

CREATE TABLE customers (
    id IDENTITY PRIMARY KEY,
    name_surname VARCHAR(100),
    age INT,
    CHECK(0 < age and age < 150)
);

CREATE TABLE projects (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    company_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL
);

ALTER TABLE projects
ADD CONSTRAINT company_id_fk
FOREIGN KEY(company_id)
REFERENCES companies(id);

ALTER TABLE projects
ADD CONSTRAINT customer_id_fk
FOREIGN KEY(customer_id)
REFERENCES customers(id);

CREATE TABLE developers (
    id IDENTITY PRIMARY KEY,
    name_surname VARCHAR(100),
    age INT,
    gender VARCHAR(10) NOT NULL,
    CHECK(0 < age and age < 150),
    CHECK(gender IN('male', 'female'))
);

CREATE TABLE projects_developers (
    project_id BIGINT NOT NULL,
    developer_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, developer_id),
    FOREIGN KEY(project_id) REFERENCES projects(id),
    FOREIGN KEY(developer_id) REFERENCES developers(id)
);

CREATE TABLE skills (
    id IDENTITY PRIMARY KEY,
    department VARCHAR(50),
    skill_level VARCHAR(50)
);

CREATE TABLE developers_skills (
    developer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (developer_id, skill_id),
    FOREIGN KEY(developer_id) REFERENCES developers(id),
    FOREIGN KEY(skill_id) REFERENCES skills(id)
);