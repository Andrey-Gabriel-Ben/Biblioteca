public class Professor extends Usuario {
    
    public Professor(int id, String nome, String email, String telefone, String endereco) {
        super(id, nome, email, telefone, endereco);
        this.tipo = "Professor";
    }

    
}