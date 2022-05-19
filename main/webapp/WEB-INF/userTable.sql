create table board(
	idx int primary key auto_increment,
	name varchar(30) not null,
	password varchar(50) not null,
	subject varchar(300) not null,
	content text not null,
	regDate timestamp default now(),
	clickCount int(4) default 0,
	ip varchar(20) not null
);

select password('1234'), md5('1234'), sha('1234');
select length(password('1'));
select length(password('1234'));
-- 길이가 41로 같게 나온다

INSERT INTO board (name, password, subject, content, ip)
VALUES 
	('한사람', password('1234'), '제목이다1', '내용이다1', '192.168.0.45'),
	('두사람', password('1234'), '제목이다2', '내용이다2', '192.168.0.45'),
	('세사람', password('1234'), '제목이다3', '내용이다3', '192.168.0.45'),
	('네사람', password('1234'), '제목이다4', '내용이다4', '192.168.0.45'),
	('오사람', password('1234'), '제목이다5', '내용이다5', '192.168.0.45');
	
delete from board where idx = 3;