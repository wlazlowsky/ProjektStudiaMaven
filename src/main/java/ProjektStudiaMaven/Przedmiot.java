package ProjektStudiaMaven;

import java.util.Objects;

public class Przedmiot {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private final String nazwa;

    public Przedmiot(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Przedmiot przedmiot = (Przedmiot) o;
        return Objects.equals(nazwa, przedmiot.nazwa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nazwa);
    }
}
