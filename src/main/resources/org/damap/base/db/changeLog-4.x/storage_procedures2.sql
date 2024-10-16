-- Keep available procedures up to date. These should be created with `CREATE OR REPLACE`


-- Storage
CREATE OR REPLACE PROCEDURE damap_insert_storage(url_ varchar, version_ integer, location_ varchar, backup_location_ varchar, active_ boolean)
LANGUAGE SQL
AS $$
    INSERT INTO
        internal_storage ( id, version, url, storage_location, backup_location, active )
    values
        (
            NEXTVAL ('internal_storage_seq'), version_, url_, location_, backup_location_, active_
        )
$$;


CREATE OR REPLACE PROCEDURE damap_update_storage(old_url_ varchar, url_ varchar, version_ integer, location_ varchar, backup_location_ varchar, active_ boolean)
LANGUAGE SQL
AS $$
UPDATE
    internal_storage
SET
    version = version_,
    url = url_,
    storage_location = location_,
    backup_location = backup_location_,
    active = active_
WHERE
    url = old_url_;
$$;


CREATE OR REPLACE PROCEDURE damap_upsert_storage(old_url_ varchar, url_ varchar, version_ integer, location_ varchar, backup_location_ varchar, active_ boolean)
LANGUAGE plpgsql
AS $$
BEGIN
        IF NOT EXISTS
        (
            SELECT
                1
            FROM
                internal_storage
            WHERE
                url = old_url_
        )
        THEN
            CALL damap_insert_storage(url_, version_, location_, backup_location_, active_);
ELSE
            CALL damap_update_storage(old_url_, url_, version_, location_, backup_location_, active_);
END IF;
END
$$;


-- Storage Translations
CREATE OR REPLACE PROCEDURE damap_insert_storage_translation(url_ varchar, version_ integer, language_code_ varchar, title_ varchar, description_ varchar)
LANGUAGE SQL
AS $$
    INSERT INTO
        inter_storage_translation ( id, version, internal_storage_id, language_code, title, description ) 
    values
        (
            NEXTVAL ('inter_storage_translation_seq'),
            version_,
            (
                SELECT
                id
                from
                internal_storage
                where
                url = url_
            ),
            language_code_,
            title_,
            description_
        );
$$;


CREATE OR REPLACE PROCEDURE damap_update_storage_translation(url_ varchar, version_ integer, language_code_ varchar, title_ varchar, description_ varchar)
LANGUAGE SQL
AS $$
    UPDATE
        inter_storage_translation
    SET
        version = version_,
        title = title_,
        description = description_
    WHERE
        language_code = language_code_ 
        AND internal_storage_id = 
        (
            SELECT
                id 
            from
                internal_storage 
            where
                url = url_
        );
$$;


CREATE OR REPLACE PROCEDURE damap_upsert_storage_translation(url_ varchar, version_ integer, language_code_ varchar, title_ varchar, description_ varchar)
LANGUAGE plpgsql
AS $$
    BEGIN
        IF NOT EXISTS
        (
            SELECT
                1 
            FROM
                inter_storage_translation 
            WHERE
                language_code = language_code_ 
                AND internal_storage_id = 
                (
                    SELECT
                        id 
                    from
                        internal_storage 
                    where
                        url = url_
                )
        )
        THEN
            CALL damap_insert_storage_translation(url_, version_, language_code_, title_, description_);
        ELSE
            CALL damap_update_storage_translation(url_, version_, language_code_, title_, description_);
        END IF;
    END
$$;
