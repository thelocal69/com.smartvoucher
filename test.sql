create table category
(
    id            bigint auto_increment
        primary key,
    category_code varchar(20)  null,
    name          varchar(100) null,
    status        int          not null,
    created_by    varchar(50)  null,
    updated_by    varchar(50)  null,
    created_at    timestamp    null,
    updated_at    timestamp    null,
    constraint category_code
        unique (category_code)
);

create table discount_type
(
    id         bigint auto_increment
        primary key,
    code       varchar(20)  null,
    name       varchar(100) null,
    status     int          not null,
    created_by varchar(50)  null,
    updated_by varchar(50)  null,
    created_at timestamp    null,
    updated_at timestamp    null,
    constraint code
        unique (code)
);

create table merchant
(
    id            bigint auto_increment
        primary key,
    merchant_code varchar(20)  null,
    name          varchar(100) null,
    legal_name    varchar(100) null,
    logo_url      varchar(100) null,
    address       varchar(100) null,
    phone         varchar(20)  null,
    email         varchar(50)  null,
    description   varchar(512) null,
    status        int          not null,
    created_by    varchar(50)  null,
    updated_by    varchar(50)  null,
    created_at    timestamp    null,
    updated_at    timestamp    null,
    constraint merchant_code
        unique (merchant_code)
);

create table chains
(
    id          bigint auto_increment
        primary key,
    chain_code  varchar(20)  null,
    name        varchar(100) null,
    legal_name  varchar(100) null,
    logo_url    varchar(100) null,
    address     varchar(100) null,
    phone       varchar(20)  null,
    email       varchar(50)  null,
    description varchar(512) null,
    status      int          not null,
    id_merchant bigint       not null,
    created_by  varchar(50)  null,
    updated_by  varchar(50)  null,
    created_at  timestamp    null,
    updated_at  timestamp    null,
    constraint chain_code
        unique (chain_code),
    constraint FK_id_merchant_chain
        foreign key (id_merchant) references merchant (id)
);

create table roles
(
    id         bigint auto_increment
        primary key,
    name       varchar(100) null,
    role_code  varchar(100) null,
    created_by varchar(50)  null,
    updated_by varchar(50)  null,
    created_at timestamp    null,
    updated_at timestamp    null,
    constraint role_code
        unique (role_code)
);

create table serial
(
    id               bigint auto_increment
        primary key,
    batch_code       varchar(20) null,
    number_of_serial int         null,
    serial_code      varchar(20) null,
    status           int         not null,
    created_by       varchar(50) null,
    updated_by       varchar(50) null,
    created_at       timestamp   null,
    updated_at       timestamp   null,
    constraint batch_code
        unique (batch_code),
    constraint serial_code
        unique (serial_code)
);

create table store
(
    id          bigint auto_increment
        primary key,
    store_code  varchar(20)  null,
    name        varchar(100) null,
    address     varchar(100) null,
    phone       varchar(20)  null,
    status      int          not null,
    description varchar(512) null,
    id_chain    bigint       not null,
    id_merchant bigint       not null,
    created_by  varchar(50)  null,
    updated_by  varchar(50)  null,
    created_at  timestamp    null,
    updated_at  timestamp    null,
    constraint store_code
        unique (store_code),
    constraint FK_id_chain_store
        foreign key (id_chain) references chains (id),
    constraint FK_id_merchant_store
        foreign key (id_merchant) references merchant (id)
);

create table warehouse
(
    id                  bigint auto_increment
        primary key,
    warehouse_code      varchar(20)   null,
    name                varchar(100)  null,
    description         varchar(512)  null,
    term_of_use         varchar(255)  null,
    banner_url          varchar(255)  null,
    thumbnail_url       varchar(255)  null,
    id_discount_type    bigint        not null,
    discount_amount     decimal(8, 3) null,
    max_discount_amount decimal(8, 3) null,
    available_from      timestamp     null,
    available_to        timestamp     null,
    status              int           not null,
    show_on_web         int           not null,
    capacity            int           null,
    vouncher_channel    int           not null,
    id_category         bigint        not null,
    created_by          varchar(50)   null,
    updated_by          varchar(50)   null,
    created_at          timestamp     null,
    updated_at          timestamp     null,
    constraint warehouse_code
        unique (warehouse_code),
    constraint FK_discount_type_warehouse
        foreign key (id_discount_type) references discount_type (id),
    constraint FK_id_category_warehouse
        foreign key (id_category) references category (id)
);

create table warehouse_merchant
(
    id_warehouse bigint      not null,
    id_merchant  bigint      not null,
    id_role      bigint      not null,
    created_by   varchar(50) null,
    updated_by   varchar(50) null,
    created_at   timestamp   null,
    updated_at   timestamp   null,
    primary key (id_warehouse, id_merchant),
    constraint FK_id_merchant_warehouse_merchant
        foreign key (id_merchant) references merchant (id),
    constraint FK_id_warehouse_warehouse_merchant
        foreign key (id_warehouse) references warehouse (id)
);

create table warehouse_serial
(
    id_warehouse bigint      not null,
    id_serial    bigint      not null,
    created_by   varchar(50) null,
    updated_by   varchar(50) null,
    created_at   timestamp   null,
    updated_at   timestamp   null,
    primary key (id_warehouse, id_serial),
    constraint id_serial
        unique (id_serial),
    constraint FK_id_serial_warehouse_serial
        foreign key (id_serial) references serial (id),
    constraint FK_id_warehouse_warehouse_serial
        foreign key (id_warehouse) references warehouse (id)
);

create table warehouse_stores
(
    id_warehouse bigint      not null,
    id_store     bigint      not null,
    created_by   varchar(50) null,
    updated_by   varchar(50) null,
    created_at   timestamp   null,
    updated_at   timestamp   null,
    primary key (id_warehouse, id_store),
    constraint FK_id_store_warehouse_stores
        foreign key (id_store) references store (id),
    constraint FK_id_warehouse_warehouse_stores
        foreign key (id_warehouse) references warehouse (id)
);


