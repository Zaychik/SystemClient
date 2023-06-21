create table tuser (
     id         serial        not null
    ,email      varchar(255)
    ,name       varchar(255)
    ,password   varchar(255)
    ,phone      varchar(255)
    ,role       varchar(255)             check (role in ('User','ADMIN'))
    ,primary key (id)
    );
