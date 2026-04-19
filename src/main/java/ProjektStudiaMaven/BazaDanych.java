package ProjektStudiaMaven;

import java.sql.*;

public class BazaDanych {

    private static Connection polacz() throws SQLException {
        String url = "jdbc:sqlite:dziennik.db";
        return DriverManager.getConnection(url);
    }

    public static void inicjalizujBaze() {
        String sqlStudenci = "CREATE TABLE IF NOT EXISTS studenci (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "imie TEXT NOT NULL, nazwisko TEXT NOT NULL);";
        String sqlNauczyciele = "CREATE TABLE IF NOT EXISTS nauczyciele (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "imie TEXT NOT NULL, nazwisko TEXT NOT NULL);";
        String sqlPrzedmioty = "CREATE TABLE IF NOT EXISTS przedmioty (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nazwa TEXT NOT NULL);";
        String sqlOceny = "CREATE TABLE IF NOT EXISTS oceny (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_id INTEGER NOT NULL, przedmiot_id INTEGER NOT NULL, " +
                "wartosc INTEGER NOT NULL, waga INTEGER NOT NULL, " +
                "FOREIGN KEY (student_id) REFERENCES studenci(id), " +
                "FOREIGN KEY (przedmiot_id) REFERENCES przedmioty(id));";
        String nauczyciele_przedmioty = "CREATE TABLE IF NOT EXISTS nauczyciele_przedmioty (nauczyciel_id INTEGER NOT NULL," +
                "przedmiot_id INTEGER NOT NULL," +
                "FOREIGN KEY(nauczyciel_id) REFERENCES nauczyciele(id)," +
                "FOREIGN KEY(przedmiot_id) REFERENCES przedmioty(id)," +
                "PRIMARY KEY(nauczyciel_id, przedmiot_id));";

        try (Connection conn = polacz();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlStudenci);
            stmt.execute(sqlNauczyciele);
            stmt.execute(sqlPrzedmioty);
            stmt.execute(sqlOceny);
            stmt.execute(nauczyciele_przedmioty);
        } catch (SQLException ex) {
            System.err.println("Blad podczas inicjalizacji bazy: " + ex.getMessage());
        }
    }

    public static void dodajStudentaDoBazy(Student student) {
        String sql = "INSERT INTO studenci (imie, nazwisko) VALUES (?, ?);";

        try (Connection conn = polacz();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, student.getImie());
            pstmt.setString(2, student.getNazwisko());

            int zmienioneWiersze = pstmt.executeUpdate();

            if (zmienioneWiersze > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        student.setId(rs.getInt(1));
                    }
                }
                System.out.println("[SQL] Dodano studenta: "
                        + student.getImie() + " "
                        + student.getNazwisko() +
                        " (ID: " + student.getId() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Blad podczas dodawania studenta do bazy: " + e.getMessage());
        }
    }

    public static void dodajNauczycielaDoBazy(Nauczyciel nauczyciel) {
        String sql = "INSERT INTO nauczyciele (imie, nazwisko) VALUES (?, ?);";

        try (Connection conn = polacz();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, nauczyciel.getImie());
            pstmt.setString(2, nauczyciel.getNazwisko());

            int zmienioneWiersze = pstmt.executeUpdate();

            if (zmienioneWiersze > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        nauczyciel.setId(rs.getInt(1));
                    }
                }
                System.out.println("[SQL] Dodano nauczyciela: "
                        + nauczyciel.getImie() + " "
                        + nauczyciel.getNazwisko() +
                        " (ID: " + nauczyciel.getId() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Blad podczas dodawania nauczyciela do bazy: " + e.getMessage());
        }
    }

    public static void dodajPrzedmiotDoBazy(Przedmiot przedmiot) {
        String sql = "INSERT INTO przedmioty (nazwa) VALUES (?);";

        try (Connection conn = polacz();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, przedmiot.getNazwa());

            int zmienioneWiersze = pstmt.executeUpdate();

            if (zmienioneWiersze > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        przedmiot.setId(rs.getInt(1));
                    }
                }
                System.out.println("[SQL] Dodano przedmiot: "
                        + przedmiot.getNazwa() +
                        " (ID: " + przedmiot.getId() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Blad podczas dodawania przedmiotu do bazy: " + e.getMessage());
        }
    }

    public static void dodajOceneDoBazy(Student student, Ocena ocena) {
        String sql = "INSERT INTO oceny (student_id, przedmiot_id, wartosc, waga) VALUES (?, ?, ?, ?);";

        try (Connection conn = polacz();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, student.getId());
            pstmt.setInt(2, ocena.getPrzedmiot().getId());
            pstmt.setInt(3, ocena.getWartosc());
            pstmt.setInt(4, ocena.getWaga());

            int zmienioneWiersze = pstmt.executeUpdate();

            if (zmienioneWiersze > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        ocena.setId(rs.getInt(1));
                    }
                }
                System.out.println("[SQL] Dodano ocene: "
                        + ocena.toString()
                        + "studenta: "
                        + student.toString()
                        + " (ID: " + ocena.getId() + ")");
            }
        } catch (SQLException e) {
            System.err.println("Blad podczas dodawania oceny do bazy: " + e.getMessage());
        }
    }

    public static void przypiszPrzedmiotNauczycielowi(Nauczyciel nauczyciel, Przedmiot przedmiot) {
        String sql = "INSERT INTO nauczyciele_przedmioty (nauczyciel_id, przedmiot_id) VALUES (?, ?);";

        try (Connection conn = polacz();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nauczyciel.getId());
            pstmt.setInt(2, przedmiot.getId());

            int zmienioneWiersze = pstmt.executeUpdate();

            if (zmienioneWiersze > 0) {
                System.out.println("[SQL] Przypisano przedmiot: "
                        + przedmiot.toString()
                        + " nauczycielowi: "
                        + nauczyciel.toString());
            }
        } catch (SQLException e) {
            System.err.println("Blad podczas przypisywania przedmiotu nauczycielowi w bazie: " + e.getMessage());
        }
    }

    public static void wczytajWszystkoZBazy(Dziennik dziennik) {
        String sqlStudenci = "SELECT id, imie, nazwisko FROM studenci";
        String sqlNauczyciele = "SELECT id, imie, nazwisko FROM nauczyciele";
        String sqlPrzedmioty = "SELECT id, nazwa FROM przedmioty";
        String sqlOceny = "SELECT id, student_id, przedmiot_id, wartosc, waga FROM oceny";
        String sqlNauczycielePrzedmioty = "SELECT nauczyciel_id, przedmiot_id FROM nauczyciele_przedmioty";

        try (Connection conn = polacz()) {

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlStudenci)) {

                while (rs.next()) {
                    Student s = new Student(rs.getString("imie"), rs.getString("nazwisko"));
                    s.setId(rs.getInt("id"));
                    dziennik.dodajStudenta(s);
                }
                System.out.println("[SQL] Wczytano studentow.");

            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlNauczyciele)) {

                while (rs.next()) {
                    Nauczyciel n = new Nauczyciel(rs.getString("imie"), rs.getString("nazwisko"));
                    n.setId(rs.getInt("id"));
                    dziennik.dodajNauczyciela(n);
                }
                System.out.println("[SQL] Wczytano nauczycieli.");

            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlPrzedmioty)) {

                while (rs.next()) {
                    Przedmiot p = new Przedmiot(rs.getString("nazwa"));
                    p.setId(rs.getInt("id"));
                    dziennik.dodajPrzedmiot(p);
                }
                System.out.println("[SQL] Wczytano przedmioty.");

            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlOceny)) {

                while (rs.next()) {
                    int idOceny = rs.getInt("id");
                    int studentId = rs.getInt("student_id");
                    int przedmiotId = rs.getInt("przedmiot_id");
                    int wartosc = rs.getInt("wartosc");
                    int waga = rs.getInt("waga");

                    Student s = dziennik.getStudentById(studentId);
                    Przedmiot p = dziennik.getPrzedmiotById(przedmiotId);

                    if (s != null && p != null) {
                        Ocena nowaOcena = new Ocena(p, wartosc, waga);
                        nowaOcena.setId(idOceny);
                        s.addOcena(nowaOcena);
                    }
                }
                System.out.println("[SQL] Wczytano oceny.");
            }

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlNauczycielePrzedmioty)) {

                while (rs.next()) {
                    Nauczyciel n = dziennik.getNauczycielById(rs.getInt("nauczyciel_id"));
                    Przedmiot p = dziennik.getPrzedmiotById(rs.getInt("przedmiot_id"));

                    if (n != null && p != null) {
                        n.uczPrzedmiotu(p);
                    }
                }
                System.out.println("[SQL] Wczytano przedmioty nauczane przez nauczycieli.");

            }
        } catch (SQLException e) {
            System.err.println("Blad podczas wczytywania bazy danych: " + e.getMessage());
        }
    }
}