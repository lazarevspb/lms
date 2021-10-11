create table roles
(
    id   bigserial
        constraint roles_pkey
            primary key,
    name varchar(255)
);

create table users
(
    id       bigserial
        constraint users_pkey
            primary key,
    email    varchar(255)
        constraint uk_user_email
            unique,
    password varchar(255),
    username varchar(255)
        constraint uk_username
            unique
);

create table users_roles
(
    users_id bigint not null
        constraint fk_users_roles_users_id
            references users,
    roles_id bigint not null
        constraint fk_users_roles_roles_id
            references roles,
    constraint users_roles_pkey
        primary key (users_id, roles_id)
);

create table course
(
    id     bigserial not null,
    author varchar(255),
    title  varchar(255),
    primary key (id)
);

create table module
(
    id        bigserial not null,
    title     varchar(255),
    course_id int8,
    primary key (id)

);

alter table module
    add constraint FKfq09oddpwjoxcirvkh9vnfnsg
        foreign key (course_id)
            references course;

create table lessons
(
    id          bigserial PRIMARY KEY not null,
    module_id   bigserial,
    title       varchar(50),
    description varchar(400),
    created_by  bigserial,
    updated_by  bigserial,
    content     varchar(255),
    exercise    varchar(500),
    foreign key (module_id) references module (id)
);