public class Professor extends Usuario {
    
    public Professor(int id_Usuario, String nome, String cpf, String email, String telefone) {
        super(id_Usuario, nome, cpf, email, telefone);
        this.tipo = "Professor";
    }

    
}