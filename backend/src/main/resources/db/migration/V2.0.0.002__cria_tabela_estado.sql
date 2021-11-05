create table cb_estado (

	id       bigint      not null auto_increment,
	nome     varchar(20) not null,
	uf       varchar(2)  not null,
	pais_id  bigint      not null,
	
	primary key (id)
	
);

alter table cb_estado add constraint fk_estado_pais
foreign key (pais_id) references cb_pais (id);