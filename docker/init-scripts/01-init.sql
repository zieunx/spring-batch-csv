CREATE USER 'batch_local'@'%' IDENTIFIED BY 'Qwer!234';
GRANT ALL PRIVILEGES ON batch.* TO 'batch_local'@'%';
FLUSH PRIVILEGES;
