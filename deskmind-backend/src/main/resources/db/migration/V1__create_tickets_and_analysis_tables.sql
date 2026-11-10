CREATE TABLE ticket (
                 id UUID PRIMARY KEY,
                 title VARCHAR(150) NOT NULL,
                 description TEXT,
                 status VARCHAR(30) NOT NULL,
                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ticket_analysis (
                   id UUID PRIMARY KEY,
                   ticket_id UUID NOT NULL,
                   category VARCHAR(30),
                   suggested_priority VARCHAR(20),
                   summary TEXT,
                   suggested_response TEXT,
                   reasoning TEXT,
                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                   CONSTRAINT fk_ticket FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE
);