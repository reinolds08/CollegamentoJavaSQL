import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    // INSERT — aggiunge una nuova persona al database
    public void inserisci(Persona p) {
        String sql = "INSERT INTO persona (NomePersona) VALUES (?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.executeUpdate();
            System.out.println("Persona inserita: " + p.getNome());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // SELECT ALL — restituisce tutte le persone
    public List<Persona> trovaTutti() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM persona";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getString("NomePersona"),
                        rs.getString("id_persona")

                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // SELECT per ID — cerca una persona specifica
    public Persona trovaById(String id) {
        String sql = "SELECT * FROM persona WHERE id_persona = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Persona(
                        rs.getString("NomePersona"),
                        rs.getString("id_persona")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void aggiornaNome(int id, String nuovoNome) {
        String sql = "UPDATE persona SET NomePersona = ? WHERE id_persona = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuovoNome); // primo ? → nuovo nome
            ps.setInt(2, id);           // secondo ? → id della persona
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE — rimuove una persona per id
    public void elimina(String id) {
        String sql = "DELETE FROM persona WHERE id_persona = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}