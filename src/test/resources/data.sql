-- Nettoyer les tables
DELETE FROM category;
DELETE FROM voyage;

-- Insérer des voyages
INSERT INTO voyage (id, destination, prix_de_base) VALUES
(1, 'Paris', 100.0),
(2, 'New York', 200.0),
(3, 'Tokyo', 300.0);

-- Insérer des catégories (associées aux voyages)
INSERT INTO category (type, capacite_totale, places_reservees, voyage_id) VALUES
('ECONOMY', 100, 10, 1), -- Voyage 1
('BUSINESS', 20, 5, 1),  -- Voyage 1
('FIRST_CLASS', 5, 2, 2), -- Voyage 2
('PREMIUM_ECONOMY', 50, 15, 3); -- Voyage 3
