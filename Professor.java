public class Professor extends Usuario {
    
    @Override
    protected double calcularMulta(){
        return 0.0;
    }




    public static void main(String[] args) {
        Usuario teste = new Professor();
    }
}
