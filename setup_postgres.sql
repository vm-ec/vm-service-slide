-- Run these commands in pgAdmin or psql as the postgres user

-- Create the user 'sa' with password 'Password@01'
CREATE USER sa WITH PASSWORD 'Password@01';

-- Create the database 'vm-tsc-slide-deck' owned by 'sa'
CREATE DATABASE "vm-tsc-slide-deck" OWNER sa;

-- Grant all privileges on the database to 'sa'
GRANT ALL PRIVILEGES ON DATABASE "vm-tsc-slide-deck" TO sa;
