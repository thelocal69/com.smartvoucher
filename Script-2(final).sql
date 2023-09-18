-- CREATE DATABASE CybersoftProject;
USE CybersoftProject;

CREATE TABLE merchant(
	id int auto_increment,
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	merchant_code varchar(20) unique,
	name varchar(100),
	legal_name varchar(100),
	logo_url varchar(100),
	address varchar(100),	
	phone varchar(20),
	email varchar(50),
	description varchar(512),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	
	PRIMARY KEY(id)
);

CREATE TABLE chains(
	id int auto_increment,
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	chain_code varchar(20) unique, 
	name varchar(100),
	legal_name varchar(100),
	logo_url varchar(100),
	address varchar(100),
	phone varchar(20),
	email varchar(50),
	description varchar(512),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	id_merchant int NOT null,
	
	primary key(id)
);

CREATE TABLE store(
	id int auto_increment,
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	store_code varchar(20) unique, 
	name varchar(100),
	address varchar(100),
	phone varchar(20),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	description varchar(512),
	created_by varchar(50),
	updated_by varchar(50),
	id_chain int NOT null,
	id_merchant int NOT null,
	
	primary key(id)
);

CREATE TABLE warehouse(
	id int auto_increment,
	warehouse_code varchar(20) unique,
	-- id_warehouse_issuer int NOT null, 
	-- id_warehouse_acquirer int NOT null,
	-- id_store_applied int,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	name varchar(100),
	description varchar(512),
	term_of_use varchar(255),
	banner_url varchar(255),
	thumbnail_url varchar(255),
	discount_type int,
	discount_amount decimal(8,3),
	max_discount_amount decimal(8,3),
	available_from timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	available_to timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	show_on_web int NOT null, -- giá trị 0:không hiển thị cho end user và giá trị 1:được hiển thị cho end user
	capacity int,
	vouncher_channel int NOT null, -- giá trị 0:áp dụng hình thức offline và giá trị 1:áp dụng hình thức online
	id_category int NOT null,
	
	primary key(id)
);



CREATE TABLE warehouse_issuer(
	id_warehouse int -- auto_increment,
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	-- id_warehouse int,
	id_merchant int NOT null,
	id_chain int NOT null,	
	
	primary key(id_warehouse)
);

CREATE TABLE discount_type(
	id int auto_increment,
	code varchar(20) unique,
	name varchar(100),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	primary key(id)
);

