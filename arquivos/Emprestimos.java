public class Emprestimos {
    private int id_emprestimo;
    private int id_usuario;
    private int id_exemplar;
    private String data_Emprestimo;
    private String data_Devolução;
    
    public Emprestimos (int id_emprestimo, int id_usuario, int id_exemplar, String data_Emprestimo, String data_Devolução){
        this.id_emprestimo = id_emprestimo;
        this.id_usuario = id_usuario;
        this.id_exemplar = id_exemplar;
        this.data_Emprestimo = data_Emprestimo;
        this.data_Devolução = data_Devolução;
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

    public int getId_emprestimo() {
        return this.id_emprestimo;
    }

    public int getId_usuario() {
        return this.id_usuario;
    }

    public String getData_Emprestimo() {
        return this.data_Emprestimo;
    }

    public String getData_Devolução() {
        return this.data_Devolução;
    }
}
