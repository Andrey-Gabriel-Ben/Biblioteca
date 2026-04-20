import java.util.Scanner;

public /* abstract */ class Livro {
    int id_livro;
    String titulo;
    String Autor;
    String ano;
    String Categoria;
    String isbn;

    protected Livro(int id_livro, String titulo, String Autor, String ano, String Categoria, String isbn) {
        this.id_livro = id_livro;
        this.titulo = titulo;
        this.Autor = Autor;
        this.ano = ano;
        this.Categoria = Categoria;
        this.isbn = isbn;
    }

    public void cadastrarLivro() {
        try (Scanner scanner = new Scanner(System.in)) {
            // titulo
            System.out.print("Digite o nome do Livro: ");
            String nomeImput = scanner.nextLine();
            validarEntrada(nomeImput);
            setNome(nomeImput);
            
            //this.sendUserToDatabase();

        } catch (Exception e) {
            System.out.println("Devido ao erro, Livro não foi criado, por favor tente novamente\n");
        }

    }

    public static void validarEntrada(String entrada) throws Exception {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new Exception("O campo não pode estar vazio!");
        }
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getId_livro() {
        return id_livro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public String getAno() {
        return ano;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getIsbn() {
        return isbn;
    }
    
    
    


}

// LIVRO: ID, TITULO, AUTOR, EDITORA, ANO PUBLICAÇÃO, “CATEGORIA”, EDIÇÃO, ISBN
