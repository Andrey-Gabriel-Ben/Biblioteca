import java.nio.charset.StandardCharsets;
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

// classes de cadastro

    public void cadastrarUsuario (String Tipo) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);) {
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
    
    public void cadastrarlivro () {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);) {
            Livro novoLivro = new Livro(0, null, null, 0, 0, null);
           
            // título
            System.out.println("\nDigite o título do livro: ");
            String tituloImput = scanner.nextLine();
            if (!validarEntrada(tituloImput)){return;}
            novoLivro.setTitulo(tituloImput);

            //Autor
            System.out.println("\nDigite o nome do autor do livro: ");
            String autorImput = scanner.nextLine();
            if (!validarEntrada(autorImput)){return;}
            novoLivro.setAutor(autorImput);

            //ano
            System.out.println("\nDigite o ano de lançamento do livro: ");
            int anoImput = scanner.nextInt();
            if (!verificarAno(anoImput)){return;}
            novoLivro.setAno(anoImput);
            scanner.nextLine();

            //genero
            System.out.println("\nDigite o nome da genero do livro: ");
            String generoImput = scanner.nextLine();
            if (!validarEntrada(generoImput)){return;}
            Genero catLivro = new Genero(0, generoImput);
            catLivro.buscarIdPorNome(generoImput);
            if (catLivro.getId_genero() <= 0) {return;}
            novoLivro.setId_genero(catLivro.getId_genero());

            //isbn
            System.out.println("\nDigite o nº do isbn do livro: ");
            String isbnImput = scanner.nextLine();
            if (!calculaisbn(isbnImput)){return;}
            novoLivro.setIsbn(isbnImput);

            sendBookToDatabase(novoLivro);

        } catch (Exception e) {
            System.out.println("\nDevido ao erro, usuario não foi criado, por favor tente novamente\n");
        }
    }

    protected void sendBookToDatabase(Livro nvLivro){
        String insert = "INSERT INTO LIVRO (titulo, autor, ano, isbn, ID_GENERO) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, nvLivro.getTitulo());
            stmt.setString(2, nvLivro.getAutor());
            stmt.setInt(3, nvLivro.getAno());
            stmt.setString(4, nvLivro.getIsbn());
            stmt.setInt(5, nvLivro.getId_genero());

            stmt.execute();

            System.out.println("\nO livro " + nvLivro.getTitulo() + " foi enviado para o banco!");

        } catch (SQLException e) {
            ErroDeCriação("Erro ao salvar no banco: " + e.getMessage());
        }
    }

//verificações e calculos

    protected boolean calculaisbn(String isbnSujo) {
        String isbnLimpo = apenasNumeros(isbnSujo);

        if (isbnLimpo.length() != 13 || isbnLimpo.matches("(\\d)\\1{10}")){
            ErroDeCriação("O isbn inserido é invalido ou desatualizado");
            return false;
        }

        try {
            
            //transforma em array
            int[] isbnAray = new int[13];
            for (int i = 0; i < 13; i++) {
                isbnAray[i] = Character.getNumericValue(isbnLimpo.charAt(i));
            }

            //calcula:
            //soma multiplicações
            int soma = 0;
            int peso;
            for (int i = 0; i < 12; i++) {
                if((i+1)%2 == 0) {peso = 3;} else {peso = 1;}
                soma += isbnAray[i] * peso--;
            }

            //divide
            int verificador = 10 - (soma%10);

            //verifica
            if (verificador == isbnAray[12]) {
                return true;
            } else {
                ErroDeCriação("O isbn inserido está incorreto, verifique.");
                return false;
            }
            
        } catch (Exception e) {
            ErroDeCriação("O isbn inserido é invalido ou desatualizado");
            return false;
        }

    }

    protected boolean  verificarAno(int ano) {
        if (ano >= 1000 && ano <= 9999) {
            return true;
        } else {
            ErroDeCriação("Formato de ano adicionado incompativel, tente novamente");
            return false;
        }
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

//utils
    protected void ErroDeCriação(String msg) {
        System.err.println(msg);
    }

    protected String apenasNumeros(String NumerosSujos){
        String apenasNumeros = NumerosSujos.replaceAll("[^0-9]", "");
        return apenasNumeros;
    }
    
    protected static boolean  validarEntrada(String entrada) {
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

    protected String formatarCpf(String cpf){
        String numerosCPF = apenasNumeros(cpf);

        String formatado = numerosCPF.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        return formatado;
    }

//metodos necessarios
    protected double calcularMulta() {
        return 0.0;
    }
    //overide nas classes filhas

//gets e sets
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

// main teste

    public static void main(String[] args) {
        Usuario teste = new Usuario(001, "Mr. Bibliotecario", null, null, null);

        teste.cadastrarlivro();

        System.out.println("your code still alive");
    }
}


