create database finance_tracker;
use finance_tracker;
 CREATE TABLE users(
	 user_id INT PRIMARY KEY AUTO_INCREMENT,
	 user_log_in VARCHAR(30) UNIQUE,
	 user_password INT
);

 INSERT INTO users (user_log_in, user_password) 
 VALUES ("natik@nat.com", 345678902);

 INSERT INTO users (user_log_in, user_password) 
 VALUES ("mike_hernandez", 56872306);

 INSERT INTO users (user_log_in, user_password) 
 VALUES ("buyash@smeshariki.ru", 926572947);

 INSERT INTO users (user_log_in, user_password) 
 VALUES ("krosh@smeshariki.ru", -825463727);

 INSERT INTO users (user_log_in, user_password) 
 VALUES ("jeffree_star", -264823947);

 UPDATE users 
 SET user_log_in = "jeffree_starrrrr" 
 WHERE user_log_in = "jeffree_star";

 UPDATE users
 SET user_log_in = "natalie@nat.com"
 WHERE user_id = 1;

 UPDATE users 
 SET user_password = 123456789 
 WHERE user_log_in = "jeffree_starrrrr";

 UPDATE users
 SET user_password = 87623469
 WHERE user_id = 3;

 DELETE FROM users
 WHERE user_log_in = "krosh@smeshariki.ru";

 DELETE FROM users
 WHERE user_id = 5;

