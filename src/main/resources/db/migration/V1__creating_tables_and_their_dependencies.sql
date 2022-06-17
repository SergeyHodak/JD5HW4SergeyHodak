CREATE TABLE company  (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200),
    description VARCHAR(200)
);

CREATE TABLE customer (
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(50),
    second_name VARCHAR(50),
    age INT,
    CHECK(0 <= age and age <= 150)
);

CREATE TABLE project (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    company_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY(company_id) REFERENCES company(id),
    FOREIGN KEY(customer_id) REFERENCES customer(id)
);

CREATE TABLE developer (
    id IDENTITY PRIMARY KEY,
    first_name VARCHAR(50),
    second_name VARCHAR(50),
    age INT,
    gender VARCHAR(10) NOT NULL,
    CHECK(0 <= age and age <= 150),
    CHECK(gender IN('MALE', 'FEMALE'))
);

CREATE TABLE project_developer (
    project_id VARCHAR(200) NOT NULL,
    developer_id BIGINT NOT NULL,
    FOREIGN KEY(project_id) REFERENCES project(id),
    FOREIGN KEY(developer_id) REFERENCES developer(id)
);

CREATE TABLE skill (
    id IDENTITY PRIMARY KEY,
    department VARCHAR(50),
    skill_level VARCHAR(50)
);

CREATE TABLE developer_skill (
    developer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    FOREIGN KEY(developer_id) REFERENCES developer(id),
    FOREIGN KEY(skill_id) REFERENCES skill(id)
);