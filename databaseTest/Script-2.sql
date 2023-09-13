CREATE DATABASE CybersoftProject;
USE CybersoftProject;

CREATE TABLE Merchant(
	int id auto_increment,
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
	status int, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	
	primary key(id);
);

CREATE TABLE `Chain`(
	int id auto_increment,
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
	status int, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	id_merchant int,
	
	primary key(id)
);

CREATE TABLE Store(
	id int auto_increment,
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	store_code varchar(20) unique, 
	name varchar(100),
	address varchar(100),
	phone varchar(20),
	status int, -- giá trị 0:inactive và giá trị 1:active
	description varchar(512),
	created_by varchar(50),
	updated_by varchar(50),
	chain_code varchar(50),
	id_chain int,
	id_merchant int,
	
	primary key(id)
);

CREATE TABLE Warehouse(
	id int auto_increment,
	warehouse_code varchar(20) unique,
	id_warehouse_issuer int, 
	id_warehouse_acquirer int,
	id_store_applied int,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	name varchar(100),
	description varchar(512),
	term_of_use varchar(255),
	banner_url varchar(255),
	thumbnail_url varchar(255),
	discount_type varchar(20),
	discount_amount decimal(8,3),
	max_discount_amount decimal(8,3),
	available_from timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	available_to timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	status int, -- giá trị 0:inactive và giá trị 1:active
	show_on_web int, -- giá trị 0:không hiển thị cho end user và giá trị 1:được hiển thị cho end user
	capacity int,
	vouncher_channel int, -- giá trị 0:áp dụng hình thức offline và giá trị 1:áp dụng hình thức online
	id_category int,
	
	primary key(id)
);

CREATE TABLE Warehouse_issuer(
	id int auto_increment,
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	id_warehouse int,
	id_merchant int,
	id_chain,	
	
	primary key(id)
);

CREATE TABLE Discount_type(
	id int auto_increment,
	code varchar(20) unique,
	name varchar(100),
	status int, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE Category(
	id int auto_increment,
	category_code varchar(20) unique,
	name varchar(100),
	status int, -- giá trị 0:inactive và giá trị 1:active
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	
	primary key(id)
);

CREATE TABLE Warehouse_acquirer(
	id int auto_increment,
	created_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	id_warehouse,
	id_merchant int,
	chain_code varchar(20) unique,
	
	primary key(id)
);

CREATE TABLE Warehouse_stores(
	id int auto_increment,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	id_warehouse int,
	id_store int,
		
	primary key(id)
);

CREATE TABLE Warehouse_serial(
	id int auto_increment,
	id_warehouse int,
	batch_code varchar(20) unique,
	id_serial int,
		
	primary key(id)
);

CREATE TABLE Serial(
	id int auto_increment,
	batch_code varchar(20) unique,
	number_of_serial int,
	serial_code varchar(20) unique,
	status int, -- giá trị 0:ngừng áp dụng và giá trị 1:còn áp dụng
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key(id)
);

CREATE TABLE Ticket(
	id int auto_increment,
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	serial_code varchar(20) unique,
	id_warehouse int,
	category_code varchar(20) unique,
	status int, -- giá trị 0:chưa phát hành, giá trị 1:đã phát hành, giá trị 2: đã sử dụng, giá trị 3: đã hết hạn
	order_no varchar(20) unique, 
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
	id_user int,
	
	primary key(id)
);

CREATE TABLE Ticket_history(
	id int auto_increment,
	ticket_id int not null,
	serial_code varchar(20) unique, 
	prev_status int not null, -- giá trị 0,1
	is_latest int not null, -- giá trị 0,1
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key(id)
);

CREATE TABLE `Order`(
	id int auto_increment,
	created_by varchar(50),
	order_no varchar(20) unique, 
	status int, -- giá trị 0:ngừng áp dụng và giá trị 1:còn áp dụng
	id_user int,
	quantity int,
	id_warehouse int,
	
	primary key(id)
);

CREATE TABLE `User`(
	id int auto_increment,
	member_code varchar(20) unique,
	first_name varchar(20),
	last_name varchar(20),
	full_name varchar(20),
	username varchar(50),
	password varchar(100),
	phone varchar(20),
	row ??
	email varchar(50),
	status int,
	address varchar(100),
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	id_role,	
	
	primary key (id)
);

CREATE TABLE `Role`(
	id int auto_increment,
	name varchar(100),
	code ??
	created_by varchar(50),
	updated_by varchar(50),
	created_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss
	updated_at timestamp, -- nhập định dạng yyyy-mm-dd hh:mm:ss	
	
	primary key (id)
);



ALTER TABLE `Chain` ADD CONSTRAINT FK_id_merchant_chain FOREIGN KEY (id_merchant) REFERENCES Merchant(id);

ALTER TABLE Store ADD CONSTRAINT FK_chain_code_store FOREIGN KEY (chain_code) REFERENCES `Chain`(id);
ALTER TABLE Store ADD CONSTRAINT FK_merchant_code_store FOREIGN KEY (merchant_code) REFERENCES Merchant(id);

ALTER TABLE Warehouse_issuer ADD CONSTRAINT FK_id_merchant_warehouse_issuer FOREIGN KEY (id_merchant) REFERENCES Merchant(id);
ALTER TABLE Warehouse_issuer ADD CONSTRAINT FK_id_warehouse_warehouse_issuer FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id);
ALTER TABLE Warehouse_issuer ADD CONSTRAINT FK_id_chain_warehouse_issuer FOREIGN KEY (id_chain) REFERENCES `Chain`(id);

ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_warehouse_warehouse_acquirer FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id);
ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_merchant_warehouse_acquirer FOREIGN KEY (id_merchant) REFERENCES Merchant(id);
ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_chain_warehouse_acquirer FOREIGN KEY (id_chain) REFERENCES `Chain`(id);

