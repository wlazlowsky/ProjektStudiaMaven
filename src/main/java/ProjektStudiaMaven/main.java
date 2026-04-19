package ProjektStudiaMaven;

import java.io.File;

public class main {
    public static void main(String[] args) {

        BazaDanych.inicjalizujBaze();
        Dziennik dziennik = new Dziennik();

        BazaDanych.wczytajWszystkoZBazy(dziennik);

        InterfejsKonsolowy ui = new InterfejsKonsolowy();
        dziennik.start(ui);

    }
}