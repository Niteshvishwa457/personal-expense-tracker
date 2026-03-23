-- ==========================================================
-- PERSONAL EXPENSE TRACKER - MYSQL QUERIES
-- ==========================================================

-- 1. USER MANAGEMENT
-- Register a new user
INSERT INTO users (username, email, password) 
VALUES ('johndoe', 'john@example.com', '$2a$10$BCRYPT_HASHED_PASSWORD');

-- Find user by username
SELECT * FROM users WHERE username = 'johndoe';

-- Update user email
UPDATE users SET email = 'john.new@example.com' WHERE username = 'johndoe';


-- 2. CATEGORY MANAGEMENT
-- List all available categories
SELECT * FROM categories;

-- Add a custom category
INSERT INTO categories (category_name) VALUES ('Healthcare');


-- 3. TRANSACTION OPERATIONS
-- Add an income transaction
INSERT INTO transactions (user_id, category_id, amount, type, transaction_date, description)
VALUES (1, (SELECT category_id FROM categories WHERE category_name = 'Salary'), 5000.00, 'INCOME', '2026-03-01', 'Monthly Salary');

-- Add an expense transaction
INSERT INTO transactions (user_id, category_id, amount, type, transaction_date, description)
VALUES (1, (SELECT category_id FROM categories WHERE category_name = 'Food'), 45.50, 'EXPENSE', '2026-03-23', 'Lunch at Cafe');

-- View all transactions for a specific user (descending by date)
SELECT t.transaction_id, t.transaction_date, t.amount, t.type, c.category_name, t.description
FROM transactions t
JOIN categories c ON t.category_id = c.category_id
WHERE t.user_id = 1
ORDER BY t.transaction_date DESC;

-- Filter transactions by date range
SELECT * FROM transactions 
WHERE user_id = 1 
AND transaction_date BETWEEN '2026-03-01' AND '2026-03-31';

-- Filter transactions by category
SELECT t.* 
FROM transactions t
JOIN categories c ON t.category_id = c.category_id
WHERE t.user_id = 1 AND c.category_name = 'Travel';


-- 4. REPORTING & ANALYTICS
-- Calculate Total Income, Total Expenses, and Net Balance for a user
SELECT 
    SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) AS total_income,
    SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) AS total_expenses,
    SUM(CASE WHEN type = 'INCOME' THEN amount ELSE -amount END) AS net_balance
FROM transactions
WHERE user_id = 1;

-- Monthly summary: Total expenses grouped by category (for Charts)
SELECT c.category_name, SUM(t.amount) AS total_spent
FROM transactions t
JOIN categories c ON t.category_id = c.category_id
WHERE t.user_id = 1 AND t.type = 'EXPENSE' 
AND MONTH(t.transaction_date) = 3 AND YEAR(t.transaction_date) = 2026
GROUP BY c.category_name
ORDER BY total_spent DESC;

-- Find the top 5 highest expenses
SELECT * FROM transactions 
WHERE user_id = 1 AND type = 'EXPENSE'
ORDER BY amount DESC
LIMIT 5;

-- Average daily spending for the current month
SELECT AVG(daily_total) FROM (
    SELECT SUM(amount) AS daily_total
    FROM transactions
    WHERE user_id = 1 AND type = 'EXPENSE'
    AND MONTH(transaction_date) = MONTH(CURRENT_DATE())
    GROUP BY transaction_date
) AS daily_stats;


-- 5. DATABASE MAINTENANCE
-- Delete a specific transaction
DELETE FROM transactions WHERE transaction_id = 10;

-- Delete a user and all their associated data (Cascade handles transactions)
DELETE FROM users WHERE user_id = 1;