CREATE TABLE category(
	id int auto_increment,
	category_code varchar(20) unique,
	name varchar(100),
	status int NOT null, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE warehouse_acquirer(
	-- id int auto_increment,
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	id_warehouse int NOT null,
	id_merchant int NOT null,
	id_chain int NOT null,
	
	primary key(id_warehouse)
);

CREATE TABLE warehouse_stores(
	-- id int auto_increment,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	id_warehouse int NOT null,
	id_store int NOT null,
		
	primary key(id_warehouse, id_store)
);

CREATE TABLE warehouse_serial(
	-- id int auto_increment,
	id_warehouse int NOT null,
	-- batch_code varchar(20) unique,
	id_serial int unique,
		
	-- UNIQUE(id_serial),
	primary key(id_warehouse, id_serial)
);

CREATE TABLE serial(
	id int auto_increment,
	batch_code varchar(20) unique,
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
	id int auto_increment,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	id_serial int unique NOT null,
	id_warehouse int NOT null,
	id_category int unique NOT null,
	status int NOT null, -- giá trị 0:chưa phát hành, giá trị 1:đã phát hành, giá trị 2: đã sử dụng, giá trị 3: đã hết hạn
	id_order int unique NOT null, 
	claimed_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	redeemedtime_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	expired_time timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	discount_type varchar(100),
	discount_amount decimal(8,3),
	banner_url varchar(150),
	thumbnail_url varchar(150),
	acquirer_logo_url varchar(150),
	term_of_use varchar(512),
	description varchar(512),
	vouncher_channel varchar(100),
	available_from timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	available_to timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	applied_store varchar(20),
	id_user int NOT null,
	
	primary key(id)
);

CREATE TABLE ticket_history(
	id int auto_increment,
	id_ticket int not null,
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
	id int auto_increment,
	created_by varchar(50),
	order_no varchar(20) unique, 
	status int NOT null, -- giá trị 0:ngừng áp dụng và giá trị 1:còn áp dụng
	id_user int NOT null,
	quantity int,
	id_warehouse int NOT null,
	
	primary key(id)
);

CREATE TABLE users(
	id int auto_increment,
	member_code varchar(20) unique,
	first_name varchar(20),
	last_name varchar(20),
	full_name varchar(20),
	username varchar(50),
	password varchar(100),
	phone varchar(20),
	email varchar(50),
	status int NOT null,
	address varchar(100),
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	-- id_role int NOT null,	
	
	primary key (id)
);

CREATE TABLE roles(
	id int auto_increment,
	name varchar(100),
	role_code varchar(20) UNIQUE,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key (id)
);

CREATE TABLE roles_users(
	id_role int,
	id_user int,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	PRIMARY KEY(id_role, id_user)
);



ALTER TABLE chains ADD CONSTRAINT FK_id_merchant_chain FOREIGN KEY (id_merchant) REFERENCES merchant(id);

ALTER TABLE store ADD CONSTRAINT FK_id_chain_store FOREIGN KEY (id_chain) REFERENCES chains(id); 
ALTER TABLE store ADD CONSTRAINT FK_id_merchant_store FOREIGN KEY (id_merchant) REFERENCES merchant(id);

-- ALTER TABLE warehouse_issuer ADD CONSTRAINT FK_id_warehouse_warehouse_issuer FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_issuer ADD CONSTRAINT FK_id_merchant_warehouse_issuer FOREIGN KEY (id_merchant) REFERENCES merchant(id);
ALTER TABLE warehouse_issuer ADD CONSTRAINT FK_id_chain_warehouse_issuer FOREIGN KEY (id_chain) REFERENCES chains(id);

ALTER TABLE warehouse_acquirer ADD CONSTRAINT FK_id_warehouse_warehouse_acquirer FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_acquirer ADD CONSTRAINT FK_id_merchant_warehouse_acquirer FOREIGN KEY (id_merchant) REFERENCES merchant(id);
ALTER TABLE warehouse_acquirer ADD CONSTRAINT FK_id_chain_warehouse_acquirer FOREIGN KEY (id_chain) REFERENCES chains(id); 

ALTER TABLE warehouse ADD CONSTRAINT FK_discount_type_warehouse FOREIGN KEY (discount_type) REFERENCES discount_type(id);
ALTER TABLE warehouse ADD CONSTRAINT FK_id_warehouse_issuer_warehouse FOREIGN KEY (id_warehouse_issuer) REFERENCES warehouse_issuer(id);
ALTER TABLE warehouse ADD CONSTRAINT FK_id_warehouse_acquirer_warehouse FOREIGN KEY (id_warehouse_acquirer) REFERENCES warehouse_acquirer(id);
ALTER TABLE warehouse ADD CONSTRAINT FK_id_category_warehouse FOREIGN KEY (id_category) REFERENCES category(id);

ALTER TABLE warehouse_stores ADD CONSTRAINT FK_id_warehouse_warehouse_stores FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_stores ADD CONSTRAINT FK_id_store_warehouse_stores FOREIGN KEY (id_store) REFERENCES store(id);

ALTER TABLE warehouse_serial ADD CONSTRAINT FK_id_warehouse_warehouse_serial FOREIGN KEY (id_warehouse) REFERENCES warehouse(id);
ALTER TABLE warehouse_serial ADD CONSTRAINT FK_id_serial_warehouse_serial FOREIGN KEY(id_serial) REFERENCES serial(id);

ALTER TABLE ticket ADD CONSTRAINT FK_id_warehouse_ticker FOREIGN KEY (id_warehouse) REFERENCES warehouse(id); 
ALTER TABLE ticket ADD CONSTRAINT FK_id_category_ticker FOREIGN KEY (id_category) REFERENCES category(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_serial_ticker FOREIGN KEY (id_serial) REFERENCES serial(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_order_ticker FOREIGN KEY (id_order) REFERENCES orders(id);
ALTER TABLE ticket ADD CONSTRAINT FK_id_user_ticker FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE ticket_history ADD CONSTRAINT FK_id_ticket_ticket_history FOREIGN KEY (id_ticket) REFERENCES ticket(id);

ALTER TABLE orders ADD CONSTRAINT FK_id_user_oder FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE users ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES roles(id);

ALTER TABLE roles_users ADD CONSTRAINT FK_id_role_role_user FOREIGN KEY (id_role) REFERENCES roles (id);
ALTER TABLE roles_users ADD CONSTRAINT FK_id_user_role_user FOREIGN KEY (id_user) REFERENCES users (id);