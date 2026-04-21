import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Usuario {
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

    public void cadastrarUsuario (String Tipo) {
        try (Scanner scanner = new Scanner(System.in);) {
            Usuario novoUsuario = new Usuario(0, null, null, null, null);
            
            // nome
            System.out.print("Digite o nome do usuario: ");
            String nomeImput = scanner.nextLine();
            if (!validarEntrada(nomeImput)){return;}
            novoUsuario.setNome(nomeImput);

            // cpf
            System.out.print("Digite o CPF do usuario: ");
            String cpfimput = scanner.nextLine();
            if (!calcularCpf(cpfimput)){return;}
            cpfimput = apenasNumeros(cpfimput);
            novoUsuario.setCpf(cpfimput);

            // E-mail
            System.out.print("Digite o E-mail do usuario: ");
            String emailImput = scanner.nextLine();
            if (!validarEntrada(emailImput)){return;}
            novoUsuario.setEmail(emailImput);

            // Telefone
            System.out.println("Digite o telefone do usuario contendo DDI, DDD e 9 dígitos.");
            String telefoneImput = scanner.nextLine();
            if(!VerificarTelefone(telefoneImput)){return;}
            telefoneImput = apenasNumeros(telefoneImput);
            novoUsuario.setTelefone(telefoneImput);

            //tipo
            novoUsuario.setTipo(Tipo);

            novoUsuario.sendUserToDatabase();

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
            ErroDeCriação("Erro ao salvar no banco: " + e.getMessage());
        }
    }
    

    protected static boolean  validarEntrada(String entrada) {
        if (entrada == null || entrada.trim().isEmpty()) {
            System.err.println("O campo não pode estar vazio!");
            return false;
        }
        return true;
    }

    protected void ErroDeCriação(String msg) {
        System.err.println(msg);
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

    protected boolean calcularCpf(String cpfSujo) {
        String numerosCPF = apenasNumeros(cpfSujo);

        if (numerosCPF.length() != 11 || numerosCPF.matches("(\\d)\\1{10}")) {
            ErroDeCriação("O CPF inserido está incorreto, verifique.");
            return false;
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
            if (verificador1 == cpfAray[9] && verificador2 == cpfAray[10]) {
                return true;
            } else {
                ErroDeCriação("O CPF inserido está incorreto, verifique.");
                return false;
            }

        } catch (Exception e) {
            ErroDeCriação("O CPF inserido está incorreto, verifique.");
            return false;
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


    public static void main(String[] args) {
        



    }

}

// USUARIO: ID, NOME, CPF, “IDADE, E-MAIL, TELEFONE”
