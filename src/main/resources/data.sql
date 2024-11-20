-- Insérer des voyages
INSERT INTO voyage (destination, prix_de_base) VALUES
('Paris', 100.0),
('New York', 200.0),
('Tokyo', 300.0);

-- Insérer des catégories (associées aux voyages)
INSERT INTO category (type, capacite_totale, places_reservees, voyage_id) VALUES
('ECONOMY', 100, 10, 1), -- Voyage 1
('BUSINESS', 20, 5, 1),  -- Voyage 1
('FIRST_CLASS', 5, 2, 2), -- Voyage 2
('PREMIUM_ECONOMY', 50, 15, 3); -- Voyage 3

-- Insérer des voyageurs
INSERT INTO voyageur (id, nom, email, date_naissance, telephone, date_reservation, category_id) VALUES
(1, 'Alice Dupont', 'alice.dupont@example.com', '1985-07-15', '0610203040', '2024-11-18', 1), -- ECONOMY
(2, 'Bob Martin', 'bob.martin@example.com', '1990-03-25', '0611223344', '2024-11-19', 2),    -- BUSINESS
(3, 'Charlie Smith', 'charlie.smith@example.com', '1995-06-30', '0613344556', '2024-11-20', 3), -- FIRST_CLASS
(4, 'Diana Jones', 'diana.jones@example.com', '2000-12-12', '0614455667', '2024-11-19', 4);  -- PREMIUM_ECONOMY