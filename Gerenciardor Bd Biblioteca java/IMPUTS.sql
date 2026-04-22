-- esses são imputs para alimentar o banco

INSERT INTO genero (nome) VALUES 
('Ficção Científica'),
('Fantasia'),
('Terror'),
('Suspense'),
('Romance'),
('Drama'),
('Aventura'),
('Mistério'),
('Biografia'),
('História'),
('Autoajuda'),
('Poesia'),
('Infantil'),
('Gastronomia'),
('Tecnologia'),
('Psicologia'),
('Filosofia'),
('Religião'),
('HQs e Mangás'),
('Clássicos');

insert into exemplar (ID_LIVRO, aquisição, status) values (?, STR_TO_DATE(?, '%d/%m/%Y'), ?)

SELECT ID_GENERO FROM genero WHERE nome = "Ficção Científica";
SELECT * FROM LIVRO;