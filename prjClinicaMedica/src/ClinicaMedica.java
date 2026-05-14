import java.util.*;

public class ClinicaMedica {
    private Queue<Visita> visite;
    private Deque<String> record;
    private Queue<Visita> urgenti;
    private ArrayList<Visita> visiteProcessate;
    private ArrayList<Persona> pazienti;
    private int maxRecord;

    public ClinicaMedica(int maxRecord){
        this.visite = new ArrayDeque<>();
        this.record = new ArrayDeque<>();
        this.urgenti = new ArrayDeque<>();
        this.visiteProcessate = new ArrayList<>();
        this.pazienti = new ArrayList<>();
        this.maxRecord = maxRecord;
    }

    public void addPaziente(Persona p){
        if(!pazienti.contains(p)) {
            pazienti.add(p);
            aggiungiRecord("Aggiunta di un paziente");

        } else {
            throw new PazientiExceptions("Il paziente è già stato inserito");
        }
    }

    public void addVisita(Visita v){
        if(v.getPriorita()){
            urgenti.add(v);
        }else {
            visite.add(v);
        }
        aggiungiRecord("Visita aggiunta correttamente");
    }

    public void removeVisita(Visita v){
        if (!visiteProcessate.contains(v)) {
            if (visite.contains(v)) {
                visite.remove(v);
            } else {
                urgenti.remove(v);
            }
            aggiungiRecord("Rimozione avvenuta con successo");
        } else {
            throw new VisitaProcessataException("Visita già processata");
        }
    }

    public void aggiungiRecord(String mes){
        if(record.size() <= maxRecord){
            record.add(mes);
        }else{
            record.removeFirst();
            record.add(mes);
        }
    }

    public Visita processaVisita() {
        Visita temp;

        if (!urgenti.isEmpty()) {
            temp = urgenti.poll();
            visiteProcessate.add(temp);
            aggiungiRecord("Visita urgente processata");
            return temp;
        } else if (!visite.isEmpty()) {
            temp = visite.poll();
            visiteProcessate.add(temp);
            aggiungiRecord("Visita processata");
            return temp;
        }

        return null;
    }

    public String recordToString(){
        return record.toString();
    }

    public String personeToString(){
        String str = "";

        for(int i = 0; i < pazienti.size(); i ++){
            str += pazienti.get(i).toString();
        }

        return str;
    }

    public String visiteProcessateToString(){
        String str = "";
        for(int i = 0; i < visiteProcessate.size(); i++){
            visiteProcessate.get(i).toString();
        }

        return str;
    }

    public ArrayList<Persona> getPazienti(){
        return pazienti;
    }

    public String visiteInCodaToString() {
        return visite.toString();
    }

    public Queue<Visita> getUrgenti() {
        return urgenti;
    }

    public Queue<Visita> getVisite() {
        return visite;
    }
}