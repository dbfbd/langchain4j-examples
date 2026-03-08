-- Clear existing data
DELETE FROM orders;
DELETE FROM product;
DELETE FROM customer;
DELETE FROM chat_log;

-- Reset auto increment
ALTER TABLE customer AUTO_INCREMENT = 1;
ALTER TABLE product AUTO_INCREMENT = 1;
ALTER TABLE orders AUTO_INCREMENT = 1;
ALTER TABLE chat_log AUTO_INCREMENT = 1;

-- Customers (5 customers with different VIP levels)
INSERT INTO customer (name, email, phone, vip_level, created_at) VALUES
('Alice Johnson', 'alice@example.com', '13800138001', 3, '2024-01-15 10:00:00'),
('Bob Smith', 'bob@example.com', '13800138002', 2, '2024-02-20 11:00:00'),
('Carol White', 'carol@example.com', '13800138003', 1, '2024-03-10 09:00:00'),
('David Brown', 'david@example.com', '13800138004', 0, '2024-04-05 14:00:00'),
('Emma Davis', 'emma@example.com', '13800138005', 0, '2024-05-12 16:00:00');

-- Products (10 products in different categories)
INSERT INTO product (name, category, price, stock, description) VALUES
('iPhone 15 Pro', 'Electronics', 999.99, 50, 'Latest Apple flagship smartphone with A17 Pro chip, titanium design, and advanced camera system.'),
('Samsung 4K Smart TV 55"', 'Electronics', 699.99, 30, '55-inch 4K UHD Smart TV with HDR, built-in streaming apps, and voice control.'),
('Sony WH-1000XM5 Headphones', 'Electronics', 349.99, 100, 'Industry-leading noise canceling wireless headphones with 30-hour battery life.'),
('Nike Air Max 270', 'Clothing', 149.99, 200, 'Comfortable running shoes with Air Max cushioning for all-day wear.'),
('Levi\'s 501 Original Jeans', 'Clothing', 59.99, 300, 'Classic straight-fit jeans made from 100% cotton denim.'),
('Columbia Waterproof Jacket', 'Clothing', 129.99, 150, 'Lightweight waterproof jacket perfect for outdoor activities.'),
('Organic Green Tea (100 bags)', 'Food', 19.99, 500, 'Premium organic green tea from the mountains of Japan, naturally rich in antioxidants.'),
('Dark Chocolate Gift Box', 'Food', 34.99, 200, 'Assorted premium dark chocolates from Belgium, perfect for gifting.'),
('The Art of Clean Code (Book)', 'Books', 29.99, 400, 'A comprehensive guide to writing clean, maintainable code with practical examples.'),
('Yoga Mat Premium', 'Sports', 49.99, 250, 'Non-slip premium yoga mat made from eco-friendly materials, 6mm thickness.');

-- Orders (8 orders with different statuses)
INSERT INTO orders (order_number, customer_id, product_id, quantity, total_price, status, shipping_address, tracking_number, created_at, updated_at) VALUES
('SS-10001', 1, 1, 1, 999.99, 'COMPLETED', '123 Main St, New York, NY 10001', 'TRK-001-ALICE', '2025-01-10 09:00:00', '2025-01-20 15:00:00'),
('SS-10002', 1, 3, 1, 349.99, 'SHIPPED', '123 Main St, New York, NY 10001', 'TRK-002-ALICE', '2025-02-15 10:00:00', '2025-02-18 08:00:00'),
('SS-10003', 2, 2, 1, 699.99, 'PAID', '456 Oak Ave, Los Angeles, CA 90001', NULL, '2025-03-01 14:00:00', '2025-03-01 14:30:00'),
('SS-10004', 2, 4, 2, 299.98, 'PENDING', '456 Oak Ave, Los Angeles, CA 90001', NULL, '2025-03-05 11:00:00', '2025-03-05 11:00:00'),
('SS-10005', 3, 5, 1, 59.99, 'SHIPPED', '789 Pine Rd, Chicago, IL 60601', 'TRK-005-CAROL', '2025-02-20 09:30:00', '2025-02-22 10:00:00'),
('SS-10006', 3, 9, 1, 29.99, 'COMPLETED', '789 Pine Rd, Chicago, IL 60601', 'TRK-006-CAROL', '2025-01-25 16:00:00', '2025-02-01 12:00:00'),
('SS-10007', 4, 7, 3, 59.97, 'CANCELLED', '321 Elm St, Houston, TX 77001', NULL, '2025-03-03 13:00:00', '2025-03-03 14:00:00'),
('SS-10008', 5, 10, 1, 49.99, 'REFUNDING', '654 Maple Dr, Phoenix, AZ 85001', 'TRK-008-EMMA', '2025-02-28 11:00:00', '2025-03-04 09:00:00');
