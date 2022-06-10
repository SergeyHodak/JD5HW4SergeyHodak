ALTER TABLE developer ADD salary number;

ALTER TABLE project ADD cost number;

UPDATE project AS T1
SET T1.cost = (
    SELECT SUM(salary)
    FROM developer AS T2
    WHERE T2.id IN (
        SELECT T3.developer_id
        FROM project_developer AS T3
        WHERE T3.project_id=T1.id
    )
);

ALTER TABLE project ADD creation_date DATE;