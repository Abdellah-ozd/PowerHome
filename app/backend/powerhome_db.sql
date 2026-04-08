
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS equipement;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS habitat;

-- 1. Ttable des locataires)
CREATE TABLE habitat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    floor INT,
    area DOUBLE,
    resident_name VARCHAR(100),
    appliances_count INT,
    eco_coins INT DEFAULT 0
);

-- 2. Table des utilisateurs
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    habitat_id INT NOT NULL,
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);

-- 3. Table des équipements
CREATE TABLE equipement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    habitat_id INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    puissance_watts INT NOT NULL,
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);
-- 4. Table des réservations
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    habitat_id INT NOT NULL,
    date_res DATE NOT NULL,
    heure_res INT NOT NULL,
    puissance_watts INT NOT NULL,
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);


-- =========================================
--  INSERTION DES DONNÉES DE TEST
-- =========================================

-- Les Locataires
INSERT INTO habitat (floor, area, resident_name, appliances_count, eco_coins) VALUES
(1, 45.5, 'Jeffrey Xatab', 5, 0),
(2, 60.0, 'Sarah Croche', 8, 100),
(3, 30.5, 'Mehdi Maouz', 12, 50);

-- Les Comptes de connexion
INSERT INTO user (email, password, habitat_id) VALUES
('jeffrey@test.com', '123456', 1),
('sarah@test.com', '123456', 2),
('mehdi@test.com', '123456', 3);

INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(1, 'Réfrigérateur', 300),
(1, 'Télévision', 150),
(1, 'PlayStation 5', 250);


INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(2, 'Four électrique', 2500),
(2, 'Radiateur', 1500),
(2, 'Aspirateur', 800);

INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(3, 'Lave-linge', 2000),
(3, 'Sèche-linge', 2200),
(3, 'PC Gamer', 600);

-- Fausse réservation pour le calendrier
INSERT INTO reservation (habitat_id, date_res, heure_res, puissance_watts) VALUES
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 19, 2500),
(3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 19, 2000);