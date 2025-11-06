CREATE TABLE companies (
                           id_company INT NOT NULL UNIQUE AUTO_INCREMENT PRIMARY KEY,
                           address_id INT NOT NULL,
                           name VARCHAR (150) NOT NULL,
                           username VARCHAR(20) NOT NULL UNIQUE ,
                           password TEXT NOT NULL,
                           email VARCHAR(150) NOT NULL,
                           phone VARCHAR (20) NOT NULL,
                           business VARCHAR(150) NOT NULL,
                           role VARCHAR(10),
                           creation_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6),
                           last_update_time_stamp TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
                           FOREIGN KEY (address_id) REFERENCES address(id_address)
);