create database bibliotecajava
CHARACTER SET utf8mb4 
COLLATE utf8mb4_0900_ai_ci;
/*drop database bibliotecajava;*/

/*drop table usuarios;*/
CREATE TABLE USUARIOS(
	ID_USUARIO int AUTO_INCREMENT NOT NULL,
	NOME VARCHAR(100) NOT NULL,
    CPF char(11) NOT NULL unique, 
    EMAIL VARCHAR(254) NOT NULL unique,
	TELEFONE char(19) NOT NULL,
    TIPO VARCHAR(10),
    
	PRIMARY KEY(ID_USUARIO)
);
select * from usuarios;

/*drop table GENERO;*/
CREATE TABLE GENERO (
	ID_GENERO INT auto_increment NOT NULL ,
    nome varchar(50) unique,
    
    PRIMARY KEY(ID_GENERO)
);
select * FROM GENERO;

/*drop table LIVRO;*/
CREATE TABLE LIVRO (
	ID_LIVRO int auto_increment not null,
    titulo varchar(150) not null,
    autor varchar(100) not null,
    ano year not null,
    isbn char(13) not null unique,
	ID_GENERO int not null,
    
    primary key (ID_LIVRO),
    constraint fk_GENERO_lIVRO foreign key(ID_GENERO) REFERENCES GENERO(ID_GENERO)
);
select * FROM LIVRO;

/*drop table EXEMPLAR;*/
create table EXEMPLAR (
	ID_EXEMPLAR int auto_increment not null,
	ID_LIVRO int not null,
    aquisição date not null,
    status char(12),
    
    primary key (ID_EXEMPLAR),
    constraint fk_livro_exemplar foreign key(ID_LIVRO) REFERENCES LIVRO(ID_LIVRO)
)
select * FROM EXEMPLAR;

/*drop TABLE EMPRESTIMO*/
CREATE TABLE EMPRESTIMO (
	ID_EMPRESTIMO INT auto_increment NOT NULL,
    ID_USUARIO int NOT NULL,
    ID_EXEMPLAR INT NOT NULL,
    DATA_EMPRESTIMO DATE NOT NULL,
    DEVOLUÇÃO_DATA DATE NOT NULL,
    
    primary key (ID_EMPRESTIMO),
    constraint fk_USUARIOS_EMPRESTIMO foreign key(ID_USUARIO) REFERENCES USUARIOS(ID_USUARIO),
	constraint fk_EXEMPLAR_EMPRESTIMO foreign key(ID_EXEMPLAR) REFERENCES EXEMPLAR(ID_EXEMPLAR)
)
SELECT * FROM EMPRESTIMO;

