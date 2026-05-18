import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    // INSERT — aggiunge una nuova persona al database
    public void inserisci(Persona p) {
        //stringa del comando SQL da eseguire
        String sql = "INSERT INTO pazienti (NomePersona) VALUES (?)";

        //mi connetto al database
        try (Connection conn = DbConnection.getConnection();
            //rappresenta la query in SQL, dove '?' sono i campi
            PreparedStatement ps = conn.prepareStatement(sql)) {
            //inserisco il primo '?'
            ps.setString(1, p.getNome());
            //eseguo la query
            ps.executeUpdate();
            //messaggio di riuscita del comando
            System.out.println("Paziente inserito: " + p.getNome());

        } catch (SQLException e) {
            //lancia eccezzione se non riesce a connettersi al DB
            e.printStackTrace();
        }
    }

    // SELECT ALL — restituisce tutte le persone
    public List<Persona> trovaTutti() {
        //creo lista dei record
        List<Persona> lista = new ArrayList<>();
        //stringa del comando SQL da eseguire
        String sql = "SELECT * FROM persona";
        //mi connetto al database
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Persona(
                        //aggiungo record alla lista
                        rs.getString("NomePersona"),
                        rs.getString("id_persona")

                ));
            }

        } catch (SQLException e) {
            //lancia eccezzione se non riesce a connettersi al DB
            e.printStackTrace();
        }

        return lista;
    }

    // SELECT per ID — cerca una persona specifica
    public Persona trovaById(String id) {
        //stringa del comando SQL da eseguire
        String sql = "SELECT * FROM pazienti WHERE id_persona = ?";

        //mi connetto al database
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            //cerca il record con campo 'id'
            ps.setString(1, id);
            //esegue la query
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //ritorna il risultato della query
                return new Persona(
                        rs.getString("NomePersona"),
                        rs.getString("id_persona")
                );
            }

        } catch (SQLException e) {
            //lancia eccezzione se non riesce a connettersi al DB
            e.printStackTrace();
        }

        return null;
    }

    public void aggiornaNome(int id, String nuovoNome) {
        
        //stringa del comando SQL da eseguire 
        String sql = "UPDATE pazienti SET NomePersona = ? WHERE id_persona = ?";

        //mi connetto al database
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //aggiorna il record con il nuovo nome
            ps.setString(1, nuovoNome); 

            //cerca il record con campo 'id'
            ps.setInt(2, id);      

            //esegue la QUERY 
            ps.executeUpdate();

        } catch (SQLException e) {
            //lancia eccezzione se non riesce a connettersi al DB
            e.printStackTrace();
        }
    }

    // DELETE — rimuove una persona per id
    public void elimina(String id) {

        //stringa del comando SQL da eseguire
        String sql = "DELETE FROM pazienti WHERE id_persona = ?";

        //mi connetto al database
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //elimina il record con campo 'id'
            ps.setString(1, id);
            
            //eseque la QUERY
            ps.executeUpdate();

        } catch (SQLException e) {

            //lancia eccezzione se non riesce a connettersi al DB
            e.printStackTrace();
        }
    }
}
