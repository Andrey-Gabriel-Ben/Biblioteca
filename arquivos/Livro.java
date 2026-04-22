public class Livro{
    private int id_livro;
    private String titulo;
    private String Autor;
    private int ano;
    private int id_genero;
    private String isbn;

    protected Livro(int id_livro, String titulo, String Autor, int ano, int id_genero, String isbn) {
        this.id_livro = id_livro;
        this.titulo = titulo;
        this.Autor = Autor;
        this.ano = ano;
        this.id_genero = id_genero;
        this.isbn = isbn;
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

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getId_livro() {
        return this.id_livro;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getAutor() {
        return this.Autor;
    }

    public int getAno() {
        return this.ano;
    }

    public int getId_genero() {
        return this.id_genero;
    }

    public String getIsbn() {
        return this.isbn;
    }
    
    public static void main(String[] args) {
        
    }
    


}

// LIVRO: ID, TITULO, AUTOR, EDITORA, ANO PUBLICAÇÃO, “genero”, EDIÇÃO, ISBN
