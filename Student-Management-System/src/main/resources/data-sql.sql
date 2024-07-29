CREATE DATABASE sms
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;



CREATE SEQUENCE id_students_seq;
CREATE SEQUENCE id_teachers_seq;
CREATE SEQUENCE id_courses_seq;
CREATE SEQUENCE id_student_courses_seq;

CREATE TABLE students
(
  id bigint NOT NULL DEFAULT nextval('id_students_seq'::regclass),
  first_name character varying(255),
  last_name character varying(255),
  age integer,
  academic_year character varying(255),
  academic_division character varying(255),
  CONSTRAINT pk_students PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


CREATE TABLE teachers
(
  id bigint NOT NULL DEFAULT nextval('id_teachers_seq'::regclass),
  first_name character varying(255),
  last_name character varying(255),
  age integer,  
  CONSTRAINT pk_teachers PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);



CREATE TABLE courses
(
  id bigint NOT NULL DEFAULT nextval('id_courses_seq'::regclass),
  code character varying(255),
  name character varying(255),
  type character varying(25),
  teacher_id bigint,
  academic_year character varying(255),
  CONSTRAINT pk_course PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE student_courses
(
  id bigint NOT NULL DEFAULT nextval('id_student_courses_seq'::regclass),
  student_id bigint NOT NULL,
  course_id bigint NOT NULL,  
  CONSTRAINT pk_student_courses PRIMARY KEY (id),
  CONSTRAINT fk_student_course_on_course_id FOREIGN KEY (course_id)
      REFERENCES courses (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT,
  CONSTRAINT fk_student_course_on_student_id FOREIGN KEY (student_id)
      REFERENCES students (id) MATCH SIMPLE
      ON UPDATE RESTRICT ON DELETE RESTRICT
)
WITH (
  OIDS=FALSE
);




INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (1, 'Ivan', 'Petrov', 24, 1, 'C');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (2, 'Georgi','Ivanov', 24, 1, 'A');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (3, 'Vasil','Iliev', 23, 3, 'A');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (4, 'Pavel','Stoyanov', 24, 2, 'B');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (5, 'Galin','Ivanov', 22, 2, 'A');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (6, 'Petar','Petrov', 47, 3, 'B');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (7, 'Yordan','Penchev', 28, 1, 'B');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (8, 'Pencho','Penchev', 26, 4, 'A');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (9, 'Ivan','Ivanov', 28, 4, 'B');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (10, 'Mladen','Mladenov', 29, 5, 'A');
INSERT INTO students(id, first_name, last_name, age, academic_year, academic_division) VALUES (11, 'Simeon','Tolev', 21, 5, 'B');



INSERT INTO teachers(id, first_name, last_name, age)  VALUES (1, 'Ivan', 'Ivanov', 35);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (2,'Pavel','Stoyanov',50);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (3,'Asen','Asenov',44);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (4,'Timothy','Jones',48);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (5,'Monika','Halmshow',39);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (6,'Holly','Buckless',43);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (7,'Lydia','Hallam',46);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (8,'Sarah','Perl',33);
INSERT INTO teachers(id, first_name, last_name, age)  VALUES (9,'Isak','Albam',55);



INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (1, 'IT100', 'Java Programming', 'Main',5, 1);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (2, 'M103', 'Mathematics', 'Main', 2, 3);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (3, 'DG300', 'Differential Geometry', 'Main', 4, 4);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (4, 'M102', 'Mathematics 1', 'Main',  1, 1);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (5, 'M101', 'Mathematics 2', 'Main', 3, 2);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (6, 'IT101', 'Java Programming for Adnavced', 'Main', 9, 5);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (7, 'IT102', 'Databases', 'Secondary',8, 2);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (8, 'IT103', 'Microservices', 'Main',6, 5);
INSERT INTO courses(id, code, name, type, teacher_id, academic_year) VALUES (9, 'IT104', 'Spring Cloud', 'Secondary',7, 5);


INSERT INTO student_courses(id, student_id, course_id) VALUES (1, 2, 1);
INSERT INTO student_courses(id, student_id, course_id) VALUES (2, 1, 1);
INSERT INTO student_courses(id, student_id, course_id) VALUES (3, 1, 4);
INSERT INTO student_courses(id, student_id, course_id) VALUES (4, 7, 1);
INSERT INTO student_courses(id, student_id, course_id) VALUES (5, 4, 7);
INSERT INTO student_courses(id, student_id, course_id) VALUES (6, 3, 2);
INSERT INTO student_courses(id, student_id, course_id) VALUES (7, 7, 4);

INSERT INTO student_courses(id, student_id, course_id) VALUES (8, 5, 5);
INSERT INTO student_courses(id, student_id, course_id) VALUES (9, 5, 7);
INSERT INTO student_courses(id, student_id, course_id) VALUES (10, 6, 2);
INSERT INTO student_courses(id, student_id, course_id) VALUES (11, 10, 6);
INSERT INTO student_courses(id, student_id, course_id) VALUES (12, 8, 3);
INSERT INTO student_courses(id, student_id, course_id) VALUES (13, 11, 8);


INSERT INTO student_courses(id, student_id, course_id) VALUES (14, 9, 3);
INSERT INTO student_courses(id, student_id, course_id) VALUES (15, 10, 9);
INSERT INTO student_courses(id, student_id, course_id) VALUES (16, 11, 9);