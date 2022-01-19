-- Authentication and view user profile

select *
from Users;


-- creating an database administrative user for creating the database 

create user OBS identified by 123456 default tablespace OBS;
grant dba to OBS;



create or replace function table_checker(table_name varchar2)
    return number
    is
    checker varchar2(20);
    rtn     number := 0;
begin
    select max(object_name)
    into checker
    from user_objects
    where table_name = object_name;
    if checker is null then
        rtn := 1;
    end if;
    return rtn;
end;
/

declare
    checker int;
begin
    checker := table_checker('cxvxc');

end;
/


Create table Users
(
    U_id           number primary key,
    name unique varchar2(20),
    email          varchar2(20),
    password       varchar2(20),
    phone_No       varchar2(11),
    Address        varchar2(20),
    Dob            date,
    U_type         number,
    recommendation varchar2(40)
);


-- creating a role for all customers
create role customer_role;
grant select on Users to customer_role;
grant insert on Users to customer_role;
grant select on Book to customer_role;


-- Auto id generator

create or replace function get_Id(Dob date)
    return number
    is
    user_id number;

BEGIN
    select max(U_id)
    into user_id
    from Users
    where Dob = Users.Dob;
    IF user_id is null then
        user_id := TO_NUMBER(TO_CHAR(Dob, 'YYYYMMDD') || '001');
    ELSE
        user_id := user_id + 1;
    END IF;
    return user_id;
END;
/

Create or Replace trigger Generate_user_id
    before insert
    on Users
    for each row

begin

    :NEW.U_id := get_Id(:NEW.Dob);

end;
/


-- test auto id generator works properly or not 
insert into Users(name, email, password, phone_No, Address, Dob, U_type, recommendation)
VALUES ('Tahmid', 'tahmid@gmail.com', '123456', '018757623', 'farmgate', TO_DATE('05-11-1999', 'mm-dd-yyyy'),
        1, 'Science fiction books');


-- procedure for inserting value to user table

create or replace procedure Add_value_User_table(name Users.name%TYPE, email Users.email%TYPE,
                                                 password Users.password%TYPE, phone_No Users.phone_No%TYPE,
                                                 Address Users.Address%TYPE, Dob Users.Dob%TYPE,
                                                 U_type Users.U_type%TYPE, recommendation Users.recommendation%TYPE)
    is

begin
    insert into Users(name, email, password, phone_No, Address, Dob, U_type, recommendation)
    values (name, email, password, phone_No, Address, dob, U_type, recommendation);


end;
/

begin
    Add_value_User_table('xyz', 'wer@gmail.com', '12345', '018757623', 'farmgate', TO_DATE('12-01-1999', 'mm-dd-yyyy'),
                         1, 'Science fiction books');
end;
/


-- create user and grant him access base on his role

create or replace procedure retrieve_id(email1 in varchar2, password1 in varchar2, userId out number)
    is

begin
    select U_id
    into userId
    from Users
    where email1 = Users.email
      and password1 = Users.password;
end;
/
declare
    gval number;
begin
    retrieve_id('rafi', '123456', gval);
    dbms_output.put_line(gval);
end;
/

create or replace procedure user_creation(user_id Users.U_id%TYPE)
    is
    name     Users.name%TYPE;
    password Users.password%TYPE;

begin
    select name, password
    into name,password
    from Users
    where user_id = Users.U_id;
    EXECUTE IMMEDIATE 'CREATE USER ' || name || ' IDENTIFIED BY ' || password;
    EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO ' || name;
    EXECUTE IMMEDIATE 'GRANT customer_role TO ' || name;
end;
/


-- user can view his profile

create or replace procedure User_profile(user_id in Users.U_id%type, name out Users.name%TYPE,
                                         email out Users.email%TYPE, password out Users.password%TYPE,
                                         phone_No out Users.phone_No%TYPE, Address out Users.Address%TYPE,
                                         Dob out Users.Dob%TYPE, recommendation out Users.recommendation%TYPE)
    is


