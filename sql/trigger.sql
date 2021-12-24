CREATE OR REPLACE FUNCTION update_delete_func() RETURNS TRIGGER as $$

DECLARE CURSOR_LOG CURSOR FOR SELECT * FROM reader_log;
row_Log reader_log%ROWTYPE;

begin
    IF old.id % 2 = 0 THEN
        INSERT INTO reader_log(id, first_name, second_name) VALUES (old.id, old.first_name, old.second_name);
        UPDATE reader_log SET first_name = trim(BOTH 'x' FROM first_name);

        RETURN NEW;
    ELSE
        RAISE NOTICE 'readerID is odd';

        FOR row_log IN cursor_log LOOP
                UPDATE reader_log SET first_name = 'x' || row_Log.first_name || 'x' WHERE "id" = row_log."id";
        END LOOP;

        RETURN NEW;
    END IF;
END;

$$ language plpgsql;

create trigger reader_trigger before delete or update on reader for each row
execute procedure update_delete_func();