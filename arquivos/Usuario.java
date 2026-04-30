import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        this.tipo = null;
    }

    // buscas
    protected int buscarIdPorCpf(String cpf) {
        String select = "select ID_USUARIO from usuarios where cpf = ?;";
        int idEncontrado = -1;

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(select)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idEncontrado = rs.getInt("ID_USUARIO");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar Usuario: " + e.getMessage());
        }

        return idEncontrado;

    }

    // verificações e calculos

    protected int converterAno(String ano) {
        int anoInt = 0;

        try {
            if (ano.contains("ac") || ano.contains("AC")) {
                anoInt = Integer.parseInt(ano) * (-1);
            } else {
                anoInt = Integer.parseInt(ano);
            }

        } catch (NumberFormatException e) {
            System.err.println("Ano inserido invalido, verifique e tente novamente");

        } 
        
        return anoInt;
    }

    protected boolean VerificarTelefone(String telefone) {
        String NTelefone = apenasNumeros(telefone);

        if (NTelefone.length() == 13) {
            return true;
        } else {
            System.err.println("Número inválido. Certifique-se de incluir DDI, DDD e 9 dígitos.");
            return false;
        }
    }

    protected boolean calcularCpf(String cpfSujo) {
        String numerosCPF = apenasNumeros(cpfSujo);

        if (numerosCPF.length() != 11 || numerosCPF.matches("(\\d)\\1{10}")) {
            System.err.println("O CPF inserido está incorreto, verifique.");
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
                System.err.println("O CPF inserido está incorreto, verifique.");
                return false;
            }

        } catch (Exception e) {
            System.err.println("O CPF inserido está incorreto, verifique.");
            return false;
        }

    }

    // utils

    protected String apenasNumeros(String NumerosSujos) {
        String apenasNumeros = NumerosSujos.replaceAll("[^0-9]", "");
        return apenasNumeros;
    }

    protected static boolean validarEntrada(String entrada) {
        if (entrada == null || entrada.trim().isEmpty()) {
            System.err.println("O campo não pode estar vazio!");
            return false;
        }
        return true;
    }

    // formatações
    protected String formatarTelefone(String telefone) {
        String NTelefone = apenasNumeros(telefone);

        String formatado = NTelefone.replaceFirst("(\\d{2})(\\d{2})(\\d{5})(\\d{4})", "+$1 ($2) $3-$4");
        return formatado;
    }

    protected String formatarCpf(String cpf) {
        String numerosCPF = apenasNumeros(cpf);

        String formatado = numerosCPF.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return formatado;
    }

    // metodos necessarios
    protected double calcularMulta() {
        return 0.0;
    }
    // overide nas classes filhas

    // gets e sets
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


    public static void main(String[] args) {
        Usuario tst = new Usuario(0, null, null, null, null);

        int id = tst.buscarIdPorCpf("11414322933");

        System.out.println(id);

    }

}
