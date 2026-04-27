import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final  Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("arquivos/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("Erro ao carregar configurações do banco: " + e.getMessage());
            System.out.println("Professor, verifica se você instalou o arquivo que te mandei no teans e deixou na pasta 'arquivos'");
        }
    }

    

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL não encontrado. Verifique se adicionou o .jar ao projeto.", e);
        }

        return DriverManager.getConnection(
            props.getProperty("db.URL"),
            props.getProperty("db.USUARIO"),
            props.getProperty("db.SENHA")
        );
    }

    public static void main(String[] args) {
        System.out.println("--- Iniciando teste de conexão com Supabase ---");

        try (Connection conexao = ConexaoBanco.getConnection()) {
            if (conexao != null) {
                System.out.println("SUCESSO: O Java conseguiu falar com o PostgreSQL no Supabase!");
                System.out.println("Status: Pronto para gerenciar a Biblioteca na nuvem.");
            }
        } catch (SQLException e) {
            System.err.println("FALHA: A conexão falhou!");
            System.err.println("Causa do erro: " + e.getMessage());

            // Dicas específicas para PostgreSQL/Supabase
            if (e.getMessage().contains("password authentication failed")) {
                System.err.println("DICA: A senha do banco está incorreta.");
            } else if (e.getMessage().contains("Connection refused")) {
                System.err.println("DICA: Verifique se a URL está correta ou se sua internet permite conexões na porta 5432.");
            }
        }
    }
}