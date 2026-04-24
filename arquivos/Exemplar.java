import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Exemplar {
    private int id_exemplar;
    private int id_livro;
    private String aquisição;
    private String Status;

    public Exemplar(int id_exemplar, int id_livro, String aquisição, String Status) {
        this.id_exemplar = id_exemplar;
        this.id_livro = id_livro;
        this.aquisição = aquisição;
        this.Status = Status;
    }

    public int buscarIdDisponivelPorIdLivro(int id_livro) {
        String select = "select ID_EXEMPLAR from exemplar where ID_LIVRO = ? and status = 'DISPONÍVEL';";
        int idEncontrado = -1;

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(select)) {

            stmt.setInt(1, id_livro);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEncontrado = rs.getInt("ID_EXEMPLAR");
                } else {
                    System.err.println("Nenhum exemplar Disponivel encontrado");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar Exemplar: " + e.getMessage());
        }

        return idEncontrado;
    }

    public void alterarStatus(int ID_EXEMPLAR, String Status) {
        String alter = "update exemplar set status = ? WHERE id_exemplar = ?;";

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(alter)) {

            stmt.setString(1, Status);
            stmt.setInt(2, ID_EXEMPLAR);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Status alterado com sucesso!");
            } else {
                System.out.println("Nenhum exemplar encontrado com o ID: " + ID_EXEMPLAR);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar status do Exemplar: " + e.getMessage());
        }

    }

    // getters e stters
    public void setId_exemplar(int id_exemplar) {
        this.id_exemplar = id_exemplar;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setAquisição(String aquisição) {
        this.aquisição = aquisição;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getId_exemplar() {
        return this.id_exemplar;
    }

    public int getId_livro() {
        return this.id_livro;
    }

    public String getAquisição() {
        return this.aquisição;
    }

    public String getStatus() {
        return this.Status;
    }

    public static void main(String[] args) {
        Exemplar ex = new Exemplar(0, 0, null, null);

        int teste = ex.buscarIdDisponivelPorIdLivro(6);
        System.out.println(teste);
    }

}
