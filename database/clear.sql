DO $$BEGIN
    DROP TABLE addressesdatabase CASCADE;
    DROP TABLE cartsdatabase CASCADE;
    DROP TABLE customersdatabase CASCADE;
    DROP TABLE orderdetailsdatabase CASCADE;
    DROP TABLE ordersdatabase CASCADE;
    DROP TABLE productsdatabase CASCADE;
END $$;
