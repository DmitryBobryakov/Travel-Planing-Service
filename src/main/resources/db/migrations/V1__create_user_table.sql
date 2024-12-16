CREATE TABLE "user"
(
    profile_id BIGINT PRIMARY KEY,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    friends_id integer[]
)
