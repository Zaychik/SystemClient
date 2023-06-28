insert into public.Users (email, name, password, phone, role)
select 'user@gmail.com', 'User', '$2a$10$TKdSPmWuuiWcYhdouvUcc.GVuK/EQNMVax/MXMLgj7D53fiVICPfS', '89036874010', 'USER';


insert into public.Users (email, name, password, phone, role)
select 'admin@gmail.com', 'Admin', '$2a$10$TKdSPmWuuiWcYhdouvUcc.GVuK/EQNMVax/MXMLgj7D53fiVICPfS', '89036874010', 'ADMIN';
