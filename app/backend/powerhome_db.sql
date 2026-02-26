CREATE TABLE habitat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    floor INT,
    area DOUBLE,
    resident_name VARCHAR(100),
    appliances_count INT
);

INSERT INTO habitat (floor, area, resident_name, appliances_count) VALUES 
(1, 45.5, 'Jeffrey Xatab', 5),
(2, 60.0, 'Sarah Croche', 8),
(3, 30.5, 'Mehdi Maouz', 12);