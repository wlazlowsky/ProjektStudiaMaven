package ProjektStudiaMaven;

import java.util.ArrayList;

public class Nauczyciel extends Osoba {
    private ArrayList<Przedmiot> nauczanePrzedmioty = new ArrayList<>();

    public Nauczyciel(String imie, String nazwisko) {
        super(imie, nazwisko);
    }

    public void setNauczanePrzedmioty(ArrayList<Przedmiot> nauczanePrzedmioty) {
        this.nauczanePrzedmioty = nauczanePrzedmioty;
    }

    public ArrayList<Przedmiot> getNauczanePrzedmioty() {
        return nauczanePrzedmioty;
    }

    void wystawOcene(Przedmiot przedmiot, Student student, int wartosc, int waga) {
        student.addOcena(new Ocena(przedmiot, wartosc, waga));
    }

    void uczPrzedmiotu(Przedmiot przedmiot) {
        nauczanePrzedmioty.add(przedmiot);
    }
    void nieUczPrzedmiotu(Przedmiot przedmiot) {
        nauczanePrzedmioty.remove(przedmiot);
    }

    void usunOcene(Student student, int index) {
        student.removeOcena(index);
    }
}
