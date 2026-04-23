public class Exemplar {
    private int id_exemplar;
    private int id_livro;
    private String aquisição;
    private String Status;

    public Exemplar(int id_exemplar, int id_livro, String aquisição, String Status){
        this.id_exemplar = id_exemplar;
        this.id_livro = id_livro;
        this.aquisição = aquisição;
        this.Status = Status;
    }


    public void setId_exemplar(int id_exemplar) {
        this.id_exemplar = id_exemplar;
    }

    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }

    public void setAquisição(String aquisição) {
        this.aquisição = aquisição;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getId_exemplar() {
        return this.id_exemplar;
    }

    public int getId_livro() {
        return this.id_livro;
    }

    public String getAquisição() {
        return this.aquisição;
    }

    public String getStatus() {
        return this.Status;
    }

    
}
