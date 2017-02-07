--
--    Copyright 2015-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

CREATE TABLE t_test_user (
  id bigint NOT NULL primary key AUTO_INCREMENT,
  user_name varchar(50) NOT NULL,
  password varchar(64) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  gender tinyint DEFAULT NULL,
  birthday datetime DEFAULT NULL,
  create_time datetime DEFAULT NULL
);

INSERT INTO t_test_user (id, user_name, password, age, gender, birthday, create_time)
VALUES
	(1,'user_mapperUpdate','password1',3,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(2,'user2_modifyList',NULL,22,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(3,'user3_modifyList',NULL,23,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(4,'user4_modifyList',NULL,24,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(5,'user_modifyByChain','password5',25,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(6,'user_modifyByChain','password6',26,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(7,'user_modifyByChain','password7',27,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(8,'user8','password8',28,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(9,'user9','password9',29,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(10,'user10','password10',30,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(11,'user11','password11',31,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(12,'user12','password12',32,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(13,'user13','password13',33,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(14,'user14','password14',34,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(15,'user15','password15',35,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(16,'user16','password16',36,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(17,'user17','password17',37,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(18,'user18','password18',38,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(19,'user19','password19',39,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(20,'user20','password20',40,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(21,'user21','password21',41,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(22,'user22','password22',42,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(23,'user23','password23',43,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(24,'user24','password24',44,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(25,'user25','password25',45,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(26,'user26','password26',46,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(27,'user27','password27',47,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(28,'user28','password28',48,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(29,'user29','password29',49,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(30,'user30','password30',50,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(31,'user31','password31',51,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(32,'user32','password32',52,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(33,'user33','password33',53,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(34,'user34','password34',54,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(35,'user35','password35',55,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(36,'user36','password36',56,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(37,'user37','password37',57,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(38,'user38','password38',58,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(39,'user39','password39',59,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(40,'user40','password40',60,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(41,'user41','password41',61,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(42,'user42','password42',62,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(43,'user43','password43',63,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(44,'user44','password44',64,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(45,'user45','password45',65,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(46,'user46','password46',66,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(47,'user47','password47',67,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(48,'user48','password48',68,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(49,'user49','password49',69,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(50,'user50','password50',70,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(51,'user51','password51',71,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(52,'user52','password52',72,0,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(58,'testNameSave1','testPassword1',23,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(59,'testNameSaveList0','testPasswordList0',23,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(60,'testNameSaveList1','testPasswordList1',24,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(61,'testNameSaveList2','testPasswordList2',25,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(62,'testNameSaveList3','testPasswordList3',26,1,'2017-01-19 11:07:50','2017-01-19 11:07:50'),
	(63,'testNameSaveList4','testPasswordList4',27,1,'2017-01-19 11:07:50','2017-01-19 11:07:50');

CREATE TABLE t_test_price (
	id bigint NOT NULL primary key AUTO_INCREMENT,
	price_date int(11) NOT NULL,
	price int(11) DEFAULT 0,
	create_time datetime DEFAULT NULL
);

CREATE TABLE t_test_price_20170207 (
	id bigint NOT NULL primary key AUTO_INCREMENT,
	price_date int(11) NOT NULL,
	price int(11) DEFAULT 0,
	create_time datetime DEFAULT NULL
);

insert into t_test_price_20170207(id, price_date, price, create_time)
values
	(1, 20170207, 301, '2017-02-07 11:56:00'),
	(2, 20170207, 302, '2017-02-07 11:56:00'),
	(3, 20170207, 303, '2017-02-07 11:56:00'),
	(4, 20170207, 304, '2017-02-07 11:56:00'),
	(5, 20170207, 305, '2017-02-07 11:56:00'),
	(6, 20170207, 306, '2017-02-07 11:56:00'),
	(7, 20170207, 307, '2017-02-07 11:56:00'),
	(8, 20170207, 308, '2017-02-07 11:56:00'),
	(9, 20170207, 309, '2017-02-07 11:56:00'),
	(10, 20170207, 310, '2017-02-07 11:56:00'),
	(11, 20170207, 311, '2017-02-07 11:56:00'),
	(12, 20170207, 312, '2017-02-07 11:56:00');