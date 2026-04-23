import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Livro {
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

    // buscas
    protected int buscarIdPorNomeLivro(String nomeLivro){
        String select = "SELECT ID_LIVRO FROM LIVRO WHERE titulo = ?";
        int idEncontrado =  -1; 

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(select)) {

            stmt.setString(1, nomeLivro);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEncontrado = rs.getInt("ID_LIVRO");
                }
            } 

        } catch (SQLException e) {
            System.err.println("Erro ao buscar Livro: " + e.getMessage());
        }

        return idEncontrado;
    }

    // metodos de calculo e verificação

    protected boolean calculaisbn(String isbnSujo) {
        String isbnLimpo = apenasNumeros(isbnSujo);

        if (isbnLimpo.length() != 13 || isbnLimpo.matches("(\\d)\\1{10}")) {
            System.err.println("O isbn inserido é invalido ou desatualizado");
            return false;
        }

        try {

            // transforma em array
            int[] isbnAray = new int[13];
            for (int i = 0; i < 13; i++) {
                isbnAray[i] = Character.getNumericValue(isbnLimpo.charAt(i));
            }

            // calcula:
            // soma multiplicações
            int soma = 0;
            int peso;
            for (int i = 0; i < 12; i++) {
                if ((i + 1) % 2 == 0) {
                    peso = 3;
                } else {
                    peso = 1;
                }
                soma += isbnAray[i] * peso--;
            }

            // divide
            int verificador = 10 - (soma % 10);

            // verifica
            if (verificador == isbnAray[12]) {
                return true;
            } else {
                System.err.println("O isbn inserido está incorreto, verifique.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("O isbn inserido é invalido ou desatualizado");
            return false;
        }

    }

    protected String apenasNumeros(String NumerosSujos) {
        String apenasNumeros = NumerosSujos.replaceAll("[^0-9]", "");
        return apenasNumeros;
    }

    // getters e setters:
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

}

// LIVRO: ID, TITULO, AUTOR, EDITORA, ANO PUBLICAÇÃO, “genero”, EDIÇÃO, ISBN
