create table tUser
(
     id    int           not null primary key
    ,name  varchar(50)   not null
    ,email varchar(100)      null
    ,phone varchar(50)       null
);
insert into public.tUser (id, name, email, phone)
select 1, 'Alex', 'alex@gmail.com', '89036874010';
