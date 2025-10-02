create table rule_sanction
(
    country_code varchar(255) not null
        primary key,
    active       boolean      not null,
    reason       varchar(255)
);

alter table rule_sanction
    owner to admin;

