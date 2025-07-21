CREATE TABLE IF NOT EXISTS event (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS event_venue (
    event_id BIGINT REFERENCES event(id) NOT NULL,
    brand VARCHAR(255) REFERENCES venue(brand) NOT NULL,
    provider VARCHAR(255) REFERENCES venue(provider)NOT NULL,
    external_id VARCHAR(255) REFERENCES venue(external_id) NOT NULL,
    PRIMARY KEY(event_id, brand, provider, external_id)
);
