-- On nettoie le terrain avant de construire
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS equipement;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS habitat;

-- 1. La table des locataires (La base)
CREATE TABLE habitat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    floor INT,
    area DOUBLE,
    resident_name VARCHAR(100),
    appliances_count INT,
    eco_coins INT DEFAULT 0 -- 💰 La fameuse cagnotte écolo !
);

-- 2. La table des utilisateurs (Pour le Login)
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    habitat_id INT NOT NULL,
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);

-- 3. La table des équipements (Liée à l'habitat)
CREATE TABLE equipement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    habitat_id INT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    puissance_watts INT NOT NULL,
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);

-- 4. La table des réservations (Pour le Calendrier !)
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    habitat_id INT NOT NULL,
    date_res DATE NOT NULL,
    heure_res INT NOT NULL,
    puissance_watts INT NOT NULL, -- 🔥 NOUVELLE COLONNE : L'impact réel de l'appareil !
    FOREIGN KEY (habitat_id) REFERENCES habitat(id) ON DELETE CASCADE
);


-- =========================================
-- 📦 INSERTION DES DONNÉES DE TEST (LE FAKE)
-- =========================================

-- Les Locataires
INSERT INTO habitat (floor, area, resident_name, appliances_count, eco_coins) VALUES
(1, 45.5, 'Jeffrey Xatab', 5, 0),
(2, 60.0, 'Sarah Croche', 8, 100),
(3, 30.5, 'Mehdi Maouz', 12, 50);

-- Les Comptes de connexion
-- (Le mot de passe c'est 'azerty' pour tout le monde)
INSERT INTO user (email, password, habitat_id) VALUES
('jeffrey@test.com', '123456', 1),
('sarah@test.com', '123456', 2),
('mehdi@test.com', '123456', 3);

-- Les Équipements de Jeffrey (ID 1)
INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(1, 'Réfrigérateur', 300),
(1, 'Télévision', 150),
(1, 'PlayStation 5', 250);

-- Les Équipements de Sarah (ID 2)
INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(2, 'Four électrique', 2500),
(2, 'Radiateur', 1500),
(2, 'Aspirateur', 800);

-- Les Équipements de Mehdi (ID 3)
INSERT INTO equipement (habitat_id, nom, puissance_watts) VALUES
(3, 'Lave-linge', 2000),
(3, 'Sèche-linge', 2200),
(3, 'PC Gamer', 600);

-- On met une fausse réservation pour demain histoire de tester le calendrier direct
-- 🔥 On inclut la puissance de l'appareil choisi !
INSERT INTO reservation (habitat_id, date_res, heure_res, puissance_watts) VALUES
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 19, 2500), -- Sarah a réservé son Four (2500W) demain à 19h
(3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 19, 2000); -- Mehdi a réservé son Lave-linge (2000W) à la même heure