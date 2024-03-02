CREATE DATABASE CybersoftProject;

USE CybersoftProject;

CREATE TABLE merchant(
	id bigint auto_increment,
	merchant_code varchar(20) unique,
	name varchar(100),
	legal_name varchar(100),
	logo_url varchar(100),
	address varchar(100),	
	phone varchar(20),
	email varchar(50),
	description text,
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	PRIMARY KEY(id)
);

CREATE TABLE chains(
	id bigint auto_increment,
	chain_code varchar(20) unique, 
	name varchar(100),
	legal_name varchar(100),
	logo_url varchar(100),
	address varchar(100),
	phone varchar(20),
	email varchar(50),
	description text,
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	id_merchant bigint NOT null,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE store(
	id bigint auto_increment,
	store_code varchar(20) unique, 
	name varchar(100),
	address varchar(100),
	phone varchar(20),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	description text,
	id_chain bigint NOT null,
	id_merchant bigint NOT null,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE warehouse(
	id bigint auto_increment,
	warehouse_code varchar(20) unique,
	id_label int not null,
	name varchar(100),
	description text,
	term_of_use varchar(255),
	banner_url varchar(255),
	thumbnail_url varchar(255),
	id_discount_type bigint not null,
	discount_amount decimal(8,3),
	price decimal(12,2),
    original_price decimal(12,2),
	max_discount_amount decimal(8,3),
	available_from timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	available_to timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	show_on_web int NOT null, -- giá trị 0:không hiển thị cho end user và giá trị 1:được hiển thị cho end user
	capacity int,
	voucher_channel int NOT null, -- giá trị 0:áp dụng hình thức offline và giá trị 1:áp dụng hình thức online
	id_category bigint NOT null,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE token(
id bigint auto_increment,
token text,
token_type varchar(100),
expired tinyint,
revokes tinyint,
id_user bigint not null,

primary key(id)
);

CREATE TABLE verification_token(
	id bigint  auto_increment,
        users_id bigint not null,
        token varchar(255),
        expired_time timestamp,
    primary key (id)
);


CREATE TABLE label(
id int auto_increment,
name varchar(50),
slug varchar(255),
primary key(id)
);

CREATE TABLE discount_type(
	id bigint auto_increment,
	code varchar(20) unique,
	name varchar(100),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	primary key(id)
);

CREATE TABLE warehouse_merchant(
	id_warehouse bigint,
	id_merchant bigint,
	id_role bigint not null, -- acquirer/issuer
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	PRIMARY key(id_warehouse, id_merchant)
);

CREATE TABLE category(
	id bigint auto_increment,
	category_code varchar(20) unique,
	name varchar(100),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	banner_url varchar(255),

	primary key(id)
);
CREATE TABLE warehouse_stores(
	id_warehouse bigint,
	id_store bigint,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
		
	primary key(id_warehouse, id_store)
);

CREATE TABLE warehouse_serial(
	id_warehouse bigint,
	id_serial bigint unique,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
		
	-- UNIQUE(id_serial),
	primary key(id_warehouse, id_serial)
);

CREATE TABLE serial(
	id bigint auto_increment,
	batch_code varchar(20),
	number_of_serial int,
	serial_code varchar(20) unique,
	status int NOT null, -- giá trị 0:ngừng áp dụng và giá trị 1:còn áp dụng
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE ticket(
	id bigint auto_increment,
	id_serial bigint unique NOT null,
	id_warehouse bigint NOT null,
	id_category bigint NOT null,
	status int NOT null, -- giá trị 0:chưa phát hành, giá trị 1:đã phát hành, giá trị 2: đã sử dụng, giá trị 3: đã hết hạn
	id_order bigint NOT null, 
	claimed_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	redeemedtime_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	expired_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	discount_type varchar(100),
	discount_amount decimal(8,3),
	banner_url varchar(150),
	thumbnail_url varchar(150),
	acquirer_logo_url varchar(150),
	term_of_use varchar(512),
	description text,
	vouncher_channel varchar(100),
	available_from timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	available_to timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	applied_store bigint NOT null,
	id_user bigint NOT null,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE ticket_history(
	id bigint auto_increment,
	id_ticket bigint not null,
	serial_code varchar(20) unique, 
	prev_status int not null, -- giá trị 0,1
	is_latest int not null, -- giá trị 0,1
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key(id)
);

CREATE TABLE orders(
	id bigint auto_increment,
	order_no varchar(20) unique, 
	status int NOT null, -- giá trị 0:ngừng áp dụng và giá trị 1:còn áp dụng
	id_user bigint NOT null,
	quantity int,
	id_warehouse bigint NOT null,
	updated_by varchar(50),
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key(id)
);

CREATE TABLE users(
	id bigint auto_increment,
	member_code varchar(20) unique,
	first_name varchar(20),
	last_name varchar(20),
	full_name varchar(20),
	username varchar(50),
	password varchar(100),
	phone varchar(20),
	email varchar(50),
	status int NOT null,
	ava_url varchar(100),
	address varchar(100),
    balance decimal(12, 2),
	enable tinyint,
	provider varchar(50),
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss		
	
	primary key (id)
);

CREATE TABLE roles(
	id bigint auto_increment,
	name varchar(100),
	role_code varchar(100) UNIQUE,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key (id)
);

CREATE TABLE roles_users(
	id_role bigint not null ,
	id_user bigint not null ,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	PRIMARY KEY(id_role, id_user)
);

CREATE TABLE comment(
    id bigint auto_increment,
    id_warehouse bigint not null ,
    id_user bigint not null ,
    comment_user varchar(512),
    created_by varchar(50),
    updated_by varchar(50),
    created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
    updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss

    PRIMARY KEY(id)
);

CREATE TABLE reply_comment(
                        id bigint auto_increment,
                        id_comment bigint not null ,
                        id_user bigint not null ,
                        reply_comment varchar(512),
                        created_by varchar(50),
                        updated_by varchar(50),
                        created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
                        updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss

                        PRIMARY KEY(id)
);

CREATE TABLE wishlist(
    id bigint auto_increment,
    name varchar(155),
    banner_url varchar(255),
    price decimal(12,2),
    original_price decimal(12,2),
    max_discount_amount decimal(8,3),
    category_name varchar(155),
    id_user bigint not null,
    id_warehouse bigint not null,
    status int not null,
    created_by varchar(50),
    updated_by varchar(50),
    created_at timestamp,
    updated_at timestamp,

    PRIMARY KEY (id)
);


ALTER TABLE chains ADD CONSTRAINT FK_id_merchant_chains FOREIGN KEY (id_merchant) REFERENCES merchant(id);

ALTER TABLE store ADD CONSTRAINT FK_id_chain_store FOREIGN KEY (id_chain) REFERENCES chains(id); 
ALTER TABLE store ADD CONSTRAINT FK_id_merchant_store FOREIGN KEY (id_merchant) REFERENCES merchant(id);

ALTER TABLE warehouse ADD CONSTRAINT FK_discount_type_warehouse FOREIGN KEY (id_discount_type) REFERENCES discount_type(id);
ALTER TABLE warehouse ADD CONSTRAINT FK_id_category_warehouse FOREIGN KEY (id_category) REFERENCES category(id);
ALTER TABLE warehouse ADD CONSTRAINT FK_id_label_warehouse FOREIGN KEY(id_label) REFERENCES label(id);

ALTER TABLE warehouse_merchant ADD CONSTRAINT FK_id_warehouse_warehouse_merchant FOREIGN KEY (id_warehouse) REFERENCES warehouse (id);
ALTER TABLE warehouse_merchant ADD CONSTRAINT FK_id_merchant_warehouse_merchant FOREIGN KEY (id_merchant) REFERENCES merchant (id);
ALTER TABLE warehouse_merchant ADD CONSTRAINT FK_id_role_warehouse_merchant FOREIGN KEY (id_role) REFERENCES roles(id);

ALTER TABLE warehouse_stores ADD CONSTRAINT FK_id_warehouse_warehouse_stores FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_stores ADD CONSTRAINT FK_id_store_warehouse_stores FOREIGN KEY (id_store) REFERENCES store(id);

ALTER TABLE warehouse_serial ADD CONSTRAINT FK_id_warehouse_warehouse_serial FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_serial ADD CONSTRAINT FK_id_serial_warehouse_serial FOREIGN KEY(id_serial) REFERENCES serial(id);

ALTER TABLE ticket ADD CONSTRAINT FK_id_warehouse_ticket FOREIGN KEY (id_warehouse) REFERENCES warehouse(id); 
ALTER TABLE ticket ADD CONSTRAINT FK_id_category_ticket FOREIGN KEY (id_category) REFERENCES category(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_serial_ticket FOREIGN KEY (id_serial) REFERENCES serial(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_order_ticket FOREIGN KEY (id_order) REFERENCES orders(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_user_ticket FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE ticket ADD CONSTRAINT FK_applied_store_ticket FOREIGN KEY (applied_store) REFERENCES store(id);

ALTER TABLE ticket_history ADD CONSTRAINT FK_id_ticket_ticket_history FOREIGN KEY (id_ticket) REFERENCES ticket(id);

ALTER TABLE orders ADD CONSTRAINT FK_id_user_orders FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE orders ADD CONSTRAINT FK_id_warehouse_orders FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);

ALTER TABLE roles_users ADD CONSTRAINT FK_id_role_roles_users FOREIGN KEY (id_role) REFERENCES roles (id);
ALTER TABLE roles_users ADD CONSTRAINT FK_id_user_roles_users FOREIGN KEY (id_user) REFERENCES users (id);

ALTER TABLE token ADD CONSTRAINT FK_id_user_token FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE verification_token ADD CONSTRAINT FK_user_verification_token FOREIGN KEY (users_id) REFERENCES users(id);

ALTER TABLE comment ADD CONSTRAINT FK_comment_warehouse FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE comment ADD CONSTRAINT FK_comment_user FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE reply_comment ADD CONSTRAINT  FK_reply_comment FOREIGN KEY (id_comment) REFERENCES comment(id);
ALTER TABLE reply_comment ADD CONSTRAINT  FK_reply_user FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE wishlist ADD CONSTRAINT FK_wishlist_user FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE wishlist ADD CONSTRAINT FK_wishlist_warehouse FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);

-- INSERT

INSERT INTO label (name, slug) VALUES
                             ('Ưu đãi đặc biệt', 'special'),
                             ('Cuối tuần', 'weekend'),
                             ('Chỉ có tại SmartVoucher', 'onlySmartvoucher'),
                             ('Khỏe đẹp', 'healthy'),
                             ('Công nghệ và Đời sống', 'technologiesLife');

INSERT INTO roles(name, role_code)VALUES ('ROLE_ADMIN', 'ADMIN-1');
INSERT INTO roles(name, role_code)VALUES ('ROLE_USER', 'USER-2');
INSERT INTO roles (name, role_code) VALUES ('ROLE_MERCHANT','MERCHANT');


INSERT INTO category (category_code, name, status, created_by, created_at, banner_url)
VALUES
    ('cat_travel', 'Du lịch', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/524f0888fcc54b28b2fac26cb3f53d3f.png'),
    ('cat_electronics', 'Điện tử', 1, 'ADMIN', NOW(), 'http://localhost:8082https://lh3.google.com/u/0/d/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB
/category/417db0c36f6e4ce28e9b13a0925c1a31.png'),
    ('cat_fashion', 'Thời trang', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/030a1455209a470595a8149de663566f.png'),
    ('cat_food', 'Ẩm thực', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/171be693f8d248658e7c5738517d9df0.png'),
    ('cat_books', 'Sách và Học nghệ thuật', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/351501b85bb54b3eb2ee4b17b0252da8.png'),
    ('cat_health', 'Sức khỏe và Làm đẹp', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/e6ea5c61fa634639994ab34062c366c6.png'),
    ('cat_home', 'Nội thất và Gia đình', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/d75b20e4cebf4cdea4bd3c3369cd31f3.png'),
    ('cat_technology', 'Công nghệ', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/c1ab9c054f69488f9c8abf375dd9a649.png'),
    ('cat_sports', 'Thể thao và Hoạt động ngoại ô', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/2d14823947854ec4bd4e92d0880545dc.png'),
    ('cat_game', 'Trò chơi', 1, 'ADMIN', NOW(), 'http://localhost:8082/category/a9d19582c7784906a66cefa40c67bf9f.png');

INSERT INTO discount_type (code, name, status, created_by, created_at)
VALUES
    ('DT001', 'Percentage Off', 1, 'admin', '2023-11-11 00:00:00'),
    ('DT002', 'Fixed Amount Off', 1, 'admin', '2023-11-11 00:00:00'),
    ('DT003', 'Buy One Get One Free', 1, 'admin', '2023-11-11 00:00:00'),
    ('DT004', 'Free Shipping', 1, 'admin', '2023-11-11 00:00:00');

INSERT INTO merchant
(merchant_code, name, legal_name, logo_url, address, phone, email, status, created_by, created_at)
VALUES
    ('M01', 'Organic Foods', 'Công ty TNHH Organic Foods', 'http://localhost:8082/merchant/1c69ad9aff6c48e6acad7aad275bc807.png', '123 Đường Lê Lai, Quận 1, TP.HCM', '0123456789', 'merchant1@example.com', 1, 'Admin', '2023-01-01 10:00:00'),
    ('M02', 'Thực phẩm Annamese GoGo', 'Công ty TNHH Thực phẩm Annamese GoGo', 'http://localhost:8082/merchant/2b95d2c02e8f4b6c8fa77392bd30db73.png', '456 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', '0987654321', 'merchant2@example.com', 1, 'Admin', '2023-01-02 11:00:00'),
    ('M03', 'Cửa hàng Ramenly', 'Công ty TNHH Ramenly', 'http://localhost:8082/merchant/914475034c2a413aaf8b267c9a1f4e2a.png', '789 Đường Lê Thánh Tôn, Quận 3, TP.HCM', '0111222333', 'merchant3@example.com', 1, 'Admin', '2023-01-03 12:00:00'),
    ('M04', 'Cửa hàng Thai Cuisine', 'Công ty TNHH Thai Cuisine', 'http://localhost:8082/merchant/de9b19ad92944b6db4144dd2db8e46d0.png','86D Song Hành, Quận 2,TP.HCM', '0444556666', 'merchant4@example.com', 1, 'Admin', '2023-01-04 13:00:00'),
    ('M05', 'Everest Beauty', 'Công ty TNHH Everest Beauty', 'http://localhost:8082/merchant/ae9e6da311a44d6fb1c0e75c2595e8ce.png', '234 Đường Hoàng Sa, Quận 1, TP.HCM', '0777888999', 'merchant5@example.com', 1, 'Admin', '2023-01-05 14:00:00'),
    ('M06', 'Cửa hàng Future Tech', 'Công ty TNHH Future Tech', 'http://localhost:8082/merchant/dba3c56a6c3f47b4b85009e073a898d3.png', '567 Đường Cách Mạng Tháng Tám, Quận 3, TP.HCM', '0222334444', 'merchant6@example.com', 1, 'Admin', '2023-01-06 15:00:00'),
    ('M07', 'Sport Light', 'Công ty TNHH Sport Light', 'http://localhost:8082/merchant/bfe807c573bc4b06b85a1b97e0a6c48b.png', '890 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', '0556667777', 'merchant7@example.com', 1, 'Admin', '2023-01-07 16:00:00'),
    ('M08', 'Travel Buddy', 'Công ty TNHH Travel Buddy', 'http://localhost:8082/merchant/1d2d19da55734481bb7b6384744c5d4d.png', '345 Đường Phan Xích Long, Quận Phú Nhuận, TP.HCM', '0884349050', 'merchant8@example.com', 1, 'Admin', '2023-01-08 17:00:00'),
    ('M09', 'Quần áo thể thao Soccer', 'Công ty TNHH Soccer Cloth', 'http://localhost:8082/merchant/15af7d5cf2ab496786f6f5dce7ce8cfc.png', '678 Đường Trần Hưng Đạo, Quận 5, TP.HCM', '0965227633', 'merchant9@example.com', 1, 'Admin', '2023-01-09 18:00:00'),
    ('M10', 'Gradient Gaming', 'Công ty TNHH Gradient Gaming', 'http://localhost:8082/merchant/95d986cf7d0f4618b5b44d59dc348554.png', '901 Đường Lê Văn Sỹ, Quận 3, TP.HCM', '0945556666', 'merchant10@example.com', 1, 'Admin', '2023-01-10 19:00:00'),
    ('M11', 'My Cosmetic and Spa', 'Công ty TNHH My Cosmetic and Spa', 'http://localhost:8082/merchant/b9775671710d43debb4b8b48101979a0.png', '234 Đường Lê Đại Hành, Quận 10, TP.HCM', '0772485919', 'merchant11@example.com', 1, 'Admin', '2023-01-11 20:00:00'),
    ('M12', 'Monster Coffee', 'Công ty TNHH Monster Coffee', 'http://localhost:8082/merchant/f88fd5d2e2ca4d2ab3e0363125e51eb8.png', '567 Đường Nguyễn Trãi, Quận 1, TP.HCM', '0323133644', 'merchant12@example.com', 1, 'Admin', '2023-01-12 21:00:00'),
    ('M13', 'Sushi Japan', 'Công ty TNHH Sushi Japan', 'http://localhost:8082/merchant/9b4cdb3f68094ea3bddb7da03c23567d.png', '890 Đường Bùi Viện, Quận 1, TP.HCM', '0556617287', 'merchant13@example.com', 1, 'Admin', '2023-01-13 22:00:00'),
    ('M14', 'Cửa hàng Nowi', 'Công ty TNHH Nowi', 'http://localhost:8082/merchant/2585b37a8e6f4feba3f45f73e276110a.png', '345 Đường Cao Thắng, Quận 3, TP.HCM', '0889991010', 'merchant14@example.com', 1, 'Admin', '2023-01-14 23:00:00'),
    ('M15', 'Mighty Lion', 'Công ty TNHH Mighty Lion', 'http://localhost:8082/merchant/ab0ad06204d44025ba4558cf3ed00446.png', '678 Đường Huỳnh Văn Bánh, Quận Phú Nhuận, TP.HCM', '0122213453', 'merchant15@example.com', 1, 'Admin', '2023-01-15 00:00:00'),
    ('M16', 'Art Academy', 'Art Academy', 'http://localhost:8082/merchant/7da07224a97f43e2bbeab3d29815af0b.png', '901 Đường Lý Tự Trọng, Quận 1, TP.HCM', '0455566266', 'merchant16@example.com', 1, 'Admin', '2023-01-16 01:00:00');



INSERT INTO chains
(chain_code, name, legal_name, logo_url, address, phone, email, status, id_merchant, created_by, created_at)
VALUES
    ('C1', 'Thai Cuisine Trần Khắc Chân', 'Thai Cuisine Trần Khắc Chân', 'https://lh3.google.com/u/0/d/1Z0m0i5ZEA9ooJxCzio0iNhjFJEAX7xHx', '10 Trần Khắc Chân, Quận 1, TP.HCM', '0122433833', 'chain1@example.com', 1, 4, 'Admin', '2023-01-03 12:00:00'),
    ('C2', 'Thai Cuisine Đặng Văn Bi', 'Thai Cuisine Đặng Văn Bi', 'https://lh3.google.com/u/0/d/1Z0m0i5ZEA9ooJxCzio0iNhjFJEAX7xHx', '20 Đặng Văn Bi, Bình Thọ, TP. Thủ Đức', '0123433833', 'chain2@example.com', 1, 4, 'Admin', '2023-01-03 12:00:00'),
    ('C3', 'Organic Foods', 'Công ty TNHH Organic Foods', 'https://lh3.google.com/u/0/d/198VNFoxgbhr-8gb46Gh2J4uabL8VKall', '123 Đường Lê Lai, Quận 1, TP.HCM', '0123456789', 'merchant1@example.com', 1, 1, 'Admin', '2023-01-01 10:00:00'),
    ('C4', 'Thực phẩm Annamese GoGo', 'Công ty TNHH Thực phẩm Annamese GoGo', 'https://lh3.google.com/u/0/d/1wOeTjLNJYWIsPrH9l8-rGfVdmVoSbQCw', '456 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', '0987654321', 'merchant2@example.com', 1, 2, 'Admin', '2023-01-02 11:00:00'),
    ('C5', 'Cửa hàng Ramenly', 'Công ty TNHH Ramenly', 'https://lh3.google.com/u/0/d/1Sh46E6RvyvyRDcegmagajLyQwcX2b4sh', '789 Đường Lê Thánh Tôn, Quận 3, TP.HCM', '0111222333', 'merchant3@example.com', 1, 3, 'Admin', '2023-01-03 12:00:00'),
    ('C6', 'Everest Beauty', 'Công ty TNHH Everest Beauty', 'https://lh3.google.com/u/0/d/1aEgq3rPkkHIL34sb8tZL3DPdBZ1XASGo', '234 Đường Hoàng Sa, Quận 1, TP.HCM', '0777888999', 'merchant5@example.com', 1, 5,'Admin', '2023-01-05 14:00:00'),
    ('C7', 'Cửa hàng Future Tech', 'Công ty TNHH Future Tech', 'https://lh3.google.com/u/0/d/1LCemRMd7EUviKLx4mDziBcAaLaHnp_W5', '567 Đường Cách Mạng Tháng Tám, Quận 3, TP.HCM', '0222334444', 'merchant6@example.com', 1, 6,'Admin', '2023-01-06 15:00:00'),
    ('C8', 'Sport Light', 'Công ty TNHH Sport Light', 'https://lh3.google.com/u/0/d/11LDi-iYcAGBFgnxHEGx0SvmBqWBMEpIu', '890 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', '0556667777', 'merchant7@example.com', 1, 7, 'Admin', '2023-01-07 16:00:00'),
    ('C9', 'Travel Buddy', 'Công ty TNHH Travel Buddy', 'https://lh3.google.com/u/0/d/1G9jZakH0pMAzXLIW5wbGdX_rula_t8Go', '345 Đường Phan Xích Long, Quận Phú Nhuận, TP.HCM', '0884349050', 'merchant8@example.com', 1, 8, 'Admin', '2023-01-08 17:00:00'),
    ('C10', 'Quần áo thể thao Soccer', 'Công ty TNHH Soccer Cloth', 'https://lh3.google.com/u/0/d/1TBVaVaVIUt4aJLs7hYF07zPxSW8dMyWB', '678 Đường Trần Hưng Đạo, Quận 5, TP.HCM', '0965227633', 'merchant9@example.com', 1, 9, 'Admin', '2023-01-09 18:00:00'),
    ('C11', 'Gradient Gaming', 'Công ty TNHH Gradient Gaming', 'https://lh3.google.com/u/0/d/1KZv03hFlItg0PxqFfGdTbd--9CFKr8H1', '901 Đường Lê Văn Sỹ, Quận 3, TP.HCM', '0945556666', 'merchant10@example.com', 1, 10, 'Admin', '2023-01-10 19:00:00'),
    ('C12', 'My Cosmetic and Spa', 'Công ty TNHH My Cosmetic and Spa', 'https://lh3.google.com/u/0/d/1bCPeAkjtcpSB7hsHW-EZ_gNm-nflwnyx', '234 Đường Lê Đại Hành, Quận 10, TP.HCM', '0772485919', 'merchant11@example.com', 1, 11, 'Admin', '2023-01-11 20:00:00'),
    ('C13', 'Monster Coffee', 'Công ty TNHH Monster Coffee', 'https://lh3.google.com/u/0/d/111diTDOZAOcejZLHO2Adak_6Bxst5NTs', '567 Đường Nguyễn Trãi, Quận 1, TP.HCM', '0323133644', 'merchant12@example.com', 1, 12, 'Admin', '2023-01-12 21:00:00'),
    ('C14', 'Sushi Japan', 'Công ty TNHH Sushi Japan', 'https://lh3.google.com/u/0/d/1zTNpkvPLoSVI0Z4KiU7Axrjmlzj2Z-hh', '890 Đường Bùi Viện, Quận 1, TP.HCM', '0556617287', 'merchant13@example.com', 1, 13, 'Admin', '2023-01-13 22:00:00'),
    ('C15', 'Cửa hàng Nowi', 'Công ty TNHH Nowi', 'https://lh3.google.com/u/0/d/1SNHWH_MBO20LDUBIhOoECdynTkFLMVCI', '345 Đường Cao Thắng, Quận 3, TP.HCM', '0889991010', 'merchant14@example.com', 1, 14, 'Admin', '2023-01-14 23:00:00'),
    ('C16', 'Mighty Lion', 'Công ty TNHH Mighty Lion', 'https://lh3.google.com/u/0/d/1RCjLkFXU4Vy5C99YNoQ4gf-sTSj0zrEP', '678 Đường Huỳnh Văn Bánh, Quận Phú Nhuận, TP.HCM', '0122213453', 'merchant15@example.com', 1, 15, 'Admin', '2023-01-15 00:00:00'),
    ('C17', 'Art Academy', 'Art Academy', 'https://lh3.google.com/u/0/d/1X51Awq520qjfJ6tiLftIQ69h13j_P-C1', '901 Đường Lý Tự Trọng, Quận 1, TP.HCM', '0455566266', 'merchant16@example.com', 1, 16, 'Admin', '2023-01-16 01:00:00');

INSERT INTO store
(store_code, name, address, phone, status, id_chain, id_merchant, created_by, created_at)
VALUES
    ('S01', 'Cửa hàng Organic Foods', '123 Đường Lê Lai, Quận 1, TP.HCM', '0123456789', 1, 3, 1, 'Admin', '2023-01-01 10:00:00'),
    ('S02', 'Cửa hàng Thực phẩm Annamese GoGo', '456 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', '0987654321', 1, 2, 2, 'Admin', '2023-01-02 11:00:00'),
    ('S03', 'Cửa hàng Ramenly', '789 Đường Lê Thánh Tôn, Quận 3, TP.HCM', '0111222333', 1, 5, 3, 'Admin', '2023-01-03 12:00:00'),
    ('S04', 'Thai Cuisine Trần Khắc Chân', '10 Đường Trần Khắc Chân, Quận 1, TP.HCM', '0122433833', 1, 1, 4, 'Admin', '2023-01-04 13:00:00'),
    ('S05', 'Thai Cuisine Đặng Văn Bi', '20 Đặng Văn Bi, Bình Thọ, TP. Thủ Đức', '0123433833', 1, 2, 4, 'Admin', '2023-01-04 13:00:00'),
    ('S06', 'Everest Beauty', '234 Đường Hoàng Sa, Quận 1, TP.HCM', '0777888999', 1, 6, 5, 'Admin', '2023-01-05 14:00:00'),
    ('S07', 'Cửa hàng Future Tech', '567 Đường Cách Mạng Tháng Tám, Quận 3, TP.HCM', '0222334444', 1, 7, 6, 'Admin', '2023-01-06 15:00:00'),
    ('S08', 'Sport Light', '890 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM', '0556667777', 1, 8, 7, 'Admin', '2023-01-07 16:00:00'),
    ('S09', 'Travel Buddy', '345 Đường Phan Xích Long, Quận Phú Nhuận, TP.HCM', '0884349050', 1, 9, 8, 'Admin', '2023-01-08 17:00:00'),
    ('S10', 'Quần áo thể thao Soccer', '678 Đường Trần Hưng Đạo, Quận 5, TP.HCM', '0965227633', 1, 10, 9, 'Admin', '2023-01-09 18:00:00'),
    ('S11', 'Gradient Gaming', '901 Đường Lê Văn Sỹ, Quận 3, TP.HCM', '0945556666', 1, 11, 10, 'Admin', '2023-01-10 19:00:00'),
    ('S12', 'My Cosmetic and Spa', '234 Đường Lê Đại Hành, Quận 10, TP.HCM', '0772485919', 1, 12, 11, 'Admin', '2023-01-11 20:00:00'),
    ('S13', 'Monster Coffee', '567 Đường Nguyễn Trãi, Quận 1, TP.HCM', '0323133644', 1, 13, 12, 'Admin', '2023-01-12 21:00:00'),
    ('S14', 'Sushi Japan', '890 Đường Bùi Viện, Quận 1, TP.HCM', '0556617287', 1, 14, 13, 'Admin', '2023-01-13 22:00:00'),
    ('S15', 'Cửa hàng Nowi', '345 Đường Cao Thắng, Quận 3, TP.HCM', '0889991010', 1, 15, 14, 'Admin', '2023-01-14 23:00:00'),
    ('S16', 'Mighty Lion', '678 Đường Huỳnh Văn Bánh, Quận Phú Nhuận, TP.HCM', '0122213453', 1, 16, 15, 'Admin', '2023-01-15 00:00:00'),
    ('S17', 'Art Academy', '901 Đường Lý Tự Trọng, Quận 1, TP.HCM', '0455566266', 1, 17, 16, 'Admin', '2023-01-16 01:00:00');


INSERT INTO warehouse
(warehouse_code, id_label, name, term_of_use, thumbnail_url, banner_url, price, original_price, id_discount_type, discount_amount, max_discount_amount, available_from, available_to, status, show_on_web, capacity, voucher_channel, id_category, created_by, created_at, description)
VALUES

    ('W01', 1, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_vega.png', 'http://localhost:8082/warehouse/banner_vega.png', 20.000, 100000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_vega.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W02', 1, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_thai.png', 'http://localhost:8082/warehouse/banner_thai.png', 10.000, 100000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_thai.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W03', 1, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_thai.png', 'http://localhost:8082/warehouse/banner_travel.png', 11.000, 100000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_thai.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W04', 1, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_steak.png', 'http://localhost:8082/warehouse/banner_steak.png', 50.000, 100000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_steak.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W05', 1, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_spa.png', 'http://localhost:8082/warehouse/banner_spa.png', 5.000, 100000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_spa.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W06', 1, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_shower.png', 'http://localhost:8082/warehouse/banner_shower.png', 20.000, 100000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_shower.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W07', 1, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_tech.png', 'http://localhost:8082/warehouse/banner_tech.png',90.000, 100000, 1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_tech.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W08', 1, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_sport.png', 'http://localhost:8082/warehouse/banner_sport.png', 80.000, 100000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_sport.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W09', 1, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_sushi.png', 'http://localhost:8082/warehouse/banner_sushi.png', 25.000, 100000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_sushi.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W10', 1, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_art.png', 'http://localhost:8082/warehouse/banner_art.png',200.000, 100000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/banner_art.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W11', 1, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_makeup.png', 'http://localhost:8082/warehouse/banner_makeup.png', 30.000, 100000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_makeup.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W12', 1, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_organe.png', 'http://localhost:8082/warehouse/banner_organe.png', 20.000, 100000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_organe.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W13', 1, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_culow.png', 'http://localhost:8082/warehouse/banner_culow.png', 50.000, 100000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_culow.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
        ('W14', 1, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/thumbnail_game.png', 'http://localhost:8082/warehouse/banner_game.png', 20.000, 100000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/thumbnail_game.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W15', 1, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png', 'http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png', 18.000, 100000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    ),
    ('W16', 1, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png', 'http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png', 18.000, 100000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00',
     '<div>
<div>
<h2>Chi tiết sản phẩm</h2>
<img
classname="Po"
alt=""
src="http://localhost:8082/warehouse/318a83e10d0e4988bb6be294d82f2523.png" />
</div>
<hr />
	<div>
<h2>Chính sách bảo hành</h2>
<h4>Thời gian bảo hành</h4>
<p>12 tháng</p>
<h4>Cách thức bảo hành</h4>
<p>Đổi mới voucher khác nếu lỗi trong quá trình sử dụng hoặc hoàn tiền nếu không có sản phẩm bảo hành.</p>
</div>
<hr />
<div>
<h3>Điều kiện sử dụng</h3>
            <p>Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.</p>
            <h3>Hướng dẫn sử dụng voucher</h3>
            <p>
              <b>Bước 1: </b>Khách hàng đổi điểm lấy voucher và nhấn sử dụng
              voucher nhận được từ SmartVoucher
            </p>
            <p>
              <b>Bước 2: </b>Khách hàng đặt hàng hóa theo mong muốn
            </p>
            <p>
              <b> Bước 3: </b>Tại cửa hàng chấp nhận ưu đãi, khách hàng cung
              cấp voucher để được giảm giá hoặc nhập mã giảm giá vào mục ưu
              đãi khi thanh toán trực tuyến
            </p>
            <p>
              <b>Bước 4: </b>Mệnh giá voucher sẽ được trừ tiền vào hóa đơn
            </p>
            <h2>Đến với SmartVoucher nhận ưu đãi ngay!</h2>
</div>
<hr/>
</div>'
    );

#     ('W16', 2, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJtUkmxTz4OVfYY2XfybhNrnMCZtY0L-', 'http://localhost:8082/warehouse/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB', 20.000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00'),
#     ('W17', 2, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1KxDpUQh2YvwRC5EO_mxjCDjtFyWluYaV', 'http://localhost:8082/warehouse/1anNPxeOYwkWhpGnq55w9C2JCoaotQ9NM', 10.000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00'),
#     ('W18', 2, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJA2vQXocDRPNb5_SRFdldWKqBCJm1iY', 'http://localhost:8082/warehouse/1r3fgHLHkE5KlfRoi4AtOTVV2SzOGrFPE', 11.000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00'),
#     ('W19', 2, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1r8rqSZ_yv3XOHkY9eJbsvliCvX6TOdb0', 'http://localhost:8082/warehouse/1Cn00l27CFNDl1D_AC-VELHsaclqp4PyV', 50.000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00'),
#     ('W20', 2, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1pEApfsfH_kK1yMUx1yoci4h54nTX6lu2', 'http://localhost:8082/warehouse/1ik6wac5i0UHlXu4KV1JzMx4se0ftxFgP', 5.000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00'),
#     ('W21', 2, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/190qke_W__xbmg0Bp9Knuiu8J-Sjl5NU1', 'http://localhost:8082/warehouse/1uA65oeU4b8OhJvdxDHUJwcn4LW4XPxlU', 20.000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00'),
#     ('W22', 2, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1MKYeHSKU5VUzoMELfjatfkuWxJ-VQEFH', 'http://localhost:8082/warehouse/11t8MHGeL1Ao8WQ_rQ7bc-HfRLSWvI-oM',90.000 ,1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00'),
#     ('W23', 2, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/10X1MXgVMdrGCUWbJJhcUByfv2w0grEzh', 'http://localhost:8082/warehouse/1Y8wBfvPSH1Pn_hZFrl85zrvmSVk74vMy', 80.000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00'),
#     ('W24', 2, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/145ybGB1fVkanFvTXnaSB7dg7qBnDttZj', 'http://localhost:8082/warehouse/1dl_dk8bp6ynvc0TuPr09Nb1OkNwQZutY', 25.000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00'),
#     ('W25', 2, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1oDOHEhfGdwt8lCBm8o5LzSVfTVO8anyV', 'http://localhost:8082/warehouse/1Dzcdndh8KdiEU6SD5FlefSpjps9kW0oO',200.000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00'),
#     ('W26', 2, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1aA9pLRrkDmFD21vxl0TqrjGxFC4J58aO', 'http://localhost:8082/warehouse/1oiosDBO7JN3Uwn9rpW8W-QG4ZRWjUL9f', 30.000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00'),
#     ('W27', 2, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/13_XAI9APvonm-GriYuT69hON6Ealh6Zn', 'http://localhost:8082/warehouse/1Hf2AnS04jucxeIxY4R-7cwVrg7UT1bcv', 20.000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00'),
#     ('W28', 2, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1UTad-tEoduL0I6MNQJWwPxaod8hz3Zcz', 'http://localhost:8082/warehouse/12lPiKzHUV-M_u7R6i5hXW7q6_nPEYSzl', 50.000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00'),
#     ('W29', 2, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1trE3cFwDovjrgDLJeydVyqjaGvcbDBSc', 'http://localhost:8082/warehouse/1lKs8dBv9ZsVj-NI2LL5Ga_ggcTqujq8X', 20.000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00'),
#     ('W30', 2, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#     ('W31', 2, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#
#     ('W32', 3, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJtUkmxTz4OVfYY2XfybhNrnMCZtY0L-', 'http://localhost:8082/warehouse/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB', 20.000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00'),
#     ('W33', 3, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1KxDpUQh2YvwRC5EO_mxjCDjtFyWluYaV', 'http://localhost:8082/warehouse/1anNPxeOYwkWhpGnq55w9C2JCoaotQ9NM', 10.000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00'),
#     ('W34', 3, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJA2vQXocDRPNb5_SRFdldWKqBCJm1iY', 'http://localhost:8082/warehouse/1r3fgHLHkE5KlfRoi4AtOTVV2SzOGrFPE', 11.000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00'),
#     ('W35', 3, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1r8rqSZ_yv3XOHkY9eJbsvliCvX6TOdb0', 'http://localhost:8082/warehouse/1Cn00l27CFNDl1D_AC-VELHsaclqp4PyV', 50.000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00'),
#     ('W36', 3, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1pEApfsfH_kK1yMUx1yoci4h54nTX6lu2', 'http://localhost:8082/warehouse/1ik6wac5i0UHlXu4KV1JzMx4se0ftxFgP', 5.000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00'),
#     ('W37', 3, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/190qke_W__xbmg0Bp9Knuiu8J-Sjl5NU1', 'http://localhost:8082/warehouse/1uA65oeU4b8OhJvdxDHUJwcn4LW4XPxlU', 20.000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00'),
#     ('W38', 3, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1MKYeHSKU5VUzoMELfjatfkuWxJ-VQEFH', 'http://localhost:8082/warehouse/11t8MHGeL1Ao8WQ_rQ7bc-HfRLSWvI-oM',90.000 ,1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00'),
#     ('W39', 3, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/10X1MXgVMdrGCUWbJJhcUByfv2w0grEzh', 'http://localhost:8082/warehouse/1Y8wBfvPSH1Pn_hZFrl85zrvmSVk74vMy', 80.000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00'),
#     ('W40', 3, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/145ybGB1fVkanFvTXnaSB7dg7qBnDttZj', 'http://localhost:8082/warehouse/1dl_dk8bp6ynvc0TuPr09Nb1OkNwQZutY', 25.000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00'),
#     ('W41', 3, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1oDOHEhfGdwt8lCBm8o5LzSVfTVO8anyV', 'http://localhost:8082/warehouse/1Dzcdndh8KdiEU6SD5FlefSpjps9kW0oO',200.000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00'),
#     ('W42', 3, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1aA9pLRrkDmFD21vxl0TqrjGxFC4J58aO', 'http://localhost:8082/warehouse/1oiosDBO7JN3Uwn9rpW8W-QG4ZRWjUL9f', 30.000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00'),
#     ('W43', 3, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/13_XAI9APvonm-GriYuT69hON6Ealh6Zn', 'http://localhost:8082/warehouse/1Hf2AnS04jucxeIxY4R-7cwVrg7UT1bcv', 20.000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00'),
#     ('W44', 3, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1UTad-tEoduL0I6MNQJWwPxaod8hz3Zcz', 'http://localhost:8082/warehouse/12lPiKzHUV-M_u7R6i5hXW7q6_nPEYSzl', 50.000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00'),
#     ('W45', 3, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1trE3cFwDovjrgDLJeydVyqjaGvcbDBSc', 'http://localhost:8082/warehouse/1lKs8dBv9ZsVj-NI2LL5Ga_ggcTqujq8X', 20.000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00'),
#     ('W46', 3, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#     ('W47', 3, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#
#     ('W48', 4, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJtUkmxTz4OVfYY2XfybhNrnMCZtY0L-', 'http://localhost:8082/warehouse/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB', 20.000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00'),
#     ('W49', 4, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1KxDpUQh2YvwRC5EO_mxjCDjtFyWluYaV', 'http://localhost:8082/warehouse/1anNPxeOYwkWhpGnq55w9C2JCoaotQ9NM', 10.000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00'),
#     ('W50', 4, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJA2vQXocDRPNb5_SRFdldWKqBCJm1iY', 'http://localhost:8082/warehouse/1r3fgHLHkE5KlfRoi4AtOTVV2SzOGrFPE', 11.000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00'),
#     ('W51', 4, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1r8rqSZ_yv3XOHkY9eJbsvliCvX6TOdb0', 'http://localhost:8082/warehouse/1Cn00l27CFNDl1D_AC-VELHsaclqp4PyV', 50.000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00'),
#     ('W52', 4, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1pEApfsfH_kK1yMUx1yoci4h54nTX6lu2', 'http://localhost:8082/warehouse/1ik6wac5i0UHlXu4KV1JzMx4se0ftxFgP', 5.000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00'),
#     ('W53', 4, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/190qke_W__xbmg0Bp9Knuiu8J-Sjl5NU1', 'http://localhost:8082/warehouse/1uA65oeU4b8OhJvdxDHUJwcn4LW4XPxlU', 20.000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00'),
#     ('W54', 4, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1MKYeHSKU5VUzoMELfjatfkuWxJ-VQEFH', 'http://localhost:8082/warehouse/11t8MHGeL1Ao8WQ_rQ7bc-HfRLSWvI-oM',90.000 ,1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00'),
#     ('W55', 4, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/10X1MXgVMdrGCUWbJJhcUByfv2w0grEzh', 'http://localhost:8082/warehouse/1Y8wBfvPSH1Pn_hZFrl85zrvmSVk74vMy', 80.000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00'),
#     ('W56', 4, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/145ybGB1fVkanFvTXnaSB7dg7qBnDttZj', 'http://localhost:8082/warehouse/1dl_dk8bp6ynvc0TuPr09Nb1OkNwQZutY', 25.000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00'),
#     ('W57', 4, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1oDOHEhfGdwt8lCBm8o5LzSVfTVO8anyV', 'http://localhost:8082/warehouse/1Dzcdndh8KdiEU6SD5FlefSpjps9kW0oO',200.000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00'),
#     ('W58', 4, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1aA9pLRrkDmFD21vxl0TqrjGxFC4J58aO', 'http://localhost:8082/warehouse/1oiosDBO7JN3Uwn9rpW8W-QG4ZRWjUL9f', 30.000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00'),
#     ('W59', 4, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/13_XAI9APvonm-GriYuT69hON6Ealh6Zn', 'http://localhost:8082/warehouse/1Hf2AnS04jucxeIxY4R-7cwVrg7UT1bcv', 20.000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00'),
#     ('W60', 4, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1UTad-tEoduL0I6MNQJWwPxaod8hz3Zcz', 'http://localhost:8082/warehouse/12lPiKzHUV-M_u7R6i5hXW7q6_nPEYSzl', 50.000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00'),
#     ('W61', 4, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1trE3cFwDovjrgDLJeydVyqjaGvcbDBSc', 'http://localhost:8082/warehouse/1lKs8dBv9ZsVj-NI2LL5Ga_ggcTqujq8X', 20.000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00'),
#     ('W62', 4, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#     ('W63', 4, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#
#     ('W64', 5, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJtUkmxTz4OVfYY2XfybhNrnMCZtY0L-', 'http://localhost:8082/warehouse/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB', 20.000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00'),
#     ('W65', 5, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1KxDpUQh2YvwRC5EO_mxjCDjtFyWluYaV', 'http://localhost:8082/warehouse/1anNPxeOYwkWhpGnq55w9C2JCoaotQ9NM', 10.000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00'),
#     ('W66', 5, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJA2vQXocDRPNb5_SRFdldWKqBCJm1iY', 'http://localhost:8082/warehouse/1r3fgHLHkE5KlfRoi4AtOTVV2SzOGrFPE', 11.000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00'),
#     ('W67', 5, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1r8rqSZ_yv3XOHkY9eJbsvliCvX6TOdb0', 'http://localhost:8082/warehouse/1Cn00l27CFNDl1D_AC-VELHsaclqp4PyV', 50.000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00'),
#     ('W68', 5, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1pEApfsfH_kK1yMUx1yoci4h54nTX6lu2', 'http://localhost:8082/warehouse/1ik6wac5i0UHlXu4KV1JzMx4se0ftxFgP', 5.000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00'),
#     ('W69', 5, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/190qke_W__xbmg0Bp9Knuiu8J-Sjl5NU1', 'http://localhost:8082/warehouse/1uA65oeU4b8OhJvdxDHUJwcn4LW4XPxlU', 20.000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00'),
#     ('W70', 5, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1MKYeHSKU5VUzoMELfjatfkuWxJ-VQEFH', 'http://localhost:8082/warehouse/11t8MHGeL1Ao8WQ_rQ7bc-HfRLSWvI-oM',90.000 ,1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00'),
#     ('W71', 5, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/10X1MXgVMdrGCUWbJJhcUByfv2w0grEzh', 'http://localhost:8082/warehouse/1Y8wBfvPSH1Pn_hZFrl85zrvmSVk74vMy', 80.000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00'),
#     ('W72', 5, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/145ybGB1fVkanFvTXnaSB7dg7qBnDttZj', 'http://localhost:8082/warehouse/1dl_dk8bp6ynvc0TuPr09Nb1OkNwQZutY', 25.000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00'),
#     ('W73', 5, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1oDOHEhfGdwt8lCBm8o5LzSVfTVO8anyV', 'http://localhost:8082/warehouse/1Dzcdndh8KdiEU6SD5FlefSpjps9kW0oO',200.000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00'),
#     ('W74', 5, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1aA9pLRrkDmFD21vxl0TqrjGxFC4J58aO', 'http://localhost:8082/warehouse/1oiosDBO7JN3Uwn9rpW8W-QG4ZRWjUL9f', 30.000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00'),
#     ('W75', 5, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/13_XAI9APvonm-GriYuT69hON6Ealh6Zn', 'http://localhost:8082/warehouse/1Hf2AnS04jucxeIxY4R-7cwVrg7UT1bcv', 20.000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00'),
#     ('W76', 5, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1UTad-tEoduL0I6MNQJWwPxaod8hz3Zcz', 'http://localhost:8082/warehouse/12lPiKzHUV-M_u7R6i5hXW7q6_nPEYSzl', 50.000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00'),
#     ('W77', 5, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1trE3cFwDovjrgDLJeydVyqjaGvcbDBSc', 'http://localhost:8082/warehouse/1lKs8dBv9ZsVj-NI2LL5Ga_ggcTqujq8X', 20.000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00'),
#     ('W78', 5, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#     ('W79', 5, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#
#     ('W80', 9, 'Giảm 50.000 cho đơn hàng cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJtUkmxTz4OVfYY2XfybhNrnMCZtY0L-', 'http://localhost:8082/warehouse/1qlIU_eMPvfCYlaHs8IDrTNQBE4jxhxwB', 20.000, 1, 50.000, 50.000, '2023-01-01 10:00:00', '2024-01-10 10:00:00', 1, 1, 100, 1, 4, 'Admin', '2023-01-01 10:00:00'),
#     ('W81', 9, 'Giảm 100,000đ Món Thái ngon chuẩn vị', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1KxDpUQh2YvwRC5EO_mxjCDjtFyWluYaV', 'http://localhost:8082/warehouse/1anNPxeOYwkWhpGnq55w9C2JCoaotQ9NM', 10.000, 1, 100.000, 100.000, '2023-02-01 11:00:00', '2024-02-10 11:00:00', 1, 1, 150, 1, 4, 'Admin', '2023-02-01 11:00:00'),
#     ('W82', 9, 'Giảm 10% tối đa 100% khi mua vé máy bay, khách sạn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1mJA2vQXocDRPNb5_SRFdldWKqBCJm1iY', 'http://localhost:8082/warehouse/1r3fgHLHkE5KlfRoi4AtOTVV2SzOGrFPE', 11.000, 2, 0.1, 100.000, '2023-03-01 12:00:00', '2024-03-10 12:00:00', 1, 1, 200, 1, 1, 'Admin', '2023-03-01 12:00:00'),
#     ('W83', 9, 'Giảm 20% Set Noel’s Special Menu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1r8rqSZ_yv3XOHkY9eJbsvliCvX6TOdb0', 'http://localhost:8082/warehouse/1Cn00l27CFNDl1D_AC-VELHsaclqp4PyV', 50.000, 2, 0.2, 150.000, '2023-04-01 13:00:00', '2024-04-10 13:00:00', 1, 1, 250, 1, 4, 'Admin', '2023-04-01 13:00:00'),
#     ('W84', 9, 'Giảm 50.000đ dịch vụ gội đầu dưỡng sinh', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1pEApfsfH_kK1yMUx1yoci4h54nTX6lu2', 'http://localhost:8082/warehouse/1ik6wac5i0UHlXu4KV1JzMx4se0ftxFgP', 5.000, 1, 50.000, 50.000, '2023-05-01 14:00:00', '2024-05-10 14:00:00', 1, 1, 300, 1, 6, 'Admin', '2023-05-01 14:00:00'),
#     ('W85', 9, 'Giảm 70.000đ mua hàng Black Friday', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/190qke_W__xbmg0Bp9Knuiu8J-Sjl5NU1', 'http://localhost:8082/warehouse/1uA65oeU4b8OhJvdxDHUJwcn4LW4XPxlU', 20.000, 1, 70.000, 70.000, '2023-06-01 15:00:00', '2024-06-10 15:00:00', 1, 1, 350, 1, 3, 'Admin', '2023-06-01 15:00:00'),
#     ('W86', 9, 'Sale khủng mua đồ công nghệ', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1MKYeHSKU5VUzoMELfjatfkuWxJ-VQEFH', 'http://localhost:8082/warehouse/11t8MHGeL1Ao8WQ_rQ7bc-HfRLSWvI-oM',90.000 ,1, 300.000, 300.000, '2023-07-01 16:00:00', '2024-07-10 16:00:00', 1, 1, 400, 1, 8, 'Admin', '2023-07-01 16:00:00'),
#     ('W87', 9, 'Mua 1 tặng 1 sản phẩm quần áo thể thao', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/10X1MXgVMdrGCUWbJJhcUByfv2w0grEzh', 'http://localhost:8082/warehouse/1Y8wBfvPSH1Pn_hZFrl85zrvmSVk74vMy', 80.000, 3, 0, 0, '2023-08-01 17:00:00', '2024-08-10 17:00:00', 1, 1, 450, 1, 9, 'Admin', '2023-08-01 17:00:00'),
#     ('W88', 9, 'Freeship Sushi cuối tuần', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/145ybGB1fVkanFvTXnaSB7dg7qBnDttZj', 'http://localhost:8082/warehouse/1dl_dk8bp6ynvc0TuPr09Nb1OkNwQZutY', 25.000, 4, 0, 0, '2023-09-01 18:00:00', '2024-09-10 18:00:00', 1, 1, 500, 1, 1, 'Admin', '2023-09-01 18:00:00'),
#     ('W89', 9, 'Giảm 10% khóa học tại Art Academy', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1oDOHEhfGdwt8lCBm8o5LzSVfTVO8anyV', 'http://localhost:8082/warehouse/1Dzcdndh8KdiEU6SD5FlefSpjps9kW0oO',200.000, 2, 0.1, 500.000, '2023-10-01 19:00:00', '2024-10-10 19:00:00', 1, 1, 550, 1, 5, 'Admin', '2023-10-01 19:00:00'),
#     ('W90', 9, 'Giảm 70.000đ dưỡng da, trang điểm', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1aA9pLRrkDmFD21vxl0TqrjGxFC4J58aO', 'http://localhost:8082/warehouse/1oiosDBO7JN3Uwn9rpW8W-QG4ZRWjUL9f', 30.000, 1, 70.000, 70.000, '2023-11-01 20:00:00', '2023-12-10 20:00:00', 1, 1, 600, 1, 6, 'Admin', '2023-11-01 20:00:00'),
#     ('W91', 9, 'Tặng 1kg Cam khi mua Cam Úc', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/13_XAI9APvonm-GriYuT69hON6Ealh6Zn', 'http://localhost:8082/warehouse/1Hf2AnS04jucxeIxY4R-7cwVrg7UT1bcv', 20.000, 3, 0, 0, '2023-12-01 21:00:00', '2023-12-10 21:00:00', 1, 1, 650, 1, 4, 'Admin', '2023-12-01 21:00:00'),
#     ('W92', 9, 'Giảm 10% tour Cù Lao Câu', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1UTad-tEoduL0I6MNQJWwPxaod8hz3Zcz', 'http://localhost:8082/warehouse/12lPiKzHUV-M_u7R6i5hXW7q6_nPEYSzl', 50.000, 2, 0.1, 100.000, '2023-11-01 22:00:00', '2024-01-10 22:00:00', 1, 1, 700, 1, 1, 'Admin', '2024-01-01 22:00:00'),
#     ('W93', 9, 'Giảm 50.000đ thẻ nạp Game', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/1trE3cFwDovjrgDLJeydVyqjaGvcbDBSc', 'http://localhost:8082/warehouse/1lKs8dBv9ZsVj-NI2LL5Ga_ggcTqujq8X', 20.000, 1, 50.000, 50.000, '2023-02-01 23:00:00', '2024-02-10 23:00:00', 1, 1, 750, 1, 10, 'Admin', '2024-02-01 23:00:00'),
#     ('W94', 9, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00'),
#     ('W95', 9, 'Giảm 50.000đ trên tổng hóa đơn', 'Mỗi voucher chỉ được sử dụng 01 lần. Không áp dụng cho Combo. Không được tách hoặc cộng gộp hóa đơn.', 'http://localhost:8082/warehouse/16fy6FOM3xWawB5siUIdpoz8byXEutvmK', 'http://localhost:8082/warehouse/1APDAJDso1zStLJATNPGe2WAiYP7LYUyZ', 18.000, 1, 50.000, 50.000, '2023-03-01 00:00:00', '2024-03-10 00:00:00', 1, 1, 800, 1, 3, 'Admin', '2024-03-01 00:00:00');
#

INSERT INTO warehouse_merchant (id_warehouse, id_merchant, id_role, created_by, created_at)
VALUES
#     (1,1,3,'Admin','2023-05-01 14:00:00'),
#     (2,4,3,'Admin','2023-05-01 14:00:00'),
#     (3,8,3,'Admin','2023-05-01 14:00:00'),
#     (4,12,3,'Admin','2023-05-01 14:00:00'),
#     (5,5,3,'Admin','2023-05-01 14:00:00'),
#     (6,14,3,'Admin','2023-05-01 14:00:00'),
#     (7,6,3,'Admin','2023-05-01 14:00:00'),
#     (8,15,3,'Admin','2023-05-01 14:00:00'),
#     (9,13,3,'Admin','2023-05-01 14:00:00'),
#     (10,16,3,'Admin','2023-05-01 14:00:00'),
#     (11,11,3,'Admin','2023-05-01 14:00:00'),
#     (12,2,3,'Admin','2023-05-01 14:00:00'),
#     (13,8,3,'Admin','2023-05-01 14:00:00'),
#     (14,10,3,'Admin','2023-05-01 14:00:00'),
#     (15,3,3,'Admin','2023-05-01 14:00:00'),
#     (16,7,3,'Admin','2023-05-01 14:00:00');
(17,1,3,'Admin','2023-05-01 14:00:00'),
(18,4,3,'Admin','2023-05-01 14:00:00'),
(19,8,3,'Admin','2023-05-01 14:00:00'),
(20,12,3,'Admin','2023-05-01 14:00:00'),
(21,5,3,'Admin','2023-05-01 14:00:00'),
(22,14,3,'Admin','2023-05-01 14:00:00'),
(23,6,3,'Admin','2023-05-01 14:00:00'),
(24,15,3,'Admin','2023-05-01 14:00:00'),
(25,13,3,'Admin','2023-05-01 14:00:00'),
(26,16,3,'Admin','2023-05-01 14:00:00'),
(27,11,3,'Admin','2023-05-01 14:00:00'),
(28,2,3,'Admin','2023-05-01 14:00:00'),
(29,8,3,'Admin','2023-05-01 14:00:00'),
(30,10,3,'Admin','2023-05-01 14:00:00'),
(31,3,3,'Admin','2023-05-01 14:00:00'),
(32,7,3,'Admin','2023-05-01 14:00:00');



INSERT INTO warehouse_stores (id_warehouse, id_store, created_by, created_at)
VALUES
#     (1, 1, 'Admin', '2023-01-01 10:00:00'),
#     (2, 4, 'Admin', '2023-01-02 11:00:00'),
#     (3, 9, 'Admin', '2023-01-03 12:00:00'),
#     (4, 13, 'Admin', '2023-01-04 13:00:00'),
#     (5, 6, 'Admin', '2023-01-05 14:00:00'),
#     (6, 15, 'Admin', '2023-01-06 15:00:00'),
#     (7, 7, 'Admin', '2023-01-07 16:00:00'),
#     (8, 16, 'Admin', '2023-01-08 17:00:00'),
#     (9, 14, 'Admin', '2023-01-09 18:00:00'),
#     (10, 17, 'Admin', '2023-01-10 19:00:00'),
#     (11, 12, 'Admin', '2023-01-11 20:00:00'),
#     (12, 2, 'Admin', '2023-01-12 21:00:00'),
#     (13, 9, 'Admin', '2023-01-13 22:00:00'),
#     (14, 11, 'Admin', '2023-01-14 23:00:00'),
#     (15, 4, 'Admin', '2023-01-15 00:00:00'),
#     (16, 3, 'Admin', '2023-01-15 00:00:00');
(17, 1, 'Admin', '2023-01-01 10:00:00'),
(18, 4, 'Admin', '2023-01-02 11:00:00'),
(19, 9, 'Admin', '2023-01-03 12:00:00'),
(20, 13, 'Admin', '2023-01-04 13:00:00'),
(21, 6, 'Admin', '2023-01-05 14:00:00'),
(22, 15, 'Admin', '2023-01-06 15:00:00'),
(23, 7, 'Admin', '2023-01-07 16:00:00'),
(24, 16, 'Admin', '2023-01-08 17:00:00'),
(25, 14, 'Admin', '2023-01-09 18:00:00'),
(26, 17, 'Admin', '2023-01-10 19:00:00'),
(27, 12, 'Admin', '2023-01-11 20:00:00'),
(28, 2, 'Admin', '2023-01-12 21:00:00'),
(29, 9, 'Admin', '2023-01-13 22:00:00'),
(30, 11, 'Admin', '2023-01-14 23:00:00'),
(31, 4, 'Admin', '2023-01-15 00:00:00'),
(32, 3, 'Admin', '2023-01-15 00:00:00');
