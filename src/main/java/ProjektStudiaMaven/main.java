package ProjektStudiaMaven;

import java.io.File;

public class main {
    public static void main(String[] args) {
        Dziennik dziennik;
        String config = "dziennik.json";
        File plik = new File(config);

        if (plik.exists() && !plik.isDirectory()) {
            System.out.println("Znaleziono plik konfiguracyjny. Wczytuje dane...");
            dziennik = Dziennik.wczytajZJson(config);
            if (dziennik != null) {
                dziennik.odtworzPowiazania();
            } else {
                System.out.println("Blad wczytania, tworze nowy dziennik.");
                dziennik = new Dziennik();
                dziennik.inicjalizujDaneTestowe();
            }
        } else {
            System.out.println("Nie znaleziono pliku konfiguracyjnego. Inicjalizuje dane testowe...");
            dziennik = new Dziennik();
            dziennik.inicjalizujDaneTestowe();
        }

        dziennik.inicjalizujScanner();
        dziennik.start();
    }
}