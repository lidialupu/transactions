CREATE TABLE Client-Info(
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  sum_value INT(100000) NOT NULL,
  customer_name VARCHAR(500) NOT NULL,
  transaction_type VARCHAR(5000) NOT NULL
);