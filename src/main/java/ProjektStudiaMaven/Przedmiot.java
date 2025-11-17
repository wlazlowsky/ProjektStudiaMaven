package ProjektStudiaMaven;

public class Przedmiot {
    private final String nazwa;
    private final Nauczyciel nauczyciel;

    public Przedmiot(String nazwa, Nauczyciel nauczyciel) {
        this.nazwa = nazwa;
        this.nauczyciel = nauczyciel;
        nauczyciel.uczPrzedmiotu(this);
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    public Nauczyciel getNauczyciel() {
        return nauczyciel;
    }
}
