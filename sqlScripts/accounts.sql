use finance_tracker;
 CREATE TABLE accounts(
	user_id INT, 
	account_id INT PRIMARY KEY AUTO_INCREMENT, 
	account_name VARCHAR(30), 
	account_balance DECIMAL (11, 2), 
	FOREIGN KEY (user_id) REFERENCES users(user_id)
	ON DELETE CASCADE
);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(1, "student_card", 242.25);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(1, "visa_gold", 10004582.25);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(2, "Cash", 18.35);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(1, "payment_card", -703.47);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(3, "natik_card", 18.35);

INSERT INTO accounts(user_id, account_name, account_balance) 
VALUES (2, "not_a_student_anymore_card", 127493.50);

INSERT INTO accounts(user_id, account_name, account_balance)
VALUES(3, "Cash", 0.00);

UPDATE accounts 
SET account_name = "visa_very_gold" 
WHERE account_id = 2;

UPDATE accounts 
SET account_balance = -524.00 
WHERE (user_id = 1 AND account_id = 4);

UPDATE accounts 
SET account_name = "poor_me", account_balance = -612.75 
WHERE account_id = 5;

DELETE FROM accounts 
WHERE account_id = 4;

DELETE FROM accounts 
WHERE (user_id = 3 AND account_id = 7);