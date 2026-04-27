import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ByteBook {

    public static void exibirInterfaçe(Scanner scanner, Bibliotecario Gestor) {
        int opcao;

        do {
            System.out.println("===== BYTEBOOK - SISTEMA DE GERENCIAMENTO=====");
            System.out.println("Seja bem-vindo, o que deseja fazer agora? \n");

            System.out.println("1-Cadastrar novo livro");
            System.out.println("2-Cadastrar novo exemplar");
            System.out.println("3-Cadastrar Usuário");
            System.out.println("4-Emprestar Livro");
            System.out.println("5-Devolver Livro");
            System.out.println("6-Listar Livros");
            System.out.println("0-Sair");

            System.out.print("\n Escolha uma opcao: ");
            String entradaOpcao = scanner.nextLine().trim();

            try {
                opcao = Integer.parseInt(entradaOpcao);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite apenas números.");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.cadastrarlivro(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 2 -> {
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.cadastrarExemplar(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 3 ->{
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.cadastrarUsuario(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 4 -> {
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.cadastrarEmprestimo(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 5 -> {
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.devolverLivro(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 6 -> {
                    boolean sucesso = false;
                    while (!sucesso) {
                        sucesso = Gestor.listarLivrosDisponiveis(scanner);
                        if (!sucesso) {
                            System.out.println("Você Gostaria de tentar novamente? (s/n)");
                            String resposta = scanner.nextLine();
                            if (!resposta.equalsIgnoreCase("s")) {
                                System.out.println("Retornando ao menu principal...");
                                sucesso = true;
                            }
                        }
                    }
                }

                case 0 -> {
                    System.out.println("obrigado e volte sempre");
                }

                default -> {
                    System.out.print("\n Opção inválida, por favor tente novamente\n ");
                }
            }

        } while (opcao != 0);
    }

    // main oficial
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            Bibliotecario Gestor = new Bibliotecario(000, "MR. Robinson", null, null, null);

            exibirInterfaçe(scanner, Gestor);
        }

        

    }
}