begin

    select name, email, password, phone_No, Address, Dob, recommendation
    into name,email,password,phone_No,Address,Dob,recommendation
    from Users
    where U_id = user_id;

end;
/

-- Search book 
Books<B_id,C_id,quantity,price,description,status,rating>

Create table Book_catagory
(
    C_id          number primary key,
    Catagory_name varchar2(20) unique
);
insert into Book_catagory
values (201, 'History');
insert into Book_catagory
values (202, 'Sceince Fiction');
insert into Book_catagory
values (203, 'Story');
insert into Book_catagory
values (204, 'Poetry');
Create table Book
(
    B_id        number primary key,
    C_id        number,
    name        varchar2(40) unique,
    quantity    number,
    price       number,
    description varchar2(41),
    status      varchar2(20),
    rating      number,
    constraint Book_fk foreign key (C_id) references Book_catagory
);
insert into Book
values (101, 201, 'The Guns of August', 3, 300, 'A history of world war i', 'available', 0);
insert into Book
values (102, 201, 'The Crusades', 6, 400, 'History of the War for the Holy Land', 'not available', 0);
insert into Book
values (103, 202, 'Amra O Crab Nebula', 10, 300, 'A science fiction book', 'available', 0);
insert into Book
values (104, 202, 'Serina', 12, 600, 'A science fiction book', 'available', 0);
insert into Book
values (105, 203, 'Stories of the Prophets', 20, 250, 'the life of Mohammad sm', 'available', 0);
insert into Book
values (106, 203, 'Gullivers Travels', 16, 600, 'A fantasy based story', 'not available', 0);
insert into Book
values (107, 204, 'Shreshtho Kobita', 12, 450, 'Poetry book', 'available', 0);
insert into Book
values (108, 204, 'Sanchita', 15, 320, 'poetry book', 'available', 0);

create type book_obj is object
(
    name        varchar2(40),
    quantity    number,
    price       number,
    description varchar2(41),
    status      varchar2(20),
    rating      number
);
create type book_table is table of book_obj;

create or replace function search_book(Catagory_name in Book.name%type, name out Book.name%type,
                                       quantity out Book.quantity%type, price out Book.price%type,
                                       description out Book.description%type, status out Book.status%type,
                                       rating out Book.rating%type)
    return number
    is
    v_num number;

BEGIN
    SELECT name, quantity, price, description, status, rating
    into name,quantity,price,description,status,rating
    from Book
    where Book.name = Catagory_name;

    return v_num;

END;
/

create or replace function category_search_book(B_catagory in Book_catagory.Catagory_name%type)
    return SYS_REFCURSOR
    Is
    c SYS_REFCURSOR;
begin
    open c for
        select Book.name, quantity, price, description, status, rating, B_id
        from Book,
             Book_catagory
        where Book_catagory.Catagory_name = B_catagory
          and Book.C_id = Book_catagory.C_id;
    return c;
end;
/

-- wish list
create table Wish_List
(
    W_id number primary key,
    U_id number,
    B_id number,
    Dt   date,
    constraint Wishlist_fk foreign key (U_id) references Users,
    constraint Wishlist_fk2 foreign key (B_id) references Book
);
create or replace procedure add_to_Wishlist(Book_id in Book.B_id%type, user_id in Users.U_id%type)
    is
    cnt number := 0;
begin

    select count(*) into cnt from Wish_List;
    insert into Wish_List values (500 + cnt + 1, user_id, Book_id, sysdate);
end;
/

--Order book

Order<Order_id,B_id,U_id,amount,price,date,due Amount,paid amount>
create table Orders
(
    O_id        number primary key,
    B_id        number,
    U_id        number,
    amount      number,
    price       number,
    Dt          date,
    due_Amount  number,
    paid_amount number,
    constraint Order_fk1 foreign key (B_id) references Book,
    constraint Order_fk2 foreign key (U_id) references Users
);
create or replace procedure add_To_Orders(Book_id in Book.B_id%type, U_id in Users.U_id%type, amount Orders.amount%type)
    is
    cost number := 0;
    cnt  number := 0;
