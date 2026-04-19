import java.util.Scanner;

public /* abstract */ class Usuario {
    int id_Usuario;
    String nome;
    String cpf;
    String email; // unique
    String telefone; // fazer tratamento


    public Usuario() {

        try (Scanner scanner = new Scanner(System.in)){
            // nome
            System.out.print("Digite o nome do usuario: ");
            String nomeImput = scanner.next();
            validarEntrada(nomeImput);
            this.nome = nomeImput;

            // cpf
            System.out.print("Digite o CPF do usuario: ");
            String cpfimput = scanner.next();
            validarCpf(cpfimput);
            this.cpf = cpfimput;

            // E-mail
            System.out.print("Digite o E-mail do usuario: ");
            String emailImput = scanner.next();
            validarEntrada(nomeImput);
            this.email = emailImput;

            // Telefone
            System.out.println("Digite o telefone do usuario contendo DDI, DDD e 9 dígitos.");
            scanner.nextLine();
            String telefoneImput = scanner.nextLine();
            VerificarTelefone(telefoneImput);
            String telefoneFormatado = formatarTelefone(telefoneImput);
            this.telefone = telefoneFormatado;

            // id do usuario
        } catch (Exception e) {
            System.out.println("Devido ao erro, usuario não foi criado, por favor tente novamente\n");
        }
        
    }

    public static void validarEntrada(String entrada) throws Exception {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new Exception("O campo não pode estar vazio!");
        }
    }


    private void ErroDeCriação (String msg){
        throw new IllegalArgumentException(msg);
    }

    private boolean VerificarTelefone(String telefone){
        // Limpeza:
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");

        // Validação
        if (apenasNumeros.length() == 13){return true;} else {
            ErroDeCriação("Número inválido. Certifique-se de incluir DDI, DDD e 9 dígitos.");
            return false;
        }
    }

    private String formatarTelefone(String telefone) {
        // Limpeza:
        String apenasNumeros = telefone.replaceAll("[^0-9]", "");

        String formatado = apenasNumeros.replaceFirst("(\\d{2})(\\d{2})(\\d{5})(\\d{4})", "+$1 ($2) $3-$4");           
        return formatado;
    }

    private void validarCpf(String cpfSujo) {
        boolean resultado;

        // Calculo:
        String numerosCPF = cpfSujo.replaceAll("\\D", "");

        /*CPFs devem ter 11 dígitos e não podem ser sequências repetida (ex:111.111...)*/
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

    public String getTelefone() {
        return this.telefone;
    }

    public static void main(String[] args) {
        Usuario teste = new Usuario();
/*
        System.out.println(teste.getId_Usuario());
        System.out.println(teste.getNome());
        System.out.println(teste.getCpf());
        System.out.println(teste.getEmail());
        System.out.println(teste.getTelefone());
*/

        System.out.println("and it is begin");
    }

}

// USUARIO: ID, NOME, CPF, “IDADE, E-MAIL, TELEFONE”

// metodo para calculo de multa