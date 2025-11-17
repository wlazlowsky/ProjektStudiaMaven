package ProjektStudiaMaven;

public class Ocena {
    private Przedmiot przedmiot;
    private Student student;
    private int wartosc;
    private int waga;

    public Ocena(Przedmiot przedmiot, Student student, int wartosc, int waga) {
        this.przedmiot = przedmiot;
        this.student = student;

        if (wartosc < 1) {
            this.wartosc = 1;
        } else if (wartosc > 6) {
            this.wartosc = 6;
        } else {
            this.wartosc = wartosc;
        }

        if (waga < 1) {
            this.waga = 1;
        } else if (waga > 5) {
            this.waga = 5;
        } else {
            this.waga = waga;
        }
    }

    public int getWartosc() {
        return wartosc;
    }

    public int getWaga() {
        return waga;
    }

    public Przedmiot getPrzedmiot() {
        return przedmiot;
    }

    @Override
    public String toString() {
        return "{" + przedmiot + ", " + wartosc + " waga: " + waga + "}";
    }
}
