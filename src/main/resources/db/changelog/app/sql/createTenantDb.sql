DO
$do$
BEGIN
   IF EXISTS (SELECT FROM pg_database WHERE datname = 'mydb') THEN
      RAISE NOTICE 'Database already exists';  -- optional
ELSE
      PERFORM dblink_exec('dbname=' || current_database()  -- current db
                        , 'CREATE DATABASE mydb');
END IF;
END
$do$;