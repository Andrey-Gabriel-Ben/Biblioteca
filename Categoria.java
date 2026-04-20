public class Categoria {
    int id;
    String Categoria;

    public Categoria(){
        // E-mail
        System.out.print("Digite o E-mail do usuario: ");
        String emailImput = scanner.next();
        validarEntrada(nomeImput);
        this.Categoria = emailImput;

    }
}

//CATEGORIA: ID, CATEGORIA (ARMAZENADA EM UMA LISTA PARA CONSULTA, MAS POSSUIR UM GET E UM SET)