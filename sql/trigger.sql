DROP TABLE IF EXISTS trigger_test;
CREATE TABLE trigger_test(trigger_test_id bigserial PRIMARY KEY, trigger_test_name text);

DROP TABLE IF EXISTS trigger_test_log;
CREATE TABLE trigger_test_log(id bigserial PRIMARY KEY, trigger_test_log_id bigint, trigger_test_log_name text);

CREATE OR REPLACE FUNCTION before_delete_update_func() RETURNS TRIGGER as $trigger$
DECLARE
    CURSOR_LOG CURSOR FOR SELECT * FROM trigger_test_log;
    row_ trigger_test_log%ROWTYPE;

BEGIN
    IF old.trigger_test_id % 2 = 0 THEN
        IF old.trigger_test_id % 3 = 0 THEN
            RAISE NOTICE 'trigger_testID is multiple of 2 and 3';

            FOR row_ IN CURSOR_LOG LOOP
                UPDATE trigger_test_log SET trigger_test_log_name = '_' || row_.trigger_test_log_name || '_log' WHERE id = row_.id;
            END LOOP;

            RETURN OLD;
        ELSE
            RAISE NOTICE 'trigger_testID is even';
            INSERT INTO trigger_test_log(trigger_test_log_id, trigger_test_log_name)
            VALUES (old.trigger_test_id, old.trigger_test_name);
            UPDATE trigger_test_log SET trigger_test_log_name = trim(BOTH '_log' FROM trigger_test_log_name);

            RETURN NEW;
        END IF;
    ELSE
        RAISE NOTICE 'trigger_testID is odd';

        FOR row_ IN CURSOR_LOG LOOP
            UPDATE trigger_test_log SET trigger_test_log_name = '_' || row_.trigger_test_log_name || '_log' WHERE id = row_.id;
        END LOOP;

        RETURN OLD;
    END IF;
END

$trigger$ LANGUAGE plpgsql;

CREATE TRIGGER before_delete_update_trigger
    BEFORE DELETE OR UPDATE ON trigger_test
    FOR EACH ROW
EXECUTE procedure before_delete_update_func();

INSERT INTO trigger_test(trigger_test_name) VALUES ('trigger_test1'),('trigger_test2'),
                                                   ('trigger_test3'), ('trigger_test4'),
                                                   ('trigger_test5'), ('trigger_test6'),
                                                   ('trigger_test7'), ('trigger_test8'),
                                                   ('trigger_test9'), ('trigger_test10');

DELETE FROM trigger_test WHERE trigger_test_id % 3 = 0;
UPDATE trigger_test SET trigger_test_name = trigger_test_name || '_log' WHERE trigger_test_id % 2 = 0;