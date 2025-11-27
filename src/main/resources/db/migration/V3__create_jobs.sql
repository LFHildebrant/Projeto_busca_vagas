CREATE TABLE jobs (
                           id_job INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
                           company_id INT NOT NULL,
                           title VARCHAR (150) NOT NULL,
                           area VARCHAR(100) NOT NULL,
                           description TEXT NOT NULL,
                           state VARCHAR(2) NOT NULL,
                           city VARCHAR (255) NOT NULL,
                           salary NUMERIC,
                           creation_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
                           last_update_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                           FOREIGN KEY (company_id) REFERENCES companies(id_company)
);