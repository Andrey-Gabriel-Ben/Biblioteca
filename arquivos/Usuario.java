import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class Usuario {
    protected int id_Usuario;
    protected String nome;
    protected String cpf;
    protected String email; 
    protected String telefone; 
    protected String tipo;

    protected Usuario(int id_Usuario, String nome, String cpf, String email, String telefone) {
        this.id_Usuario = id_Usuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }
    // reescrever nas subclasses com tipo e puxar dadsos do banco

    public void cadastrarUsuario () {
        try (Scanner scanner = new Scanner(System.in)) {
            // nome
            System.out.print("Digite o nome do usuario: ");
            String nomeImput = scanner.nextLine();
            validarEntrada(nomeImput);
            setNome(nomeImput);

            // cpf
            System.out.print("Digite o CPF do usuario: ");
            String cpfimput = scanner.nextLine();
            validarCpf(cpfimput);
            cpfimput = apenasNumeros(cpfimput);
            setCpf(cpfimput);

            // E-mail
            System.out.print("Digite o E-mail do usuario: ");
            String emailImput = scanner.nextLine();
            validarEntrada(emailImput);
            setEmail(emailImput);

            // Telefone
            System.out.println("Digite o telefone do usuario contendo DDI, DDD e 9 dígitos.");
            String telefoneImput = scanner.nextLine();
            VerificarTelefone(telefoneImput);
            telefoneImput = apenasNumeros(telefoneImput);
            setTelefone(telefoneImput);

            //tipo
            setTipo(getTipo());

            this.sendUserToDatabase();

        } catch (Exception e) {
            System.out.println("Devido ao erro, usuario não foi criado, por favor tente novamente\n");
        }


    }
    //adicionar verificação para evitar usuarios repetidos

    protected void sendUserToDatabase() {
        String insert = "INSERT INTO usuarios (nome, cpf, email, telefone, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, this.getNome());
            stmt.setString(2, this.getCpf());
            stmt.setString(3, this.getEmail());
            stmt.setString(4, this.getTelefone());
            stmt.setString(5, this.getTipo());

            stmt.execute();

            System.out.println("Usuário " + this.getNome() + " enviado para o banco!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }
    

    protected static void validarEntrada(String entrada) throws Exception {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new Exception("O campo não pode estar vazio!");
        }
    }

    protected void ErroDeCriação(String msg) {
        throw new IllegalArgumentException(msg);
    }

    protected String apenasNumeros(String NumerosSujos){
        String apenasNumeros = NumerosSujos.replaceAll("[^0-9]", "");
        return apenasNumeros;
    }

    protected boolean VerificarTelefone(String telefone) {
        String NTelefone = apenasNumeros(telefone);

        if (NTelefone.length() == 13) {
            return true;
        } else {
            ErroDeCriação("Número inválido. Certifique-se de incluir DDI, DDD e 9 dígitos.");
            return false;
        }
    }

    protected String formatarTelefone(String telefone) {
        String NTelefone = apenasNumeros(telefone);

        String formatado = NTelefone.replaceFirst("(\\d{2})(\\d{2})(\\d{5})(\\d{4})", "+$1 ($2) $3-$4");
        return formatado;
    }

    protected String formatarCpf(String cpf){
        String numerosCPF = apenasNumeros(cpf);

        String formatado = numerosCPF.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return formatado;
    }

    protected void validarCpf(String cpfSujo) {
        boolean resultado;

        // Calculo:
        String numerosCPF = apenasNumeros(cpfSujo);

        /*
         * CPFs devem ter 11 dígitos e não podem ser sequências repetida (ex:111.111...)
         */
        if (numerosCPF.length() != 11 || numerosCPF.matches("(\\d)\\1{10}")) {
            System.out.println("O CPF inserido está incorreto, verifique.");
            resultado = false;
        }

        try {
            int[] cpfAray = new int[11];
            for (int i = 0; i < 11; i++) {
                cpfAray[i] = Character.getNumericValue(numerosCPF.charAt(i));
            }

            /*--- CÁLCULO DO PRIMEIRO DÍGITO ---*/
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += cpfAray[i] * peso--;
            }

            int verificador1 = 11 - (soma % 11);
            if (verificador1 > 9) {
                verificador1 = 0;
            }

            /* --- CÁLCULO DO SEGUNDO DÍGITO --- */
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += cpfAray[i] * peso--;
            }

            int verificador2 = 11 - (soma % 11);
            if (verificador2 > 9) {
                verificador2 = 0;
            }

            /*--- VERIFICAÇÃO FINAL --- (Verifica se os dígitos calculados batem com os informados)*/
            resultado = (verificador1 == cpfAray[9] && verificador2 == cpfAray[10]);

        } catch (Exception e) {
            resultado = false;
        }

        if (!resultado) {
            ErroDeCriação("cpf inválido!");
        }
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId_Usuario() {
        return this.id_Usuario;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTipo() {
        return tipo;
    }
    
    public String getTelefone() {
        return this.telefone;
    }

    public String getTelefoneformatado() {
        return formatarTelefone(this.getTelefone());
    }

    public String getCpfformatado() {
        return formatarCpf(this.getCpf());
    }

    protected double calcularMulta() {
        return 0.0;
    }
    //overide nas classes filhas

/* teste 
    public static void main(String[] args) {
        Usuario teste = new Usuario(1, "Andrey", "11414322933", "null email", "5547988001057");

        System.out.println(teste.getId_Usuario());
        System.out.println(teste.getNome());
        System.out.println(teste.getCpf());
        System.out.println(teste.getCpfformatado());
        System.out.println(teste.getEmail());
        System.out.println(teste.getTelefone());
        System.out.println(teste.getTelefoneformatado());
    }
*/

}

// USUARIO: ID, NOME, CPF, “IDADE, E-MAIL, TELEFONE”

