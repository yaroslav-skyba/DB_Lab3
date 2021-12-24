select * from book where page_count = 1;
select avg(page_count) from book;
select page_count from book group by page_count;
select * from book order by page_count;

select * from subscription where reader_id = 1;
select avg(reader_id) from subscription where reader_id = 1;
select reader_id from subscription where reader_id = 1 group by reader_id;
select * from subscription where reader_id = 1 order by reader_id;