begin
    select price
    into cost
    from Book
    where Book_id = Book.B_id;
    select count(*) into cnt from Orders;
    insert into Orders values (700 + cnt + 1, Book_id, U_id, amount, cost, sysdate, cost, 0);
end;
/

create or replace function generate_report
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin
    open c for
        select Dt, due_Amount, paid_amount, Users.name
        from Orders,
             Users
        where Orders.U_id = Users.U_id;
end;
/
create or replace function order_list_due
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin
    open c for
        select Orders.O_id,
               Orders.B_id,
               Orders.U_id,
               Orders.amount,
               Orders.price,
               Orders.Dt,
               Orders.due_Amount,
               Orders.paid_amount,
               Book.name,
               Users.name
        from Orders,
             Book,
             Users
        where Orders.B_id = Book.B_id
          and Orders.U_id = Users.U_id
          and due_Amount != 0;
    return c;
end;
/


create table Rating
(
    U_id    number,
    B_id    number,
    pending number,
    constraint Rating_fk1 foreign key (U_id) references Users,
    constraint Rating_fk2 foreign key (B_id) references Book,
    constraint Rating_pk primary key (U_id, B_id)
);
create or replace function pending_rating(customer_id in number)
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin
    open c for
        select Book.B_id, Book.name, Book.quantity, Book.price, Book.description, Book.status, Book.rating
        from Rating,
             Book
        where Rating.U_id = customer_id
          and Rating.B_id = Book.B_id
          and pending = 1;
    return c;
end;
/

create or replace procedure give_rate(Book_id in Book.B_id%type, user_id in Users.U_id%type,
                                      new_rating in Book.rating%type)
    is
    prev_Rating number := null;
begin
    update Rating
    set pending=0
    where Rating.B_id = Book_id
      and Rating.U_id = user_id;

    select rating
    into prev_Rating
    from Book
    where Book.B_id = Book_id;

    IF prev_Rating is null THEN
        update Book
        set rating=new_rating
        where Book.B_id = Book_id;
        dbms_output.put_line(new_rating);

    ELSE
        update Book
        set rating=(prev_Rating + new_rating) / 2
        where Book.B_id = Book_id;
        dbms_output.put_line(new_rating + 3);
    END IF;
end;
/

create or replace function Wishlist_exists(Book_id in Book.B_id%type, user_id in Users.U_id%type)
    return number
    is
    cnt number := 0;

begin
    select count(*)
    into cnt
    from Wish_List
    where Wish_List.B_id = Book_id
      and Wish_List.U_id = user_id;

    return cnt;
end;
/

create or replace function get_All_BookList
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin
    open c for
        select Book.name, quantity, price, description, status, rating, B_id
        from Book;
    return c;
end;
/
insert into Users(name, email, password, phone_No, Address, Dob, U_type, recommendation)
VALUES ('abc', 'admin@gmail.com', '12345', '018757623', 'farmgate', TO_DATE('11', 'mm-dd-yyyy'),
        0, 'Science fiction books');

create or replace function check_Admin(Name1 Users.name%type, Password1 Users.password%type)
    return number
    is
    ck  number := 0;
    cnt number := 0;
begin
    select count(*)
    into cnt
    from Users
    where Name1 = Users.name
      and Password1 = Users.password
      and Users.U_type = 0;
    IF cnt >= 1 THEN
        return 1;
    ELSE
        return 0;
    END IF;
end;
/

create or replace function order_list
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin

    open c for
        select Orders.O_id,
               Orders.B_id,
               Orders.U_id,
               Orders.amount,
               Orders.price,
               Orders.Dt,
               Orders.due_Amount,
               Orders.paid_amount,
               Book.name,
               Users.name
        from Orders,
             Book,
             Users
        where Orders.B_id = Book.B_id
          and Orders.U_id = Users.U_id;
    return c;
