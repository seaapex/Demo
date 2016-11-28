-- This script can only run with NetBeans against mysql.
-- If you don't have NetBeans installed, do the following:
-- 1. delete "delimiter;"
-- 2. add $$ at the end
-- 3. run the edited script in cmd mysql
drop database if exists NotesDB;
create database NotesDB;

use NotesDB;

create table NK_USER(
  USERNAME varchar(30) not null unique,
  PASSWORD varchar(255) not null,
  EMAIL varchar(50) not null,
  FIRSTNAME varchar(20),
  LASTNAME varchar(20),
  PHONENUMBER varchar(15),
  GENDER char(1),
  
  primary key (USERNAME)
)engine=InnoDB default charset=UTF8;

delimiter $$

create trigger CK_USER_GENDER_BIR
before insert
on NK_USER
for each row

begin
  if new.GENDER not in ('M', 'F', 'U', 'N') then
    signal sqlstate '45000'
    set message_text = 'gender is not M, F, U, or N';
  end if;
end;

$$ delimiter;

delimiter $$

create trigger CK_USER_GENDER_BUR
before update
on NK_USER
for each row

begin
  if new.GENDER not in ('M', 'F', 'U', 'N') then
    signal sqlstate '45000'
    set message_text = 'gender is not M, F, U, or N';
  end if;
end;

$$ delimiter;

create table NK_ROLE(
  ROLENAME varchar(30) not null unique,
  
  primary key (ROLENAME)
)engine=InnoDB default charset=UTF8;

create table NK_USER_ROLE(
  USERNAME varchar(30) not null,
  ROLENAME varchar(30) not null,
  
  primary key (USERNAME, ROLENAME),
  foreign key (USERNAME) references NK_USER(USERNAME),
  foreign key (ROLENAME) references NK_ROLE(ROLENAME)
)engine=InnoDB default charset=UTF8;

create table NK_NOTE(
  NOTEID binary(16) not null unique,
  CONTENT text not null,
  TRUSHED boolean not null,
  USERNAME varchar(30) not null,
  
  primary key (NOTEID),
  foreign key (USERNAME) references NK_USER(USERNAME)
)engine=InnoDB default charset=UTF8;

create table NK_USER_NOTE(
  USERNAME varchar(30) not null,
  NOTEID binary(16) not null,
  
  primary key (USERNAME, NOTEID),
  foreign key (USERNAME) references NK_USER(USERNAME),
  foreign key (NOTEID) references NK_NOTE(NOTEID)
)engine=InnoDB default charset=UTF8;

-- sha256 hex value of "password": 5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8
insert into NK_USER values ('admin','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');
insert into NK_USER values ('admin1','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');
insert into NK_USER values ('admin2','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');
insert into NK_USER values ('user','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');
insert into NK_USER values ('user1','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');
insert into NK_USER values ('user2','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','lijianzhao@lijianzhao.com','William','Li','5875816697','M');

insert into NK_ROLE values ('admin');
insert into NK_ROLE values ('user');

insert into NK_USER_ROLE values ('admin','admin');
insert into NK_USER_ROLE values ('admin1','admin');
insert into NK_USER_ROLE values ('admin2','admin');

insert into NK_USER_ROLE values ('admin','user');
insert into NK_USER_ROLE values ('admin1','user');
insert into NK_USER_ROLE values ('admin2','user');
insert into NK_USER_ROLE values ('user','user');
insert into NK_USER_ROLE values ('user1','user');
insert into NK_USER_ROLE values ('user2','user');