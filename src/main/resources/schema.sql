drop table if exists member;
create table MEMBER
(
    id       int not null primary key,
    loginId  varchar(255) not null,
    password varchar(255) not null,
    name     varchar(255) not null,
    birthday date         not null,
    mobile varchar(255) not null,
    email varchar(255) not null,
    gender enum('MALE', 'FEMALE'),
    createdAt timestamp   not null,
    updatedAt timestamp   not null
);