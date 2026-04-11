DROP TABLE IF EXISTS WALLET;

CREATE TABLE WALLET (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    balance numeric(8,2) NOT NULL,
	user_id UUID not null
);

ALTER TABLE WALLET ADD CONSTRAINT FK_WALLET_USER FOREIGN KEY (user_id) references users(id)
