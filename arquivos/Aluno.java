public class Aluno extends Usuario {
    
    public Aluno(int id_Usuario, String nome, String cpf, String email, String telefone) {
        super(id_Usuario, nome, email, telefone, telefone);
        this.tipo = "Aluno";
    }

}