ALTER TABLE Warehouse ADD CONSTRAINT FK_discount_type_warehouse FOREIGN KEY (discount_type) REFERENCES Discount_type(id);
ALTER TABLE Warehouse ADD CONSTRAINT FK_id_warehouse_issuer_warehouse FOREIGN KEY (id_warehouse_issuer) REFERENCES Warehouse_issuer(id);
ALTER TABLE Warehouse ADD CONSTRAINT FK_id_warehouse_acquirer_warehouse FOREIGN KEY (id_warehouse_acquirer) REFERENCES Warehouse_acquirer(id);
ALTER TABLE Warehouse ADD CONSTRAINT FK_id_category_warehouse FOREIGN KEY (id_category) REFERENCES Category(id);

ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_warehouse_warehouse_acquirer FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id_warehouse);
ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_merchant_warehouse_acquirer FOREIGN KEY (id_merchant) REFERENCES Merchant(id);
ALTER TABLE Warehouse_acquirer ADD CONSTRAINT FK_id_chain_warehouse_acquirer FOREIGN KEY (id_chain) REFERENCES `Chain`(id);

ALTER TABLE Warehouse_stores ADD CONSTRAINT FK_id_warehouse_warehouse_stores FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id);
ALTER TABLE Warehouse_stores ADD CONSTRAINT FK_id_store_warehouse_stores FOREIGN KEY (id_store) REFERENCES Store(id);

ALTER TABLE Warehouse_serial ADD CONSTRAINT FK_id_warehouse_warehouse_serial FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id);
ALTER TABLE Warehouse_serial ADD CONSTRAINT FK_batch_code_warehouse_serial FOREIGN KEY(batch_code) REFERENCES Serial(batch_code);

ALTER TABLE Ticket ADD CONSTRAINT FK_id_warehouse_ticker FOREIGN KEY (id_warehouse) REFERENCES Warehouse(id);
ALTER TABLE Ticket ADD CONSTRAINT FK_id_category_ticker FOREIGN KEY (id_category) REFERENCES Category(id);
ALTER TABLE Ticket ADD CONSTRAINT FK_order_no_ticker FOREIGN KEY (order_no) REFERENCES `Order`(id);
ALTER TABLE Ticket ADD CONSTRAINT FK_id_user_ticker FOREIGN KEY (id_user) REFERENCES `User`(id);

ALTER TABLE Ticket_history ADD CONSTRAINT FK_id_ticket_ticket_history FOREIGN KEY (id_ticket) REFERENCES Ticket(id);

ALTER TABLE `Order` ADD CONSTRAINT FK_id_user_oder FOREIGN KEY (id_user) REFERENCES `User`(id);

ALTER TABLE `User` ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES `Role`(id);





