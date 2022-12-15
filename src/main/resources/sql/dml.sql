-- 테스트 : 필요시 더미데이터 생성
SET REFERENTIAL_INTEGRITY FALSE;
truncate table customer;
truncate table image_file;
truncate table menu;
truncate table reservation;
truncate table review;
truncate table shop;
truncate table shop_table;
truncate table subscribe;
truncate table users;
truncate table commercial;
truncate table option;
truncate table option_shop;
SET REFERENTIAL_INTEGRITY TRUE;

insert into users(username, password, role, is_deleted, created_at) values ('ssar', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'USER', false,  now());
insert into customer(address, name, phone_number, user_id, is_deleted, created_at)
values ('주소', '커스터머', '01099966462', 1, false, now());

-- 가게 정렬을 위한 더미데이터
insert into users(username, password, role, is_deleted, created_at) values ('cos', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP', false, now());
insert into users(username, password, role, is_deleted, created_at) values ('cos2', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP',false, now());
insert into users(username, password, role, is_deleted, created_at) values ('cos3', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP', false,now());
insert into users(username, password, role, is_deleted, created_at) values ('cos4', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP', false,now());
insert into users(username, password, role, is_deleted, created_at) values ('cos5', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP',false, now());

insert into shop(category, address, close_time, information, open_time, per_hour, per_price, phone_number, shop_name, user_id, created_at)
values ('한식', '부산 부산진구 범천동', '22', '소개', '10', '1', '10000', '01011113333', '가게', 2, now());
insert into shop(category, address, close_time, information, open_time, per_hour, per_price, phone_number, shop_name, user_id, created_at)
values ('한식', '부산 부산진구 범일동', '22', '소개2', '12', '1', '15000', '01011112222', '가게2', 3,  now());
insert into shop(category, address, close_time, information, open_time, per_hour, per_price, phone_number, shop_name, user_id, created_at)
values ('일식', '부산 해운대구', '22', '소개3', '12', '1', '10000', '01011114444', '가게3',4, now());
insert into shop(category, address, close_time, information, open_time, per_hour, per_price, phone_number, shop_name, user_id,  created_at)
values ('증식', '서울 서대문구', '22', '소개4', '10', '1', '20000', '01011115555', '가게4',  5, now());
insert into shop(category, address, close_time, information, open_time, per_hour, per_price, phone_number, shop_name, user_id,  created_at)
values ('양식', '서울 동대문구', '22', '소개5', '10', '2', '20000', '01011116666', '가게5',  6, now());
-- 가게정렬을 위한 더미데이터

insert into users(username, password, role, is_deleted, created_at) values ('ssar2', '$2a$10$sPyqasyfpVYeHDVrtRcXKelXutSQobLuWzf32BXmQvnLTMbddkIwy', 'SHOP',false, now());
insert into customer(address, name, phone_number, user_id, is_deleted, created_at)
values ('주소', '커스터머2', '01099966462', 7, false, now());

insert into image_file(store_filename, shop_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 1, now());
insert into image_file(store_filename, shop_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 2, now());
insert into image_file(store_filename, shop_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 3, now());
insert into image_file(store_filename, shop_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 4, now());
insert into image_file(store_filename, shop_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 5, now());

insert into review(score, content, shop_id, customer_id, created_at) values (5, 'test',1, 1,now());
insert into review(score, content, shop_id, customer_id, created_at) values (4, 'test',1, 1,now());

insert into image_file(store_filename, review_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 1, now());
insert into image_file(store_filename, review_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 1, now());

insert into shop_table(max_people, shop_id, created_at)
values (4, 1,  now());
insert into shop_table(max_people, shop_id,  created_at)
values (4, 1,  now());
insert into shop_table(max_people, shop_id,  created_at)
values (2, 1, now());

insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221126', '16', 1, 1, false, now());
insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221126', '18', 1, 1, false, now());
insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221127', '12', 1, 2, false, now());
insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221126', '16', 1, 2, false, now());
insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221127', '12', 1, 1, false, now());
insert into reservation(reservation_date, reservation_time, customer_id, shop_table_id, is_deleted, created_at)
values ('20221127', '16', 1, 2, false, now());

insert into subscribe(customer_id, shop_id, created_at)
values (1, 1, now());

insert into option(name, image_file_id, created_at) values ('옵션', 1, now());

insert into commercial (created_at, specification, is_deleted, shop_id)
values (now(), '광고입니다', false, 1);
insert into image_file(store_filename, commercial_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 1, now());



-- 가게 정렬을 위한 더미데이터 추가
insert into subscribe (created_at, customer_id, shop_id)
values (now(), 1, 1);
insert into subscribe (created_at, customer_id, shop_id)
values (now(), 2, 1);
insert into subscribe (created_at, customer_id, shop_id)
values (now(), 1, 2);


--가격정렬을 위한 메뉴 더미
insert into menu(created_at, name, price, recommanded, shop_id, is_deleted)
values (now(), '1-1메뉴', 10000, 1, 1, false);
insert into menu(created_at, name, price, recommanded, shop_id, is_deleted)
values (now(), '1-2메뉴', 10000, 2, 1, false);
insert into menu(created_at, name, price, recommanded, shop_id, is_deleted)
values (now(), '2-1메뉴', 20000, 2, 2, false);
insert into menu(created_at, name, price, recommanded, shop_id, is_deleted)
values (now(), '3-1메뉴', 10000, 2, 3, false);
insert into menu(created_at, name, price, recommanded, shop_id, is_deleted)
values (now(), '3-2메뉴', 20000, 2, 3, false);

insert into image_file(store_filename, menu_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 1, now());
insert into image_file(store_filename, menu_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 2, now());
insert into image_file(store_filename, menu_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 3, now());
insert into image_file(store_filename, menu_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 4, now());
insert into image_file(store_filename, menu_id, created_at) values('498e8a11-1048-429c-ad13-b1b51fd714b7.png', 5, now());

--옵션 더미데이터
insert into option(name, image_file_id, created_at) values ('옵션', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션2', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션3', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션4', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션5', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션6', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션7', 1, now());
insert into option(name, image_file_id, created_at) values ('옵션8', 1, now());

insert into option_shop(created_at, option_id, shop_id) values (now(), 1, 1);
insert into option_shop(created_at, option_id, shop_id) values (now(), 2, 1);
insert into option_shop(created_at, option_id, shop_id) values (now(), 1, 2);