end;
/

create or replace procedure add_new_books(category_name in Book_catagory.Catagory_name%type, b_name in Book.name%TYPE,
                                          quantity in Book.quantity%type, price in Book.price%type,
                                          description in Book.description%type, status in Book.status%type,
                                          rating in Book.rating%type)
    is
    category_id Book.C_id%type;
    cnt         number := 0;
begin
    select C_id
    into category_id
    from Book_catagory
    where Catagory_name = category_name;
    select count(*)
    into cnt
    from Book;

    insert into Book
    values (100 + cnt + 1, category_id, b_name, quantity, price, description, status, 0);
end;
/
create or replace procedure add_new_catagory(category_name in Book_catagory.Catagory_name%type)
    is

    cnt number := 0;
begin

    select count(*)
    into cnt
    from Book_catagory;

    insert into Book_catagory
    values (200 + cnt + 1, category_name);
end;
/

create or replace function due_order_checker(Order_id in Orders.O_id%type)
    return number
    is
    Due1 number;
begin
    select max(due_Amount)
    into Due1
    from Orders
    where Orders.O_id = Order_id;
    IF Due1 > 0 THEN
        Due1 := 1;
    ELSE
        Due1 := 0;
    END IF;
    return Due1;
end;
/

create or replace procedure update_Order(Oder_id in Orders.O_id%type)
    is
    user_id number;
    Book_id number;
    cnt     number;
begin
    update Orders
    set paid_amount=due_Amount,
        due_Amount=0
    where Orders.O_id = Oder_id;
    select U_id, B_id
    into user_id,Book_id
    from Orders
    where Orders.O_id = Oder_id;
    select count(*)
    into cnt
    from Rating
    where Rating.U_id = user_id
      and Rating.B_id = Book_id;
    IF cnt = 0 THEN
        insert into Rating values (user_id, Book_id, 1);
    END IF;
end;
/
begin
    update_Order(704);
end;
/
DELETE
FROM Orders;

select Book.B_id, Book.name, quantity, price, description, status, rating
from Rating,
     Book
where Rating.U_id = 19990101001
  and Rating.B_id = Book.B_id
  and pending = 1;


declare
    Book_id     number := 106;
    user_id     number := 19990101001;
    prev_Rating number;
    new_rating  number := 4;

begin
    update Rating
    set pending=0
    where Rating.B_id = Book_id
      and Rating.U_id = user_id;

    select rating
    into prev_Rating
    from Book
    where Book.B_id = Book_id;

    IF prev_Rating is null THEN
        update Book
        set rating=new_rating
        where Book.B_id = Book_id;
        dbms_output.put_line(new_rating);

    ELSE
        update Book
        set rating=(prev_Rating + new_rating) / 2
        where Book.B_id = Book_id;
        dbms_output.put_line(new_rating + 3);
    END IF;
end;
/
set serveroutput on;
declare
    prev_Rating number;
begin
    select Book.rating
    into prev_Rating
    from Book
    where Book.B_id = 106;
    dbms_output.put_line(prev_Rating);
end;
create or replace function show_wishlist(user_id in Users.U_id%type)
    return SYS_REFCURSOR
    is
    c SYS_REFCURSOR;
begin
    open c for
        select Book.B_id, Book.name, Book.quantity, Book.price, Book.description, Book.status, Book.rating
        from Wish_List,
             Book
        where Wish_List.B_id = Book.B_id
          and Wish_List.U_id = user_id;
    return c;
end;
/

create or replace procedure retrieve_id(email1 in varchar2, password1 in varchar2, userId out number)
    is

begin
    select U_id
    into userId
    from Users
    where email1 = Users.email
      and password1 = Users.password;
end;
/
declare
    gval number;
begin
    retrieve_id('rafi', '123456', gval);
    dbms_output.put_line(gval);
end;
/