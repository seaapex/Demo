-- This script can only run with NetBeans against mysql.
-- If you don't have NetBeans installed, do the following:
-- 1. delete "delimiter;"
-- 2. add $$ at the end
-- 3. run the edited script in cmd mysql
drop database if exists NotesDB;
create database NotesDB;

use NotesDB;

create table NK_USER(
  ID binary(16) not null unique,
  NAME varchar(30) not null unique,
  PASSWORD char(44) not null,
  EMAIL varchar(50) not null,
  FIRSTNAME varchar(20),
  LASTNAME varchar(20),
  PHONENUMBER varchar(15),
  GENDER char(1),

  CREATOR_ID binary(16) not null,
  CREAT_TIME date not null,
  EDITOR_ID binary(16) not null,
  EDIT_TIME date not null,
  
  primary key (ID),
  foreign key (CREATOR_ID) references NK_USER(ID),
  foreign key (EDITOR_ID) references NK_USER(ID)
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
  ID binary(16) not null unique,
  NAME varchar(30) not null unique,
  
  CREATOR_ID binary(16) not null,
  CREAT_TIME date not null,
  EDITOR_ID binary(16) not null,
  EDIT_TIME date not null,
  
  primary key (ID),
  foreign key (CREATOR_ID) references NK_USER(ID),
  foreign key (EDITOR_ID) references NK_USER(ID)
)engine=InnoDB default charset=UTF8;

create table NK_USER_ROLE(
  USER_ID binary(16) not null,
  ROLE_ID binary(16) not null,
  
  primary key (USER_ID, ROLE_ID),
  foreign key (USER_ID) references NK_USER(ID),
  foreign key (ROLE_ID) references NK_ROLE(ID)
)engine=InnoDB default charset=UTF8;

create table NK_NOTE(
  ID binary(16) not null unique,
  CONTENT text not null,
  TRUSHED boolean not null,
  OWNER_ID binary(16) not null,
  
  CREATOR_ID binary(16) not null,
  CREAT_TIME date not null,
  EDITOR_ID binary(16) not null,
  EDIT_TIME date not null,
  
  primary key (ID),
  foreign key (CREATOR_ID) references NK_USER(ID),
  foreign key (EDITOR_ID) references NK_USER(ID),
  foreign key (OWNER_ID) references NK_USER(ID)
)engine=InnoDB default charset=UTF8;

create table NK_USER_NOTE(
  USER_ID binary(16) not null,
  NOTE_ID binary(16) not null,
  
  primary key (USER_ID, NOTE_ID),
  foreign key (USER_ID) references NK_USER(ID),
  foreign key (NOTE_ID) references NK_NOTE(ID)
)engine=InnoDB default charset=UTF8;

-- sha256 base64 value of "password": XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=
insert into NK_USER values ('0000000000000000','admin','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());
insert into NK_USER values ('0000000000000001','admin1','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());
insert into NK_USER values ('0000000000000002','admin2','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());
insert into NK_USER values ('0000000000000003','user','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());
insert into NK_USER values ('0000000000000004','user1','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());
insert into NK_USER values ('0000000000000005','user2','XohImNooBHFR0OVvjcYpJ3NgPQ1qq73WKhHvch0VQtg=','lijianzhao@lijianzhao.com','William','Li','5875816697','M','0000000000000000',now(),'0000000000000000',now());

insert into NK_ROLE values ('1000000000000000','admin','0000000000000000',now(),'0000000000000000',now());
insert into NK_ROLE values ('2000000000000000','user','0000000000000000',now(),'0000000000000000',now());

insert into NK_USER_ROLE values ('0000000000000000','1000000000000000');
insert into NK_USER_ROLE values ('0000000000000001','1000000000000000');
insert into NK_USER_ROLE values ('0000000000000002','1000000000000000');

insert into NK_USER_ROLE values ('0000000000000000','2000000000000000');
insert into NK_USER_ROLE values ('0000000000000001','2000000000000000');
insert into NK_USER_ROLE values ('0000000000000002','2000000000000000');
insert into NK_USER_ROLE values ('0000000000000003','2000000000000000');
insert into NK_USER_ROLE values ('0000000000000004','2000000000000000');
insert into NK_USER_ROLE values ('0000000000000005','2000000000000000');