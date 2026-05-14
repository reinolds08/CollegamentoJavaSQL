import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VisitaDAO {

    // INSERT — aggiunge una nuova visita (usa p.getId() come FK)
    public void inserisci(Visita v) {
        String sql = "INSERT INTO pazienti (dataOraVisita, TipoVisita, priorità, Paziente) VALUES (?, ?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getDataOra());
            ps.setString(2, v.getCampo());
            ps.setBoolean(3, v.getPriorita());
            ps.setInt(4, v.getPersona().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Visita> trovaTutti() {
        List<Visita> lista = new ArrayList<>();
        String sql = "SELECT * FROM prenotazioni " +
                "INNER JOIN pazienti ON prenotazioni.Paziente = pazienti.id_persona";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("nome"),
                        String.valueOf(rs.getInt("id")) // int del db → String Java
                );
                Visita v = new Visita(
                        rs.getString("dataOraVisita"),
                        rs.getString("TipoVisita"),
                        rs.getBoolean("priorità"),
                        p
                );
                lista.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // SELECT per persona — tutte le visite di una persona specifica
    public List<Visita> trovaPerPersona(int idPersona) {
        List<Visita> lista = new ArrayList<>();
        String sql = "SELECT v.data, v.ora, v.campo, v.priorità, " +
                "p.id, p.nome " +
                "FROM prenotazioni v " +
                "JOIN pazienti p ON v.id_persona = p.id " +
                "WHERE p.id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPersona);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("nome"),
                        String.valueOf(rs.getInt("id")) // int del db → String Java
                );
                lista.add(new Visita(
                        rs.getString("dataOraVisita"),
                        rs.getString("TipoVisita"),
                        rs.getBoolean("priorità"),
                        p
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // DELETE — elimina una visita per data e id persona
    public void elimina(String data, int idPersona) {
        String sql = "DELETE FROM prenotazioni WHERE data = ? AND id_persona = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, data);
            ps.setInt(2, idPersona);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void processaProssimaVisita() {
        String selectSql =
                "SELECT id_visita, dataOraVisita, TipoVisita, `Priorità`, Paziente " +
                        "FROM prenotazioni " +
                        "ORDER BY Priorità DESC, dataOraVisita ASC " +
                        "LIMIT 1";

        String insertRecord =
                "INSERT INTO visite_effettuate (dataOraVisita, TipoVisita, Priorità, Paziente) " +
                        "SELECT dataOraVisita, TipoVisita, `Priorità`, Paziente " +
                        "FROM prenotazioni WHERE id_visita = ?";

        String deleteVisita =
                "DELETE FROM prenotazioni WHERE id_visita = ?";

        Connection conn = null;

        try {
            conn = DbConnection.getConnection();
            conn.setAutoCommit(false);

            int idVisita = -1;
            String dataOra = null;
            String tipo = null;
            boolean priorita = false;
            int paziente = -1;

            try (PreparedStatement psSelect = conn.prepareStatement(selectSql);
                 ResultSet rs = psSelect.executeQuery()) {

                if (rs.next()) {
                    idVisita = rs.getInt("id_visita");
                    dataOra = rs.getString("dataOraVisita");
                    tipo = rs.getString("TipoVisita");
                    priorita = rs.getBoolean("Priorità");
                    paziente = rs.getInt("Paziente");
                } else {
                    System.out.println("Nessuna visita da processare.");
                    conn.rollback();
                    return;
                }
            }

            try (PreparedStatement psInsert = conn.prepareStatement(insertRecord);
                 PreparedStatement psDelete = conn.prepareStatement(deleteVisita)) {

                psInsert.setInt(1, idVisita);
                psInsert.executeUpdate();

                psDelete.setInt(1, idVisita);
                psDelete.executeUpdate();

                conn.commit();

                System.out.println("Visita processata correttamente:");
                System.out.println("Id visita: " + idVisita);
                System.out.println("Data e ora: " + dataOra);
                System.out.println("Tipo visita: " + tipo);
                System.out.println("Priorità: " + priorita);
                System.out.println("Id paziente: " + paziente);
            }

        } catch (SQLException e) {
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Visita> trovaRecord() {
        List<Visita> lista = new ArrayList<>();
        String sql = "SELECT r.dataOraVisita, r.TipoVisita, r.priorità, p.id_persona, p.NomePersona " +
                "FROM visite_effettuate r JOIN pazienti p ON r.Paziente = p.id_persona";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona(
                        rs.getString("NomePersona"),
                        rs.getString("id_persona")
                );

                lista.add(new Visita(
                        rs.getString("dataOraVisita"),
                        rs.getString("TipoVisita"),
                        rs.getBoolean("priorità"),
                        p
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
