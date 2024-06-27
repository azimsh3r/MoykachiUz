CREATE SCHEMA IF NOT EXISTS moykachi;

SET search_path TO moykachi;

CREATE TABLE IF NOT EXISTS auto_user (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    role VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    created_at timestamp
);

-- Create table auto_brand
CREATE TABLE IF NOT EXISTS auto_brand (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    brand VARCHAR(255),
    logo VARCHAR(1000)
);

-- Create table auto_model
CREATE TABLE IF NOT EXISTS auto_model (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    model VARCHAR(255),
    brand_id BIGINT,
    FOREIGN KEY (brand_id) REFERENCES auto_brand(id)
);

-- Create table auto_instance
CREATE TABLE IF NOT EXISTS auto_instance(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    color VARCHAR(255),
    number VARCHAR(255),
    model_id BIGINT,
    FOREIGN KEY (model_id) REFERENCES auto_model(id)
);

-- Create table authentication_principal
CREATE TABLE IF NOT EXISTS authentication_principal(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    token VARCHAR(255),
    otp BIGINT,
    expires_at timestamp,
    chat_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES auto_user(id)
);

-- Create table authentication_principal
CREATE TABLE IF NOT EXISTS auto_wash_registry (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    started_at timestamp,
    finished_at timestamp,
    user_id BIGINT,
    auto_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES auto_user(id),
    FOREIGN KEY (auto_id) REFERENCES auto_instance(id)
);
