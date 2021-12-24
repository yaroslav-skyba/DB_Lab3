create index book_btree_index on book using btree (page_count);
create index subscription_hash_index on subscription using hash (reader_id);