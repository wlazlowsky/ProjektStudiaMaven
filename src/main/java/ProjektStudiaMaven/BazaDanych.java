package ProjektStudiaMaven;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BazaDanych {

    public static Dziennik wczytajZJson(String sciezkaDoPliku) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(sciezkaDoPliku)) {
            Dziennik dziennik = gson.fromJson(reader, Dziennik.class);
            System.out.println("Wczytano stan dziennika z pliku: " + sciezkaDoPliku);
            return dziennik;
        } catch (IOException e) {
            System.err.println("Blad podczas wczytywania pliku JSON: " + e.getMessage());
            return null;
        }
    }

    public static void zapiszDoJson(Dziennik dziennik, String sciezkaDoPliku) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(sciezkaDoPliku)) {
            gson.toJson(dziennik, writer);
            System.out.println("Zapisano stan dziennika do pliku: " + sciezkaDoPliku);
        } catch (IOException e) {
            System.err.println("Blad podczas zapisu do pliku JSON: " + e.getMessage());
        }
    }
}
