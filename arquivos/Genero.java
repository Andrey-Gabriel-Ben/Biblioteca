import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Genero {
    private int id_genero;
    private String nome;

    //construtor
    public Genero (int id_genero, String nome) {
        this.id_genero = id_genero;
        this.nome = nome;
    }

    //buscas no banco
    protected int  buscarIdPorNome(String genero){
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
            System.err.println("Erro ao buscar genero: " + e.getMessage());
        }

        return idEncontrado;
        
    }

    //Setters e getters
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

}
