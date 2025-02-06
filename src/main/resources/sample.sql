
use connectu;
show tables;

drop table reservation;
drop table schedule;
drop table order_product;
drop table order_;
drop table paymemt_history;
drop table payment;
drop table email;
drop table course;
drop table product_file;
drop table employee_file;
drop table product;
drop table employee;
drop table user_login;
drop table user_;

select *from order_product;
select*from order_;

-- 관리자
INSERT INTO user_ (
	id,
    login_id,
    name,
    password,
    type,
    gender,
    cell_phone,
    is_receive_sms,
    email,
    is_receive_email ,
    address,
    detailed_address ,
    is_office_worker,
    is_active,
    note,
    created_on ,
    modified_on
) VALUES (
	'U13708399715165223861',
    'jenchae@naver.com', -- loginId
    '관리자',
    '1111', -- password
    'A',
    'F',
    '010-2312-5678',
    'Y',
    'jenchae@naver.com',
    'Y',
    'Sample Address',
    'Sample Detailed Address',
    'Y',
    true, -- is_active
    'This is a sample note',
    '2013-06-10 13:52:51',
    '2013-06-10 13:52:51'
);

-- 회원1
INSERT INTO user_ (
	id,
    login_id,
    name,
    password,
    type,
    gender,
    cell_phone,
    is_receive_sms,
    email,
    is_receive_email ,
    address,
    detailed_address ,
    is_office_worker,
    is_active,
    note,
    created_on ,
    modified_on
) VALUES (
	'회원1',
    'duswls3000@naver.com', -- loginId
    '고연진',
    '1111', -- password
    'S',
    'F',
    '010-9190-1376',
    'Y',
    'duswls3000@naver.com',
    'Y',
    'Sample Address',
    'Sample Detailed Address',
    'Y',
    true, -- is_active
    'This is a sample note',
    '2013-06-10 13:52:51',
    '2013-06-10 13:52:51'
);

-- 직원1
insert into employee (
	id,
	type,
	created_on,
    modified_on
)values (
	'직원1',
	'T',
	'2013-06-10 13:52:51',  -- created_on (생성 날짜)
    '2013-06-10 13:52:51' 
);

-- 상품1
insert into product (
	id,
	type,
	name,
	price,
	description,
	created_on,
    modified_on
) values (
	'상품1',
	'C',
	'10회강사수강권',
	10000,
	'영어회화가 처음이신 초보분들께 추천합니다',
	'2013-06-10 13:52:51',  -- created_on (생성 날짜)
    '2013-06-10 13:52:51' 	
)

-- 주문
insert into order_ (
	id,
	total_price,
	discount,
	user_id,
	created_on,
    modified_on
) values (
	'주문1',
	10000,
	1000,
	'회원1',
	'2013-06-10 13:52:51',  -- created_on (생성 날짜)
    '2013-06-10 13:52:51'	
)

-- 주문상품
select *from order_product;
insert into order_product (
  id,
  order_id,
  product_id,
  created_on,
  modified_on
) values (
  '주문상품1',    -- id
  '주문1',       -- order_id
  '상품1',       -- product_id
  '2025-02-08 10:00:00',  -- created_on
  '2025-02-08 10:00:00'   -- modified_on
);

-- 결제
insert into payment (
	id,
	type,
	user_id,
	order_id,
  created_on,
  modified_on	
) values (
	'결제1',
	'C',
	'회원1',
	'주문1',
  '2025-02-08 10:00:00',  -- created_on
  '2025-02-08 10:00:00'   	
)

-- 이용 중인 수강권 - course_id =1
INSERT INTO course (  -- 테이블명 추가
    user_id,
    employee_id,
    assignment_count,
    attendance_count,
    end_date,
    lesson_count,
    start_date,
    created_on,
    modified_on
) VALUES (
    '회원1', -- user_id
    '직원1',
    10,  -- assignment_count (예약 횟수)
    1,   -- attendance_count (출석 횟수)
    '2025-07-22',  -- end_date (수강 종료일)
    100,  -- lesson_count (총 수업 회차)
    '2025-01-01',  -- start_date (수강 시작일)
    '2013-06-10 13:52:51',  -- created_on (생성 날짜)
    '2013-06-10 13:52:51'   -- modified_on (수정 날짜)
);

-- 예약
insert into reservation (
  date,
  start_time,
  end_time,
  course_id,
  created_on,
  modified_on
) values (
  '2025-02-07',                  -- date (LocalDate)
  '12:12:12',         -- startTime (LocalDateTime)
  '12:12:12',         -- endTime (LocalDateTime)
  1,                              -- course_id (Foreign Key to CourseEntity)
  '2013-06-10 13:52:51',         -- created_on (Timestamp)
  '2013-06-10 13:52:51'          -- modified_on (Timestamp)
);


