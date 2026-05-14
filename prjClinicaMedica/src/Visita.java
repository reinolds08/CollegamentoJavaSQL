public class Visita {
    public String dataOra;
    private String campo;
    private boolean priorita;
    private Persona p;

    public Visita(String dataOra, String campo, boolean priorita, Persona p){
        this.dataOra = dataOra;
        this.campo = campo;
        this.priorita = priorita;
        this.p = p;
    }

    public boolean getPriorita(){
        return priorita;
    }

    public String getDataOra() {
        return dataOra;
    }

    public String getCampo() {
        return campo;
    }

    public Persona getPersona() {
        return p;
    }

    public String toString() {
        return "Visita{" +
                "Data: '" + dataOra + '\n' +
                "Campo: '" + campo + '\n' +
                "Priorita: " + priorita +
                "Paziente: " + p +
                '}';
    }
}
