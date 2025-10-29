drop table if exists member;
create table MEMBER(
   loginId varchar(255) not null primary key ,
   password varchar(255) not null ,
   name varchar(255) not null ,
   birthday date not null
);