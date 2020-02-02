use finance_tracker;
CREATE TABLE transactions(
	user_id INT,
	transaction_id INT PRIMARY KEY AUTO_INCREMENT,
	transaction_sum DECIMAL(11, 2),
	transaction_category VARCHAR(30),
	transaction_note VARCHAR(100) DEFAULT "",
	transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
	account_id INT,
	FOREIGN KEY (user_id) REFERENCES users(user_id)
	ON DELETE CASCADE,
	FOREIGN KEY(account_id) REFERENCES accounts(account_id)
	ON DELETE CASCADE
);

INSERT INTO transactions (user_id, transaction_sum, transaction_category,
			 transaction_note, transaction_date, account_id) 
VALUES (1, +20120.00, "SALARY", "finally_some_money", '2020-01-23 13:17:17', 2);


INSERT INTO transactions (user_id, transaction_sum, transaction_category,
			 transaction_note, account_id) 
VALUES(2, -12.50, "FOOD", "quite_expensive_bread", 3);


INSERT INTO transactions (user_id, transaction_sum, transaction_category, account_id) 
VALUES(2, -12.50, "FOOD", 3);


INSERT INTO transactions(user_id, transaction_sum, transaction_category, 
			 transaction_date, account_id) 
VALUES (3, 313.00, "DEPOSITS", '2020-01-19 16:37:20', 5);


INSERT INTO transactions(user_id, transaction_sum, transaction_category,
 			 transaction_date, account_id) 
VALUES (1, -56.00, "BILLS", '2020-01-08 02:09:41', 2);


INSERT INTO transactions(user_id, transaction_sum, transaction_category,
			 transaction_date, account_id)
VALUES (1, -34.67, "EATIN_OUT", '2020-01-12 04:49:10', 1);


INSERT INTO transactions(user_id, transaction_sum, transaction_category,
			 transaction_note, transaction_date, account_id)
VALUES (2, -124.67, "HOBBIES", "nice_new_bat_is_always_a_good_waste_of_money",
	'2020-01-12 04:54:12', 3);


INSERT INTO transactions(user_id, transaction_sum, transaction_category, 
			 transaction_note, transaction_date, account_id)
VALUES (3, -212.23, "CLOTHES", "poor_but_stunning", '2020-01-09 02:03:02', 5);


INSERT INTO transactions(user_id, transaction_sum, transaction_category,
			 transaction_note, account_id) 
VALUES (2, +212.23, "SAVINGS", 
	"what_can_be_better_than_finding_money_in_and_old_jacket", 5);


UPDATE transactions 
SET transaction_note = "what_can_be_better_than_finding_money_in_an_old_jacket" 
WHERE transaction_id = 10;

UPDATE transactions 
SET transaction_category = "TAXI" 
WHERE (transaction_id = 3 AND user_id = 2);

UPDATE transactions 
SET transaction_note = "don't_wanna_leave_notes_empty" 
WHERE (transaction_note = "" AND user_id = 1);

UPDATE transactions 
SET transaction_category = "EATING_OUT" 
WHERE transaction_category = "EATIN_OUT";

DELETE FROM transactions 
WHERE transaction_date BETWEEN '2020-01-12 00:00:00' AND '2020-01-12 23:59:59';

DELETE FROM transactions 
WHERE transaction_id = 3;

DELETE FROM transactions 
WHERE (user_id = 1 AND transaction_id = 6);

DELETE FROM transactions 
WHERE transaction_category IN("FOOD", "CLOTHES");
