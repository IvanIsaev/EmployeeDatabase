INSERT INTO TBL_POSTS (post) VALUES ('Инженер программист 3к');--1
INSERT INTO TBL_POSTS (post) VALUES ('Инженер программист 2к');--2
INSERT INTO TBL_POSTS (post) VALUES ('Инженер программист 1к');--3
INSERT INTO TBL_POSTS (post) VALUES ('Инженер программист');--4
INSERT INTO TBL_POSTS (post) VALUES ('Ведущий инженер программист');--5
INSERT INTO TBL_POSTS (post) VALUES ('Бухгалтер');--6
INSERT INTO TBL_POSTS (post) VALUES ('Инженер 3к');--7
INSERT INTO TBL_POSTS (post) VALUES ('Инженер 2к');--8
INSERT INTO TBL_POSTS (post) VALUES ('Инженер 1к');--9
INSERT INTO TBL_POSTS (post) VALUES ('Инженер');--10
INSERT INTO TBL_POSTS (post) VALUES ('Ведущий инженер');--11
INSERT INTO TBL_POSTS (post) VALUES ('Замсетистель генерального директора');--12
INSERT INTO TBL_POSTS (post) VALUES ('Генеральный директор');--13
------------------------------------------------------------------------------
INSERT INTO TBL_PHONE_NUMBERS (phone_number) VALUES ('1111');--1
INSERT INTO TBL_PHONE_NUMBERS (phone_number) VALUES ('2222');--2
INSERT INTO TBL_PHONE_NUMBERS (phone_number) VALUES ('3333');--3
INSERT INTO TBL_PHONE_NUMBERS (phone_number) VALUES ('4444');--4
------------------------------------------------------------------------------
INSERT INTO TBL_DEPARTMENTS (department) VALUES ('Центр разработки ПО');--1
INSERT INTO TBL_DEPARTMENTS (department) VALUES ('Бухгалтерия');--2
INSERT INTO TBL_DEPARTMENTS (department) VALUES ('Отдел инженеров');--3
INSERT INTO TBL_DEPARTMENTS (department) VALUES ('Директорат');--4
------------------------------------------------------------------------------
INSERT INTO TBL_ROOMS (room) VALUES ('101');--1
INSERT INTO TBL_ROOMS (room) VALUES ('102');--2
INSERT INTO TBL_ROOMS (room) VALUES ('103a');--3
INSERT INTO TBL_ROOMS (room) VALUES ('104a');--4
------------------------------------------------------------------------------
----------------------------Отдел разработки ПО-------------------------------
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Петр', 'Волков', 'Павлович', '1990-12-12', '1.jpg', 1, 1, 1);--1
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Иван', 'Орлов', 'Максимович', '1967-10-12', '2.jpg', 1, 1, 1);--2
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Василий', 'Симонов', 'Артёмович', '1966-06-25', '3.jpg', 1, 1, 1);--3
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, phone_number_id, room_id, department_id) VALUES ('Николай', 'Павловский', 'Андреевич', '1991-11-20', 1, 1, 1);--4
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Антон', 'Елисеев', 'Михайлович', '1972-03-10', '5.jpg', 1, 1, 1);--5
-------------------------------Бухгалтерия------------------------------------
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Екатерина', 'Никольская', 'Фёдоровна', '1991-07-14', '6.jpg', 2, 2, 2);--6
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Анастасия', 'Горшкова', 'Антоновна', '1985-02-11', '7.jpg', 2, 2, 2);--7
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Макар', 'Кудрявцев', 'Даниилович', '1973-04-01', '8.jpg', 2, 2, 2);--8
------------------------------Инженеры----------------------------------------
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, phone_number_id, room_id, department_id) VALUES ('Василиса', 'Колесникова', 'Максимовна', '1985-05-06', 3, 3, 3);--9
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Елисей', 'Кондратьев', 'Александрович', '1972-06-05', '10.jpg', 3, 3, 3);--10
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Юлия', 'Иванова', 'Максимовна', '1970-11-15', '11.jpg', 3, 3, 3);--11
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Алиса', 'Кондрашова', 'Арсентьевна', '1986-10-19', '12.jpg', 3, 3, 3);--12
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Александр', 'Алексеев', 'Владимирович', '1979-08-21', '13.jpg', 3, 3, 3);--13
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Владимир', 'Новиков', 'Русланович', '1989-08-26', '14.jpg', 3, 3, 3);--14
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Ульяна', 'Фокина', 'Александровна', '1987-09-18', '15.jpg', 3, 3, 3);--15
------------------------------Директорат--------------------------------------
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Александр', 'Иванов', 'Матвеевич', '1974-12-01', '16.jpg', 4, 4, 4);--16
INSERT INTO TBL_EMPLOYEES (name, last_name, patronymic, birthday, icon_name, phone_number_id, room_id, department_id) VALUES ('Артём', 'Олейников', 'Сергеевич', '1980-11-11', '17.jpg', 4, 4, 4);--17
------------------------------------------------------------------------------
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (1, 5);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (1, 11);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (2, 1);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (3, 2);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (4, 3);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (5, 4);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (6, 11);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (6, 12);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (7, 5);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (7, 12);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (8, 5);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (9, 6);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (10, 7);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (11, 8);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (12, 9);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (13, 10);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (14, 11);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (15, 12);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (16, 13);
INSERT INTO TBL_EMPLOYEE_POST (employee_id, post_id) VALUES (17, 1);
------------------------------------------------------------------------------