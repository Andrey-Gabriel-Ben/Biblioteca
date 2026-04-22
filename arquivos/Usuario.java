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
    }
}


