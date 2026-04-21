public /* abstract */ class Livro{
    int id_livro;
    String titulo;
    String Autor;
    int ano;
    String genero;
    String isbn;

    protected Livro(int id_livro, String titulo, String Autor, int ano, String genero, String isbn) {
        this.id_livro = id_livro;
        this.titulo = titulo;
        this.Autor = Autor;
        this.ano = ano;
        this.genero = genero;
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

    public void setgenero(String genero) {
        this.genero = genero;
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

    public int getAno() {
        return ano;
    }

    public String getgenero() {
        return genero;
    }

    public String getIsbn() {
        return isbn;
    }
    
    public static void main(String[] args) {
        
    }
    


}

// LIVRO: ID, TITULO, AUTOR, EDITORA, ANO PUBLICAÇÃO, “genero”, EDIÇÃO, ISBN
