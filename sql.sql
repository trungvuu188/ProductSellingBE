create database entranceTest;
use entranceTest;

create table product (
	product_id int auto_increment,
    cate_id int,
    style_id int,
    product_name varchar (100),
    product_desc varchar (255),
    product_price int,
    primary key (product_id, cate_id, style_id)
);

alter table product add constraint fk_product_category foreign key (cate_id) references category (cate_id);
alter table product add constraint fk_product_style foreign key (style_id) references style (style_id);

create table category (
	cate_id int auto_increment primary key,
    cate_title varchar(255)
);

create table style (
	style_id int auto_increment primary key,
    style_title varchar(100)
);

create table color (
	color_id int auto_increment primary key,
    color_name varchar(100)
);

create table product_color (
	product_id int,
    color_id int,
    primary key (product_id, color_id)
);

alter table product_color add constraint fk_product_color1 foreign key (product_id) references product (product_id);
alter table product_color add constraint fk_product_color2 foreign key (color_id) references color (color_id);

create table size (
	size_id int auto_increment primary key,
    size_name varchar(20)
);

create table product_size (
	product_id int,
    size_id int,
    primary key (product_id, size_id)
);

alter table product_size add constraint fk_product_size1 foreign key (product_id) references product (product_id);
alter table product_size add constraint fk_product_size2 foreign key (size_id) references size (size_id);

create table product_image (
	product_image_id int auto_increment,
	product_id int,
    image_data longblob,
    is_main_display boolean,
    primary key (product_image_id, product_id)
);

alter table product_image add constraint fk_product_image foreign key (product_id) references product (product_id);

create table sale (
	sale_id int auto_increment primary key,
    start_date date,
    end_date date,
    amount_sale int
);
alter table sale add column sale_status varchar(100) default 'ACTIVE';

create table product_sale (
	sale_id int,
    product_id int,
    primary key(sale_id, product_id)
);

alter table product_sale add constraint fk_product_sale1 foreign key (product_id) references product (product_id);
alter table product_sale add constraint fk_product_sale2 foreign key (sale_id) references sale (sale_id);

create table customer (
	customer_id int auto_increment primary key,
    customer_name varchar(100)
);
ALTER TABLE customer ADD phone varchar(100);
ALTER TABLE customer ADD email varchar(100);
ALTER TABLE customer ADD address varchar(100);
ALTER TABLE customer ADD password varchar(100);

create table rating (
	customer_id int,
    product_id int,
    star int,
    primary key (customer_id, product_id)
);

alter table rating add constraint fk_rating_product foreign key (product_id) references product (product_id);
alter table rating add constraint fk_rating_customer foreign key (customer_id) references customer (customer_id);

create table wishlist (
	customer_id int,
    product_id int,
    primary key (customer_id, product_id)
);

alter table wishlist add constraint fk_wishlist_cus foreign key (product_id) references product (product_id);
alter table wishlist add constraint fk_wishlist_pro foreign key (customer_id) references customer (customer_id);

create table cart (
	customer_id int,
    product_id int,
    quantity int,
    primary key (customer_id, product_id)
);
alter table cart add column size_id int;
alter table cart add column color_id int;

alter table cart add constraint fk_cart_pro foreign key (product_id) references product (product_id);
alter table cart add constraint fk_cart_cus foreign key (customer_id) references customer (customer_id);
alter table cart add constraint fk_cart_size foreign key (size_id) references size (size_id);
alter table cart add constraint fk_cart_color foreign key (color_id) references color (color_id);


create table order_item (
	order_item_id int auto_increment,
    order_id int,
    product_id int,
	size_id int,
    color_id int,
    quantity int,
    price int,
    primary key (order_item_id, product_id, order_id)
);

alter table order_item add constraint fk_order_item_product foreign key (product_id) references product (product_id);
alter table order_item add constraint fk_order_item_order foreign key (order_id) references orders (order_id);
alter table order_item add constraint fk_order_item_size foreign key (size_id) references size (size_id);
alter table order_item add constraint fk_order_item_color foreign key (color_id) references color (color_id);

create table orders (
	order_id int auto_increment unique,
    payment_id int,
	customer_id int,
    total_price int,
    primary key (customer_id, order_id, payment_id)	
);

alter table orders add constraint fk_orders_cus foreign key (customer_id) references customer (customer_id);
alter table orders add constraint fk_orders_payment foreign key (payment_id) references payment (payment_id);

create table payment (
	payment_id int auto_increment unique,
	customer_id int,
    payment_date date,
    amount int,
    primary key (customer_id, payment_id)
);

alter table payment add constraint fk_payment_cus foreign key (customer_id) references customer (customer_id);

insert into category (cate_title) values ('Home & Decor'), ('Clothing'), ('Accessories'), ('Outdoor');
insert into color (color_name) values ('Green'), ('Purple'), ('Red'), ('Black');
insert into size (size_name) value ('S'), ('M'), ('L'), ('XL');
insert into style (style_title) values ('Modern'), ('Streetwear'), ('Colorfull'), ('Vintage');

insert into product (cate_id, style_id, product_name, product_desc, product_price) values (1, 2, 'Product Test 1', 'This is product description', 50), 
																							(2, 3, 'Product Test 2', 'This is product description', 80), 
                                                                                            (3, 4, 'Product Test 3', 'This is product description', 70);
-- Hãy kiểm tra product_id từ table product để set giá trị phù hợp.                                                                                             
insert into product_size (product_id, size_id) values (7, 1), (7, 2), (7, 3), (7, 4),
														(8, 1), (8, 2), (8, 3), (8, 4),			
                                                        (9, 1), (9, 2), (9, 3), (9, 4);
                                                        
 -- Hãy kiểm tra product_id từ table product để set giá trị phù hợp.                                                                                             
insert into product_color (product_id, color_id) values (7, 1), (7, 2), (7, 3), (7, 4),
														(8, 1), (8, 2), (8, 3), (8, 4),			
                                                        (9, 1), (9, 2), (9, 3), (9, 4);                                                                                           
																				

use entranceTest;
select * from product;
select * from product_image;
select * from product_color;
select * from wishlist;
select * from customer;
select * from order_item;
select * from orders;
select * from payment;
select * from product_color;
select * from cart;
select * from rating;
select * from size;
select * from sale;
select * from product_sale;

















