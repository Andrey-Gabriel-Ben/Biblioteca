create database bibliotecajava;
/*drop database bibliotecajava;*/

/*drop table usuarios;*/
CREATE TABLE USUARIOS(
	ID_GENERO int AUTO_INCREMENT NOT NULL,
	NOME VARCHAR(100) NOT NULL,
    CPF char(11) NOT NULL unique, 
    EMAIL VARCHAR(254) NOT NULL unique,
	TELEFONE char(19) NOT NULL,
    TIPO VARCHAR(10),
	PRIMARY KEY(CODIGO_USUARIO)
);
select * from usuarios;

/*drop table GENERO;*/
CREATE TABLE GENERO (
	ID_GENERO INT auto_increment NOT NULL ,
    nome varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci unique,
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

/*
String titulo;
    String Autor;
    String ano;
    String GENERO;
    String isbn;


CREATE TABLE usuarios (
    nome VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci_as;
);



ALTER TABLE genero
ALTER COLUMN nome VARCHAR(50) COLLATE Latin1_General_CI_AS;
-- CI = Case Insensitive, AS = Accent Sensitive



*/

