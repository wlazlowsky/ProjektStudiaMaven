package ProjektStudiaMaven;

import java.util.ArrayList;

public class Student extends Osoba {
    private final ArrayList<Ocena> listaOcen;

    public Student(String imie, String nazwisko) {
        super(imie, nazwisko);
        this.listaOcen = new ArrayList<>();
    }

    public ArrayList<Ocena> getListaOcen() {
        return listaOcen;
    }

    public String wyswietlOceny() {
        return "Oceny studenta: " + this + "\n" + listaOcen;
    }

    public void addOcena(Ocena ocena) {
        listaOcen.add(ocena);
    }

    public void removeOcena(int index) {
        listaOcen.remove(index);
    }

    public double obliczSrednia(Przedmiot przedmiot) {
        if (listaOcen.isEmpty()) {
            return 0.0;
        }

        double sumaWartosciWazonych = 0;
        double sumaWag = 0;

        for (Ocena ocena : listaOcen) {
            if (ocena.getPrzedmiot().equals(przedmiot)) {
                sumaWartosciWazonych += ocena.getWartosc() * ocena.getWaga();
                sumaWag += ocena.getWaga();
            }
        }

        if (sumaWag == 0) {
            return 0.0;
        }

        return sumaWartosciWazonych / sumaWag;
    }
}
