import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Bibliotecario extends Usuario {

    public Bibliotecario(int id_Usuario, String nome, String cpf, String email, String telefone) {
        super(id_Usuario, nome, cpf, email, telefone);
        this.tipo = "Bibliotecario";
    }

    // metodos de cadastro E UPLOAD
    //Cadastro e Upload de Usuarios
    private boolean cadastrarUsuario(Scanner scanner, String Tipo) {
        Usuario novoUsuario = new Usuario(0, null, null, null, null);

        // nome
        System.out.println("\nDigite o nome do usuario: ");
        String nomeImput = scanner.nextLine();
        if (!validarEntrada(nomeImput)) {
            return false;
        }
        novoUsuario.setNome(nomeImput);

        // cpf
        System.out.println("\nDigite o CPF do usuario: ");
        String cpfimput = scanner.nextLine();
        if (!calcularCpf(cpfimput)) {
            return false;
        }
        cpfimput = apenasNumeros(cpfimput);
        novoUsuario.setCpf(cpfimput);

        // E-mail
        System.out.println("\nDigite o E-mail do usuario: ");
        String emailImput = scanner.nextLine();
        if (!validarEntrada(emailImput)) {
            return false;
        }
        novoUsuario.setEmail(emailImput);

        // Telefone
        System.out.println("\nDigite o telefone do usuario contendo DDI, DDD e 9 dígitos.");
        String telefoneImput = scanner.nextLine();
        if (!VerificarTelefone(telefoneImput)) {
            return false;
        }
        telefoneImput = apenasNumeros(telefoneImput);
        novoUsuario.setTelefone(telefoneImput);

        // tipo
        novoUsuario.setTipo(Tipo);

        sendUserToDatabase(novoUsuario);

        return true;
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

            System.out.println("\nUsuário " + usuario.getNome() + " enviado para o banco!");

        } catch (SQLException e) {
            System.err.println("\n--- AVISO DE CADASTRO ---");
            switch (e.getErrorCode()) {
                case 1062 -> {
                    String mensagem = e.getMessage();

                    if (mensagem.contains("cpf")) {
                        System.err.println("\nErro: Já existe um usuário cadastrado com este CPF.");
                        return;
                    }

                    if (mensagem.contains("email")) {
                        System.err.println("\nErro: Já existe um cadastro com esse E-mail");
                        return;
                    }

                    System.err.println("\nO usuario já existe no sistema.");
                }
                default -> {
                    System.err.println("\nErro técnico ao salvar: " + e.getMessage());
                }
            }
        }
    }

    //Cadastro e Upload de livros
    private boolean cadastrarlivro(Scanner scanner) {
        Livro novoLivro = new Livro(0, null, null, 0, 0, null);

        // título
        System.out.println("\nDigite o título do livro: ");
        String tituloImput = scanner.nextLine();
        if (!validarEntrada(tituloImput)) {
            return false;
        }
        novoLivro.setTitulo(tituloImput);

        // Autor
        System.out.println("\nDigite o nome do autor do livro: ");
        String autorImput = scanner.nextLine();
        if (!validarEntrada(autorImput)) {
            return false;
        }
        novoLivro.setAutor(autorImput);

        // ano_lançamento
        System.out.println("\nDigite o ano de lançamento do livro: ");
        String anoImput = scanner.nextLine();
        int anoInt = converterAno(anoImput);
        if (anoInt == 0) {
            return false;
        }
        novoLivro.setAno_lançamento(anoInt);

        // genero
        System.out.println("\nDigite o nome da genero do livro: ");
        String generoImput = scanner.nextLine();
        Genero generoLivro = new Genero(0, generoImput);
        novoLivro.setId_genero(generoLivro.buscarIdPorNome(generoImput));

        // isbn
        System.out.println("\nDigite o nº do isbn do livro: ");
        String isbnImput = scanner.nextLine();
        if (!novoLivro.calculaisbn(isbnImput)) {
            return false;
        }
        novoLivro.setIsbn(isbnImput);

        sendBookToDatabase(novoLivro);

        return true;
    }

    private void sendBookToDatabase(Livro nvLivro) {
        String insert = "INSERT INTO LIVRO (titulo, autor, ano, isbn, ID_GENERO) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setString(1, nvLivro.getTitulo());
            stmt.setString(2, nvLivro.getAutor());
            stmt.setInt(3, nvLivro.getAno_lançameto());
            stmt.setString(4, nvLivro.getIsbn());
            stmt.setInt(5, nvLivro.getId_genero());

            stmt.execute();

            System.out.println("\nO livro " + nvLivro.getTitulo() + " foi enviado para o banco!");

        } catch (SQLException e) {
            switch (e.getErrorCode()) {
                case 1062 -> {
                    System.err.println("\n--- AVISO DE CADASTRO ---");
                    System.err.println("\nO ISBN [" + nvLivro.getIsbn() + "] já existe no sistema.");
                }
                default -> {
                    System.err.println("\nErro técnico ao salvar: " + e.getMessage());
                }
            }
        }
    }

    //Cadastro e Upload de exemplares
    private boolean cadastrarExemplar(Scanner scanner) {

        Exemplar novoExemplar = new Exemplar(-1, -1, null, "Disponivel");

        // id-Livro
        System.out.println("\nDigite o nome do livro: ");
        String nomeImput = scanner.nextLine();
        Livro livroid = new Livro(0, nomeImput, null, 0, 0, null);
        int id_livro = livroid.buscarIdPorNomeLivro(nomeImput);
        if (id_livro <= 0) {
            return false;
        }
        novoExemplar.setId_livro(id_livro);

        // aquisição
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataHojeTexto = hoje.format(formatador);
        novoExemplar.setAquisição(dataHojeTexto);

        sendCopyToDatabase(novoExemplar);

        return true;

    }

    private void sendCopyToDatabase(Exemplar nvExemplar) {
        String insert = "insert into exemplar (ID_LIVRO, aquisição, status) values (?, STR_TO_DATE(?, '%d/%m/%Y'), ?)";

        try (Connection conn = ConexaoBanco.getConnection(); PreparedStatement stmt = conn.prepareStatement(insert)) {

            stmt.setInt(1, nvExemplar.getId_livro());
            stmt.setString(2, nvExemplar.getAquisição());
            stmt.setString(3, nvExemplar.getStatus());

            stmt.execute();

            System.out.println("\nO novo exemplar foi enviado para o banco!");

        } catch (SQLException e) {
            System.err.println("\nErro técnico ao salvar: " + e.getMessage());
        }
    }

    //Cadastro e Upload de emprestimos
    private boolean cadastrarEmprestimo(Scanner scanner, int diasParaDevolver) {

        Emprestimos nvEmprestimo = new Emprestimos(0, 0, 0, null, null, null, null);

        // id_Usuario
        System.out.println("\nDigite o nome do usuario: ");
        String nomeImput = scanner.nextLine();
        int id_usuario = buscarIdPorNome(nomeImput);
        if (id_usuario <= 0) {return false;}
        nvEmprestimo.setId_usuario(id_usuario);

        // id_exemplar disponivel
        System.out.println("\nDigite o título do Livro desejado: ");
        String titulo = scanner.nextLine();
        Livro lv = new Livro(0, null, null, 0, 0, null);
        int id_livro = lv.buscarIdPorNomeLivro(titulo);
        Exemplar exemplar = new Exemplar(0, 0, null, null);
        int id_exemplar = exemplar.buscarIdDisponivelPorIdLivro(id_livro);
        if (id_exemplar <= 0) {return false;}
        nvEmprestimo.setId_exemplar(id_exemplar);

        // data emprestimo
        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String hojeTexto = hoje.format(formatador);
        nvEmprestimo.setData_Emprestimo(hojeTexto);

        // data devolução
        LocalDate devolução = hoje.plusDays(diasParaDevolver);
        String devoluçãoTexto = devolução.format(formatador);
        nvEmprestimo.setData_Devolução(devoluçãoTexto);
        System.out.println("O livro deverá ser devolvido até dia " + devoluçãoTexto + " caso cotrario será cobrado multa");


        //status
        nvEmprestimo.setStatus("PENDENTE");

        sendEmprestimoToDatabase(nvEmprestimo);

        return true;
    }

    private void sendEmprestimoToDatabase(Emprestimos emprestimo) {
        //registrar novo emprestimo
        String insert = "insert into emprestimo (ID_USUARIO, ID_EXEMPLAR, DATA_EMPRESTIMO, DATA_DEVOLUCAO, status) values(?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?);";

        //Alterar status do exemplar
        String update = "update exemplar set status = 'INDISPONIVEL' WHERE id_exemplar = ?;";

        try (Connection conn = ConexaoBanco.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(insert); PreparedStatement stmt2 = conn.prepareStatement(update)) {
                stmt1.setInt(1, emprestimo.getId_usuario());
                stmt1.setInt(2, emprestimo.getId_exemplar());
                stmt1.setString(3, emprestimo.getData_Emprestimo());
                stmt1.setString(4, emprestimo.getData_Devolução());
                stmt1.setString(5, emprestimo.getStatus());

                stmt1.execute();

                stmt2.setInt(1, emprestimo.getId_exemplar());
                stmt2.execute();

                conn.commit();

                System.out.println("\nO novo Emprestimo foi cadastrado para o banco!");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            

        } catch (SQLException e) {
            System.err.println("\nErro técnico ao salvar: " + e.getMessage());
        }
    }

    //devoluções

    private boolean devolverLivro(Scanner scanner){

        Emprestimos dv = new Emprestimos(0, 0, 0, null, null, null, null);

        // id_Usuario
        System.out.println("\nDigite o nome do usuario: ");
        String nomeImput = scanner.nextLine();
        int id_usuario = buscarIdPorNome(nomeImput);
        if (id_usuario <= 0) {return false;}
        dv.setId_usuario(id_usuario);

        // id emprestimo, id exemplar e data de devolução
        System.out.println("\nDigite o título do livro a ser devolvido: ");
        String titulo = scanner.nextLine();
        dv.setarParaDevolucao(id_usuario, titulo);
        if (dv.getId_emprestimo() <= 0 || dv.getId_exemplar() <= 0 || dv.getData_Devolução() == null){return false;}

        //calculo de multa
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");

        LocalDate hj = LocalDate.now();
        LocalDate devolução = LocalDate.parse(dv.getData_Devolução(), formatter);
        Long diasDeAtraso = ChronoUnit.DAYS.between(hj, devolução);

        System.out.println(dv.getData_Devolução());
        System.out.println(diasDeAtraso);

        return true;
    }




    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        
        Bibliotecario Bibliotecario = new Bibliotecario(001, "Jujite", null, null, null);

        Bibliotecario.devolverLivro(scanner);

        System.out.println("lets the game beguns");
    }

}
