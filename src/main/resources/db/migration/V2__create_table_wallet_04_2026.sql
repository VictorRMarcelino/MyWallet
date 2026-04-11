DROP TABLE IF EXISTS WALLET CASCADE;

CREATE TABLE WALLET (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    balance numeric(8,2) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES users(id)
);
