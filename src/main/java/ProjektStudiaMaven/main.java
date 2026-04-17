package ProjektStudiaMaven;

import java.io.File;

public class main {
    public static void main(String[] args) {
        String config = "dziennik.json";
        File plik = new File(config);

        Dziennik dziennik = null;
        if (plik.exists() && !plik.isDirectory()) {
            System.out.println("Znaleziono plik konfiguracyjny. Wczytuje dane...");
            dziennik = BazaDanych.wczytajZJson(config);
            if (dziennik != null) {
                dziennik.odtworzPowiazania();
            }
        }
        if (dziennik == null) {
            System.out.println("Inicjalizuje dane testowe...");
            dziennik = new Dziennik();
            dziennik.inicjalizujDaneTestowe();
        }

        InterfejsKonsolowy ui = new InterfejsKonsolowy();
        dziennik.start(ui);
    }
}