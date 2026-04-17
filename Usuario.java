
public /* abstract */ class Usuario {
    int id_Usuario;
    String nome;
    String cpf;
    String email; // unique
    String telefone; // fazer tratamento

    private void validarCpf(String cpf) {
        if (!CalcularCpf(cpf)) {
            throw new IllegalArgumentException("cpf inválido!");
        }
    }

    private boolean CalcularCpf(String cpfSujo) {
        String numerosCPF = cpfSujo.replaceAll("\\D", "");

        // CPFs devem ter 11 dígitos e não podem ser sequências repetidas
        // (ex:111.111...)
        if (numerosCPF.length() != 11 || numerosCPF.matches("(\\d)\\1{10}")) {
            System.out.println("O CPF inserido está incorreto, verifique.");
            return false;
        }

        try {
            int[] cpfAray = new int[11];
            for (int i = 0; i < 11; i++) {
                cpfAray[i] = Character.getNumericValue(numerosCPF.charAt(i));
            }

            // --- CÁLCULO DO PRIMEIRO DÍGITO ---
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += cpfAray[i] * peso--;
            }

            int verificador1 = 11 - (soma % 11);
            if (verificador1 > 9) {
                verificador1 = 0;
            }

            // --- CÁLCULO DO SEGUNDO DÍGITO ---
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += cpfAray[i] * peso--;
            }

            int verificador2 = 11 - (soma % 11);
            if (verificador2 > 9) {
                verificador2 = 0;
            }

            // --- VERIFICAÇÃO FINAL --- (Verifica se os dígitos calculados batem com os
            // informados)
            return (verificador1 == cpfAray[9] && verificador2 == cpfAray[10]);

        } catch (Exception e) {
            return false;
        }
    }

}

// USUARIO: ID, NOME, CPF, “IDADE, E-MAIL, TELEFONE”

// metodo para calculo de multa