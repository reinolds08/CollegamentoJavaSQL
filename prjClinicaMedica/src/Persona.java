public class Persona {
    private String nome;
    private String id;

    public Persona(String nome, String id){
        this.nome = nome;
        this.id = id;
    }

    // Costruttore solo con nome (per INSERT se id è AUTO_INCREMENT)
    public Persona(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public int getId()   { return Integer.parseInt(id); }

    @Override
    public String toString() {
        return "\nNome: " + nome + "\nId: " + id;
    }
}