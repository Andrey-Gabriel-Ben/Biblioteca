import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genero {
    int id_genero;
    String nome;

    public Genero (int id_genero, String nome) {
        this.id_genero = id_genero;
        this.nome = nome;
    }

    protected void  buscarIdPorNome(String genero){
        String select = "SELECT ID_GENERO FROM genero WHERE nome = ?";
        int idEncontrado =  -1; 

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(select)) {

            stmt.setString(1, genero);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEncontrado = rs.getInt("ID_GENERO");
                }
            } 

        } catch (SQLException e) {
            ErroDeCriação("Erro ao buscar genero: " + e.getMessage());
        }

        this.setId_genero(idEncontrado);
        
    }

//iniciar utils
    protected String apenasNumeros(String NumerosSujos){
        String apenasNumeros = NumerosSujos.replaceAll("[^0-9]", "");
        return apenasNumeros;
    }

    protected void ErroDeCriação(String msg) {
        System.err.println(msg);
    }

    protected static boolean  validarEntrada(String entrada){
        if (entrada == null || entrada.trim().isEmpty()) {
            System.err.println("O campo não pode estar vazio!");
            return false;
        }
        return true;
    }

// fim utils
    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_genero() {
        return id_genero;
    }

    public String getNome() {
        return nome;
    }

    public static void main(String[] args) {
            //genero
            Genero catLivro = new Genero(0, "Ficção Científica");
            catLivro.buscarIdPorNome("Ficção Científica");
            
            System.out.println(catLivro.getId_genero());

        

    }



}
//genero: ID, genero (ARMAZENADA EM UMA LISTA PARA CONSULTA, MAS POSSUIR UM GET E UM SET)