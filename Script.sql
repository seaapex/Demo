drop database DEMO;
create database DEMO;

use DEMO;

-- table creation
create table DEMO_USER(
  ID binary(16) not null unique,
--  ID raw(16) not null unique, -- For Oracle
  NAME varchar(20) not null unique,
  PASSWORD char(44) not null,
--  PASSWORD char(64) NOT NULL, -- SHA256 HEX encoded

  CREATOR_ID binary(16) not null,
  CREAT_TIME date not null,
  EDITOR_ID binary(16) not null,
  EDIT_TIME date not null,
  
  primary key (ID),
  foreign key (CREATOR_ID) REFERENCES DEMO_USER(ID),
  foreign key (EDITOR_ID) REFERENCES DEMO_USER(ID)
);

create table DEMO_GROUP(
  ID binary(16) not null unique,
  NAME varchar(20) not null unique,
  
  CREATOR_ID binary(16) not null,
  CREAT_TIME date not null,
  EDITOR_ID binary(16) not null,
  EDIT_TIME date not null,
  
  primary key (ID),
  foreign key (CREATOR_ID) REFERENCES DEMO_USER(ID),
  foreign key (EDITOR_ID) REFERENCES DEMO_USER(ID)
);

create table DEMO_USER_GROUP(
  USER_ID binary(16) not null,
  GROUP_ID binary(16) not null,
  primary key (USER_ID, GROUP_ID),
  foreign key (USER_ID) REFERENCES DEMO_USER(ID),
  foreign key (GROUP_ID) REFERENCES DEMO_GROUP(ID)
);

-- sha256 base64 value of "demo": KpdRbDVLaISM29j1SiJqClWyHtE44getbFy7nACqWuo=
-- sha256 hex value of "demo": 2a97516c354b68848cdbd8f54a226a0a55b21ed138e207ad6c5cbb9c00aa5aea
insert into DEMO_USER values ('1234567890123456','demo','KpdRbDVLaISM29j1SiJqClWyHtE44getbFy7nACqWuo=','1234567890123456',NOW(),'1234567890123456',NOW());
insert into DEMO_USER values ('2234567890123456','user1','KpdRbDVLaISM29j1SiJqClWyHtE44getbFy7nACqWuo=','2234567890123456',NOW(),'2234567890123456',NOW());
insert into DEMO_USER values ('3234567890123456','user2','KpdRbDVLaISM29j1SiJqClWyHtE44getbFy7nACqWuo=','1234567890123456',NOW(),'1234567890123456',NOW());

insert into DEMO_GROUP values ('4234567890123456','admin','1234567890123456',NOW(),'1234567890123456',NOW());
insert into DEMO_GROUP values ('5234567890123456','user','1234567890123456',NOW(),'1234567890123456',NOW());

insert into DEMO_USER_GROUP (USER_ID, GROUP_ID) values ('1234567890123456','4234567890123456');
insert into DEMO_USER_GROUP (USER_ID, GROUP_ID) values ('1234567890123456','5234567890123456');
insert into DEMO_USER_GROUP (USER_ID, GROUP_ID) values ('2234567890123456','5234567890123456');
insert into DEMO_USER_GROUP (USER_ID, GROUP_ID) values ('3234567890123456','5234567890123456');

-- check data
select * from DEMO_USER;
select * from DEMO_GROUP;
select * from DEMO_USER_GROUP;

-- principalsQuery test
select password from demo_user where name='demo';
-- rolesQuery test
select g.name, 'Roles' from demo_user u, demo_group g, demo_user_group ug where u.name='demo' and u.id=ug.user_id and g.id=ug.group_id;