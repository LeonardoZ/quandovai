INSERT INTO `quandovai_schema`.`usuarios`
(`email`,
`nome`,
`salt`,
`senha_hash`,
`deletado`)
VALUES('leo.zapparoli@gmail.com','Leonze','82B0369FBF21C6D1C1718B3B48A23FBA0AC78570','FAE4CE6E796026E7',false);

insert into clientes (nome_completo, email, sexo, data_aniversario, celular, celular2, deletado)
	values ('Leonardo Henry','leonardo1@gmail.com','MASCULINO','1992-07-12','(14)99123-3333','(14)99123-4444', false),
		   ('Bruce Wayner','bruce.wayne@batcave.com','MASCULINO','1975-01-25','(14)85643-1236','(14)99534-2324', false),
		   ('Clark Kent','clark.kent@daily.com','MASCULINO','1973-11-25','(14)27384-2223','(14)22423-3334', false),
		   ('Laurel Lance','laurel.lance@canary.com','FEMININO','1983-09-02','(14)54456-5344','(14)7778-1276', false);

INSERT INTO `quandovai_schema`.`modelo_mensagens`
(`conteudo`,
`tipo_envio`,
`usuario_id`,
`deletado`)
VALUES
('Esse é o conteúdo que será enviado no','SMS', 1, false),
('Esse é o segundo conteúdo que será enviado no','SMS', 1, false),
('Esse é o terceiro conteúdo que será enviado no','SMS', 1, false);
