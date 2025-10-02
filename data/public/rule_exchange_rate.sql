create table rule_exchange_rate
(
    currency_code varchar(255) not null
        primary key,
    rate          numeric(38, 2)
);

alter table rule_exchange_rate
    owner to admin;

