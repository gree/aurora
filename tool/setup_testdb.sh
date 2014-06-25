#!/bin/sh

echo "create database test_a;" | mysql -uroot
echo "create database test_b;" | mysql -uroot

echo "use test_a; CREATE TABLE user (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, PRIMARY KEY (id));" | mysql -uroot
echo "use test_b; CREATE TABLE user (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(45) NOT NULL, PRIMARY KEY (id));" | mysql -uroot

echo "use test_a; INSERT INTO user (name) VALUES ('test_1');" | mysql -uroot
echo "use test_a; INSERT INTO user (name) VALUES ('test_3');" | mysql -uroot

echo "use test_b; INSERT INTO user (name) VALUES ('test_2');" | mysql -uroot
echo "use test_b; INSERT INTO user (name) VALUES ('test_4');" | mysql -uroot

