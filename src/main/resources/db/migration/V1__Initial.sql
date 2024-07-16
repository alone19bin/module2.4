CREATE TABLE users (
                       id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255));

CREATE TABLE files (
                       id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(255),
                       file_path VARCHAR(255));

CREATE TABLE events (
                        id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        created TIMESTAMP,
                        name VARCHAR(255),
                        file_id INTEGER,
                        user_id INTEGER,
                        FOREIGN KEY (file_id) REFERENCES files (id),
                        FOREIGN KEY (user_id) REFERENCES users (id));