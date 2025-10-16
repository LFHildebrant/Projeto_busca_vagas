CREATE TABLE users (
    id_user INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR (150) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE ,
    password TEXT NOT NULL,
    email VARCHAR(150),
    phone VARCHAR (20),
    experience TEXT,
    education TEXT,
    role VARCHAR(10),
    creation_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
    last_update_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)
);