DELETE FROM exam;
DELETE FROM phone;
DELETE FROM student;
DELETE FROM subject;


INSERT INTO subject (subject_name) 
select
	md5(random()::text)
FROM
  generate_series(1,1000) i;

INSERT INTO student (firstname, lastname) 
select
	md5(random()::text), md5(random()::text)
FROM
  generate_series(1,100000) i;
 
INSERT INTO phone (phonenumber) 
SELECT
  floor(random() * (21302920 - 121 + 1) + 121)::int
FROM
  generate_series(1,10000) i;
 
SELECT floor(random() * (high - low + 1) + low)::int;

EXPLAIN analyze
select s.firstname, p.phonenumber from student s 
join phone p on s.student_id = p.student_id 
where p.phonenumber like '12%';

select count(*) from phone;
select count(*) from student;
select count(*) from subject;
select count(*) from exam;

INSERT INTO exam (student_id) values
select floor(random() * (100000 - 1 + 1) + 1)::int
FROM
  generate_series(1,1000000) i;

UPDATE phone SET student_id=floor(random() * (600004 - 500003 + 1) + 500003)::int;

INSERT INTO exam (student_id, subject_id, mark) 
select floor(random() * (600004 - 500003 + 1) + 500003)::int,
       floor(random() * (3000 - 2001 + 1) + 2001)::int,
       floor(random() * (5 - 2 + 1) + 2)::int
FROM
  generate_series(1,1000000) i;


select min(student_id), max(student_id) from student;
select min(subject_id), max(subject_id) from subject;

INSERT INTO exam (student_id, subject_id, mark)
select
  floor(random() * (100000 - 1 + 1) + 1)::int,
  floor(random() * (1000 - 1 + 1) + 1)::int,
  floor(random() * (5 - 2 + 1) + 2)::int
FROM
  generate_series(1,1000000) i;
 
-- Find user by name (exact match)
select firstname from student s where s.student_id = 38328;
explain analyze
select * from student s where s.firstname = '82c2c03b9283187590ae316fb4fc354a';

drop index firstname_index;

CREATE INDEX firstname_index ON student (firstname);
CREATE INDEX firstname_index ON student USING hash (firstname);
CREATE INDEX firstname_index ON student USING gin (to_tsvector('english', firstname));
CREATE INDEX firstname_index ON student USING gist (to_tsvector('english', firstname));

SELECT pg_size_pretty(pg_indexes_size('student'));


show index firstname_index;


-- Find user by surname (partial match)
select firstname from student s where s.student_id = 16278;
explain analyze
select * from student s where s.firstname like '7d8810d9b9%';
explain analyze
select * from student s where to_tsvector('english', s.firstname) @@ to_tsquery('english', '7d8810d9b96d34:*');


-- Find user by phone number (partial match)
explain analyze
select s.firstname, p.phonenumber from student s 
join phone p on s.student_id = p.student_id 
where p.phonenumber like '12%';

explain analyze
select s.firstname, p.phonenumber from student s 
join phone p on s.student_id = p.student_id 
where to_tsvector('english', p.phonenumber) @@ to_tsquery('english', '12:*');


drop index phonenumber_index;

CREATE INDEX phonenumber_index ON phone (phonenumber);
CREATE INDEX phonenumber_index ON phone USING hash (phonenumber);
CREATE INDEX phonenumber_index ON phone USING gin (to_tsvector('english', phonenumber));
CREATE INDEX phonenumber_index ON phone USING gist (to_tsvector('english', phonenumber));

-- Find user with marks by user surname (partial match)
select firstname from student s where s.student_id = 2132;
explain analyze
select s.student_id, s.firstname, e.subject_id, e.mark from student s 
join exam e on s.student_id = e.student_id 
where s.firstname like '92719510f61625f5df798382c02e5e%';

explain analyze
select s.student_id, s.firstname, e.subject_id, e.mark from student s 
join exam e on s.student_id = e.student_id 
where to_tsvector('english', s.firstname) @@ to_tsquery('english', '92719510f61625f5df798382c02e5e:*');

drop index firstname_index;

CREATE INDEX firstname_index ON student (firstname);
CREATE INDEX firstname_index ON student USING hash (firstname);
CREATE INDEX firstname_index ON student USING gin (to_tsvector('english', firstname));
CREATE INDEX firstname_index ON student USING gist (to_tsvector('english', firstname));

-- Add trigger that will update column updated_datetime to current date in case of updating any of student. 
CREATE or replace FUNCTION update_timestamp() RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
BEGIN
  NEW.last_updated_timestamp := current_timestamp;
  RETURN NEW;
END;
$$;

create or replace trigger last_updated_timestamp_update before update on student 
FOR EACH ROW EXECUTE PROCEDURE update_timestamp();

UPDATE student SET firstname='12223'
WHERE student_id=500004;


-- reject student name with next characters '@', '#', '$')
ALTER TABLE public.student DROP CONSTRAINT if exists student_firstname_check;
ALTER TABLE public.student ADD CONSTRAINT student_firstname_check CHECK (firstname not like '%@%'  and firstname not like '%#%' and firstname not like '%$%');

INSERT INTO student (firstname) 
VALUES('slkdjdlsk@');

INSERT INTO student (firstname) 
VALUES('slkdj#dlsk');
 
INSERT INTO student (firstname) 
VALUES('slk$dlsk');


-- Create function that will return average mark for input user.
Create or replace function get_student_average_mark(Student_firstname varchar)  
returns setof record
language plpgsql  
as  
$$
declare               
  avg_ numeric;
Begin  
	select avg(e.mark) from student s 
	into avg_
	join exam e on s.student_id=e.student_id
	group by s.firstname
	having s.firstname = Student_firstname;
   return avg_;  
End;  
$$; 

select s.firstname, avg(e.mark) from student s 
join exam e on s.student_id=e.student_id
group by s.firstname
having s.firstname = 'bf180a5dd66248eb708bbdd3e8832361';

select get_student_average_mark('bf180a5dd66248eb708bbdd3e8832361');

-- Create function that will return avarage mark for input subject name.
Create or replace function get_subject_average_mark(Subject varchar)  
returns numeric
language plpgsql  
as  
$$
declare               
  avg_ numeric;
Begin  
	select avg(e.mark) from subject s 
	into avg_
	join exam e on s.subject_id = e.subject_id
	group by s.subject_name
    having s.subject_name = Subject;
    return avg_;  
End;  
$$;

select get_subject_average_mark('9f455b98fff692f554d11a106e7774c2');

select s.subject_name from subject s; 
select s.subject_name, avg(e.mark) from subject s 
join exam e on s.subject_id = e.subject_id
group by s.subject_name
having s.subject_name = '9f455b98fff692f554d11a106e7774c2';

-- Create function that will return student at "red zone" (red zone means at least 2 marks <=3).
create type student_fullname as (
    firstname varchar,
    lastname varchar
   );

Create or replace function get_red_zone_students()  
returns setof student_fullname
language plpgsql  
as  
$$
declare
    r student_fullname;
begin
	for r in execute ' 
			select s.firstname, s.lastname from student s 
			join exam e on s.student_id = e.student_id
			where e.mark <=3
			group by s.student_id
			'
	    loop
	    	return next r;
	end loop;
end
$$;

select * from get_red_zone_students();

select s.firstname, s.lastname, count(e.mark)from student s 
join exam e on s.student_id = e.student_id
where e.mark <=3 and s.firstname='bf180a5dd66248eb708bbdd3e8832361'
group by s.student_id
having count(e.mark) >=2;




