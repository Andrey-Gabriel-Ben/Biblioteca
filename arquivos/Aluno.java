public class Aluno extends Usuario {
    
    public Aluno(int id, String nome, String email, String telefone, String endereco) {
        super(id, nome, email, telefone, endereco);
        this.tipo = "Aluno";
    }

}