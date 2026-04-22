import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Bibliotecario extends Usuario {
    
    public Bibliotecario(int id_Usuario, String nome, String cpf, String email, String telefone){
        super(id_Usuario, nome, cpf, email, telefone);
        this.tipo = "Bibliotecario";
    }

// metodos de cadastro

    private void cadastrarUsuario (String Tipo) {
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

            sendUserToDatabase(novoUsuario);

        } catch (Exception e) {
            System.out.println("Devido ao erro, usuario não foi criado, por favor tente novamente\n");
        }
    }

    private void sendUserToDatabase(Usuario usuario) {
        String insert = "INSERT INTO usuarios (nome, cpf, email, telefone, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getCpf());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getTipo());

            stmt.execute();

            System.out.println("Usuário " + usuario.getNome() + " enviado para o banco!");

        } catch (SQLException e) {
            System.err.println("\n--- AVISO DE CADASTRO ---");
            switch (e.getErrorCode()) {
                case 1062 -> {
                    String mensagem = e.getMessage();

                    if (mensagem.contains("cpf")) {
                        System.err.println("Erro: Já existe um usuário cadastrado com este CPF.");
                        return;
                    } 

                    if (mensagem.contains("email")) {
                        System.err.println("Erro: Já existe um cadastro com esse E-mail");
                        return;
                    } 

                    System.err.println("O usuario já existe no sistema.");
                }
                default -> {
                    System.err.println("Erro técnico ao salvar: " + e.getMessage());
                }
            }
        }
    }

    private void cadastrarlivro () {
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
            Genero generoLivro = new Genero(0, generoImput);
            generoLivro.buscarIdPorNome(generoImput);
            if (generoLivro.getId_genero() <= 0) {return;}
            novoLivro.setId_genero(generoLivro.getId_genero());

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

    private void sendBookToDatabase(Livro nvLivro){
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
            switch (e.getErrorCode()) {
                case 1062 -> {
                    System.err.println("\n--- AVISO DE CADASTRO ---");
                    System.err.println("O ISBN [" + nvLivro.getIsbn() + "] já existe no sistema.");
                }
                default -> {
                    System.err.println("Erro técnico ao salvar: " + e.getMessage());
                }
            }
        }
    }
    
    private void cadastrarExemplar () {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);) {
            Exemplar novoExemplar = new Exemplar(-1, -1, null, "Disponivel");
           
            // id-Livro
            System.out.println("\nDigite o nome do livro: ");
            String nomeImput = scanner.nextLine();
            if (!validarEntrada(nomeImput)){return;}
            novoExemplar.buscarIdPorNomeLivro(nomeImput);
            //setter na função

            //aquisição
            LocalDate hoje = LocalDate.now();
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataHojeTexto = hoje.format(formatador);            
            novoExemplar.setAquisição(dataHojeTexto);

            sendCopyToDatabase(novoExemplar);
        } catch (Exception e) {
            System.out.println("\nDevido ao erro, usuario não foi criado, por favor tente novamente\n");
        }
    }

    private void sendCopyToDatabase(Exemplar nvExemplar){
        String insert = "insert into exemplar (ID_LIVRO, aquisição, status) values (?, STR_TO_DATE(?, '%d/%m/%Y'), ?)";

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(insert)) {
            
            stmt.setInt(1, nvExemplar.getId_livro());
            stmt.setString(2, nvExemplar.getAquisição());
            stmt.setString(3, nvExemplar.getStatus());

            stmt.execute();

            System.out.println("\nO novo exemplar foi enviado para o banco!");

        } catch (SQLException e) {
            System.err.println("Erro técnico ao salvar: " + e.getMessage());
        }
    }

    //private

    public static void main(String[] args) {
        Bibliotecario Bibliotecario = new Bibliotecario(001, "Jujite", null, null, null);
        
        Bibliotecario.cadastrarExemplar();
    }


}
