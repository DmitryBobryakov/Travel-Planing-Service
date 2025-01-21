CREATE TABLE votingroom
(
    room_id BIGINT PRIMARY KEY,
    owner_id integer NOT NULL,
    name VARCHAR(20) NOT NULL,
    participants_id integer[],
    variants_names text[] NOT NULL,
    variants_interest_rate integer[] NOT NULL, --сколько человек проголосовало за каждый вариант
    state integer NOT NULL --1 - голосование завершено, 0 - иначе
)