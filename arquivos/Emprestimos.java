import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Emprestimos {
    private int id_emprestimo;
    private int id_usuario;
    private int id_exemplar;
    private String data_Emprestimo;
    private String data_Devolução;
    private String data_retorno;
    private String status;
    
    //construtor
    public Emprestimos (int id_emprestimo, int id_usuario, int id_exemplar, String data_Emprestimo, String data_Devolução, String data_retorno, String status){
        this.id_emprestimo = id_emprestimo;
        this.id_usuario = id_usuario;
        this.id_exemplar = id_exemplar;
        this.data_Emprestimo = data_Emprestimo;
        this.data_Devolução = data_Devolução;
        this.data_retorno = data_retorno;
        this.status = status;
    }

    //SETERS PARA DEVOLUÇÃO
    public void setarParaDevolucao (int id_usuario, String titulo){
        String select = "select e.ID_EMPRESTIMO, e.ID_EXEMPLAR, E.DATA_DEVOLUCAO from emprestimo e join exemplar ex on ex.ID_EXEMPLAR = e.ID_EXEMPLAR join livro l on l.ID_LIVRO = ex.ID_LIVRO where e.ID_USUARIO = ? and e.STATUS = 'PENDENTE' and l.titulo = ?";
        int idEmprestimo = -1;
        int idExemplar = -1;
        String data_devolucao = null;

        try (Connection conn = ConexaoBanco.getConnection();PreparedStatement stmt = conn.prepareStatement(select)){

            stmt.setInt(1, id_usuario);
            stmt.setString(2, titulo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEmprestimo = rs.getInt("ID_EMPRESTIMO");
                    idExemplar = rs.getInt("ID_EXEMPLAR");
                    data_devolucao = rs.getString("DATA_DEVOLUCAO");
                } else {
                    System.err.println("Nenhum empréstimo pendente com esse livro encontrado com esse usuário.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar dados no banco: " + e.getMessage());

        }
        
        setId_emprestimo(idEmprestimo);
        setId_exemplar(idExemplar);
        setData_Devolução(data_devolucao);
        
    }

    //Setters
    public void setData_retorno(String data_retorno) {
        this.data_retorno = data_retorno;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setId_exemplar(int id_exemplar){
        this.id_exemplar = id_exemplar;
    }

    public void setId_emprestimo(int id_emprestimo) {
        this.id_emprestimo = id_emprestimo;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setData_Emprestimo(String data_Emprestimo) {
        this.data_Emprestimo = data_Emprestimo;
    }

    public void setData_Devolução(String data_Devolução) {
        this.data_Devolução = data_Devolução;
    }
    
    //Getters
    public int getId_emprestimo() {
        return id_emprestimo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public int getId_exemplar() {
        return id_exemplar;
    }

    public String getData_Emprestimo() {
        return data_Emprestimo;
    }

    public String getData_Devolução() {
        return data_Devolução;
    }

    public String getData_retorno() {
        return data_retorno;
    }

    public String getStatus() {
        return status;
    }

    
}
