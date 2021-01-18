create database taxiStorage

create table taxiStorage.manufacturers (
id int not null auto_increment,
primary key(id),
name varchar (50) not null,
country varchar(50) not null,
deleted tinyint not null
)