CREATE TABLE applications (
    id_application  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    job_id INT NOT NULL,
    name VARCHAR(150),
    email VARCHAR(200),
    phone VARCHAR(20),
    education TEXT NOT NULL,
    experience TEXT NOT NULL,
    creation_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    FOREIGN KEY (user_id) REFERENCES users(id_user),
    FOREIGN KEY (job_id) REFERENCES jobs(id_job)
);