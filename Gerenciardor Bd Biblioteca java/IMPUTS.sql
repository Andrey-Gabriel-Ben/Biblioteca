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


INSERT INTO livro (titulo, autor, ano_lancamento, isbn, id_genero) VALUES 
('Duna', 'Frank Herbert', 6, '9788576573135', 1),
('Neuromancer', 'William Gibson', 1984, '9788576573005', 1),
('O Hobbit', 'J.R.R. Tolkien', 1937, '9788595084742', 2),
('O Nome do Vento', 'Patrick Rothfuss', 2007, '9788592260508', 2),
('O Iluminado', 'Stephen King', 1977, '9788535922370', 3),
('Drácula', 'Bram Stoker', 1897, '9788582850381', 3),
('Sherlock Holmes: Um Estudo em Vermelho', 'Arthur Conan Doyle', 1887, '9788537801338', 8),
('O Código Da Vinci', 'Dan Brown', 2003, '9788575420959', 8),
('Orgulho e Preconceito', 'Jane Austen', 1813, '9788563560278', 5),
('Dom Casmurro', 'Machado de Assis', 1899, '9788582850121', 6),
('O Pequeno Príncipe', 'Antoine de Saint-Exupéry', 1943, '9788520925041', 13),
('Sapiens: Uma Breve História da Humanidade', 'Yuval Noah Harari', 2011, '9788525432186', 10),
('A Arte da Guerra', 'Sun Tzu', -500, '9788572327046', 17),
('O Morro dos Ventos Uivantes', 'Emily Brontë', 1847, '9788542211429', 6),
('Frankenstein', 'Mary Shelley', 1818, '9788594540454', 3),
('1984', 'George Orwell', 1949, '9788535914849', 1),
('O Silmarillion', 'J.R.R. Tolkien', 1977, '9788595084377', 2),
('Assassinato no Expresso do Oriente', 'Agatha Christie', 1934, '9788525414656', 8),
('Ensaio Sobre a Cegueira', 'José Saramago', 1995, '9788571644748', 6),
('Clean Code', 'Robert C. Martin', 2008, '9788576082675', 15);


INSERT INTO exemplar (ID_LIVRO, aquisição, status) VALUES 
(1, '2026-04-22', 'DISPONIVEL'), (1, '2026-04-22', 'INDISPONIVEL'), (1, '2026-04-22', 'DISPONIVEL'),
(2, '2026-04-22', 'DISPONIVEL'), (2, '2026-04-22', 'DISPONIVEL'), (2, '2026-04-22', 'INDISPONIVEL'),
(3, '2026-04-22', 'DISPONIVEL'), (3, '2026-04-22', 'DISPONIVEL'), (3, '2026-04-22', 'DISPONIVEL'),
(4, '2026-04-22', 'INDISPONIVEL'), (4, '2026-04-22', 'DISPONIVEL'), (4, '2026-04-22', 'DISPONIVEL'),
(5, '2026-04-22', 'DISPONIVEL'), (5, '2026-04-22', 'INDISPONIVEL'), (5, '2026-04-22', 'INDISPONIVEL'),
(6, '2026-04-22', 'DISPONIVEL'), (6, '2026-04-22', 'INDISPONIVEL'), (6, '2026-04-22', 'DISPONIVEL');
INSERT INTO exemplar (ID_LIVRO, aquisição, status) VALUES 
(7, '2026-04-22', 'INDISPONIVEL'), (7, '2026-04-22', 'INDISPONIVEL'), (7, '2026-04-22', 'INDISPONIVEL');

select ID_EXEMPLAR from exemplar where ID_LIVRO = 6 and status = 'DISPONÍVEL';
insert into emprestimo (ID_USUARIO, ID_EXEMPLAR, DATA_EMPRESTIMO, DEVOLUÇÃO_DATA) values(?, ?, STR_TO_DATE('?', '%d/%m/%Y'), STR_TO_DATE('?', '%d/%m/%Y'));
insert into usuarios (NOME, CPF, EMAIL, TELEFONE, TIPO) values ("andrey testes", "12345678909","andrey_testes@email.com", "5547912345678", "aluno");

