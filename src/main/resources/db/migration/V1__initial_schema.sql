create table users (
     id         serial        not null
    ,email      varchar(255)  not null
    ,name       varchar(255)
    ,password   varchar(255)  not null
    ,phone      varchar(255)
    ,role       varchar(255)  not null  check (role in ('USER','ADMIN'))
    ,primary key (id)
    ,constraint users_contraint_email unique (email)
    );
