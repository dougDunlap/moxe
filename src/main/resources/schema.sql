drop table if exists provider_patient;
drop table if exists hospital_provider ;
drop table if exists hospital;
drop table if exists provider;
drop table if exists patient;

create table patient
(
    id uuid not null default random_uuid(),
    first_name varchar(255) not null,
    middle_name varchar(255),
    last_name varchar(255) not null,
    primary key(id)
);

create table provider
(
    id uuid not null default random_uuid(),
    first_name varchar(255) not null,
    middle_name varchar(255),
    last_name varchar(255) not null,
    primary key(id)
);

create table hospital
(
    id uuid not null default random_uuid(),
    name varchar(255) not null,
    primary key(id),
    constraint unique_name unique(name)
);

create table hospital_provider
(
    id uuid not null default random_uuid(),
    hospital_id uuid not null,
    provider_id uuid not null,
    primary key(id),
    foreign key(hospital_id) references hospital(id),
    foreign key(provider_id) references provider(id),
    constraint unique_hospital_id_provider_id unique(hospital_id, provider_id)
);

create table provider_patient
(
    id uuid not null default random_uuid(),
    patient_id uuid not null,
    hospital_provider_id uuid not null,
    active boolean not null default true,
    primary key(id),
    foreign key(patient_id) references patient(id),
    foreign key(hospital_provider_id) references hospital_provider(id),
    constraint unique_patient_id_hospital_provider_id unique(patient_id, hospital_provider_id)
);
