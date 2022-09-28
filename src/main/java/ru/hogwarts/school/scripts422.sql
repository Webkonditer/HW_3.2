CREATE TABLE drivers (
                         id INT PRIMARY KEY,
                         name VARCHAR(100),
                         age INT,
                         license BOOLEAN
);
CREATE TABLE cars (
                      id INT PRIMARY KEY,
                      make VARCHAR(100),
                      model VARCHAR(100),
                      cost NUMERIC(9,2),
                      driver_id INT REFERENCES drivers (id)
);