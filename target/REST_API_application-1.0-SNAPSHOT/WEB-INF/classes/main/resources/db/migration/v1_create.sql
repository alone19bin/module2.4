CREATE TABLE users (
                       id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255));

CREATE TABLE files (
                       id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255),
                       file_path VARCHAR(255));

CREATE TABLE events (
                        id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        created TIMESTAMP,
                        name VARCHAR(255),
                        file_id INT,
                        user_id INT,
                        FOREIGN KEY (file_id) REFERENCES files (id),
                        FOREIGN KEY (user_id) REFERENCES users (id));