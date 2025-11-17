package ProjektStudiaMaven;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Dziennik {
    private final ArrayList<Student> studenci;
    private final ArrayList<Nauczyciel> nauczyciele;
    private final ArrayList<Przedmiot> przedmioty;

    public Dziennik() {
        this.studenci = new ArrayList<>();
        this.nauczyciele = new ArrayList<>();
        this.przedmioty = new ArrayList<>();
    }

    public void dodajStudenta(Student student) {
        this.studenci.add(student);
    }

    public void dodajNauczyciela(Nauczyciel nauczyciel) {
        this.nauczyciele.add(nauczyciel);
    }

    public void dodajPrzedmiot(Przedmiot przedmiot) {
        this.przedmioty.add(przedmiot);
    }

    private transient Scanner scanner;

    public void start() {
        boolean uruchomiony = true;
        while (uruchomiony) {
            String user = logowanie();
            switch (user) {
                case "Uczen":
                    Student zalogowanyStudent = wybierzStudenta();
                    System.out.println("Zalogowano jako " + zalogowanyStudent);
                    boolean uruchomionyUczen = true;
                    while (uruchomionyUczen) {
                        wyswietlMenuUcznia();
                        int input = pobierzLiczbeZZakresu(0, 3);
                        switch (input) {
                            case 1:
                                System.out.println(zalogowanyStudent.getListaOcen());
                                break;
                            case 2:
                                System.out.println(pokazPrzedmiotyStudenta(zalogowanyStudent));
                                Przedmiot wybranyPrzedmiot = wybierzPrzedmiot(zalogowanyStudent);
                                getListaOcenPrzedmiot(zalogowanyStudent, wybranyPrzedmiot);
                                break;
                            case 3:
                                System.out.println(pokazPrzedmiotyStudenta(zalogowanyStudent));
                                wybranyPrzedmiot = wybierzPrzedmiot(zalogowanyStudent);
                                System.out.println(zalogowanyStudent.obliczSrednia(wybranyPrzedmiot));
                                break;
                            case 0:
                                uruchomionyUczen = false;
                                break;
                        }
                    }
                    break;
                case "Nauczyciel":
                    Nauczyciel zalogowanyNauczyciel = wybierzNauczyciela();
                    System.out.println("Zalogowano jako: " + zalogowanyNauczyciel);
                    boolean uruchomionyNauczyciel = true;
                    while (uruchomionyNauczyciel) {
                        wyswietlMenuNauczyciela();
                        int input = pobierzLiczbeZZakresu(0, 3);
                        switch (input) {
                            case 1:
                                System.out.println(zalogowanyNauczyciel.getNauczanePrzedmioty());
                                break;
                            case 2:
                                System.out.println("Ktoremu studentowi chcesz wystawic ocene?");
                                System.out.println(studenci);
                                Student wybranyStudent = wybierzStudenta();
                                System.out.println(zalogowanyNauczyciel.getNauczanePrzedmioty());
                                Przedmiot wybranyPrzedmiot = wybierzPrzedmiotNauczyciel(zalogowanyNauczyciel);
                                System.out.println("Podaj ocene.");
                                int ocenaDoWstawienia = pobierzLiczbeZZakresu(1, 6);
                                System.out.println("Podaj wage oceny.");
                                int wagaDoWstawienia = pobierzLiczbeZZakresu(1, 5);
                                wybranyStudent.addOcena(new Ocena(wybranyPrzedmiot, wybranyStudent, ocenaDoWstawienia, wagaDoWstawienia));
                                break;
                            case 3:
                                System.out.println("Ktoremu studentowi chcesz usunac ocene?");
                                System.out.println(studenci);
                                Student wybranyStudentUsun = wybierzStudenta();
                                usuwanieOceny(wybranyStudentUsun, zalogowanyNauczyciel);
                                break;
                            case 0:
                                uruchomionyNauczyciel = false;
                                break;
                        }
                    }
                    break;
                case "Admin":
                    System.out.println("Zalogowano jako: Admin");
                    boolean uruchomionyAdmin = true;
                    while (uruchomionyAdmin) {
                        wyswietlMenuAdmina();
                        int input = pobierzLiczbeZZakresu(0, 10);
                        switch (input) {
                            case 1:
                                System.out.println("Podaj imie nauczyciela.");
                                String noweImie = scanner.nextLine();
                                System.out.println("Podaj nazwisko nauczyciela.");
                                String noweNazwisko = scanner.nextLine();
                                Nauczyciel nowyNauczyciel = new Nauczyciel(noweImie, noweNazwisko);
                                nauczyciele.add(nowyNauczyciel);
                                System.out.println("Dostepni nauczyciele po dodaniu " + nowyNauczyciel);
                                System.out.println(nauczyciele);
                                break;
                            case 2:
                                if (nauczyciele.isEmpty()) {
                                    System.out.println("Brak nauczycieli w dzienniku.");
                                    break;
                                } else if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    break;
                                }

                                System.out.println("Dostepni nauczyciele:");
                                int case2i = 1;
                                for (Nauczyciel n : nauczyciele) {
                                    System.out.println(case2i + ". " + n);
                                    case2i++;
                                }
                                int case2Wybor = pobierzLiczbeZZakresu(1, nauczyciele.size());
                                Nauczyciel case2Nauczyciel = nauczyciele.get(case2Wybor - 1);

                                System.out.println("Dostepne przedmioty do przydzielenia:");
                                case2i = 1;
                                for (Przedmiot p : przedmioty) {
                                    System.out.println(case2i + ". " + p);
                                    case2i++;
                                }
                                case2Wybor = pobierzLiczbeZZakresu(1, przedmioty.size());
                                Przedmiot case2Przedmiot = przedmioty.get(case2Wybor - 1);

                                case2Nauczyciel.uczPrzedmiotu(case2Przedmiot);

                                System.out.println("Nauczyciel " + case2Nauczyciel + " od teraz uczy " + case2Przedmiot + ".");

                                break;
                            case 3:
                                if (nauczyciele.isEmpty()) {
                                    System.out.println("Brak nauczycieli w dzienniku.");
                                    break;
                                }

                                System.out.println("Dostepni nauczyciele:");
                                int case3i = 1;
                                for (Nauczyciel n : nauczyciele) {
                                    System.out.println(case3i + ". " + n);
                                    case3i++;
                                }
                                int case3Wybor = pobierzLiczbeZZakresu(1, nauczyciele.size());
                                Nauczyciel case3Nauczyciel = nauczyciele.get(case3Wybor - 1);
                                if (case3Nauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Wybrany nauczyciel nie uczy zadnego przedmiotu.");
                                    break;
                                }
                                System.out.println("Ktorego przedmiotu ma juz nie uczyc?");
                                case3i = 1;
                                for (Przedmiot p : case3Nauczyciel.getNauczanePrzedmioty()) {
                                    System.out.println(case3i + ". " + p);
                                    case3i++;
                                }
                                case3Wybor = pobierzLiczbeZZakresu(1, case3Nauczyciel.getNauczanePrzedmioty().size());


                                //do zrobienia arraylist nauczycieli w przedmiocie i usuwanie przedmiotu.


                                break;
                        }
                        break;
                    }
                    break;
                case "Zapisz":
                    zapiszDoJson("dziennik.json");
                    break;
                case "Wyjscie":
                    uruchomiony = false;
                    break;

            }
        }
    }


    public void inicjalizujScanner() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
    }

    public void odtworzPowiazania() {
        if (this.przedmioty == null || this.nauczyciele == null) {
            return;
        }


        for (Nauczyciel n : this.nauczyciele) {
            if (n.getNauczanePrzedmioty() == null) {
                n.setNauczanePrzedmioty(new ArrayList<>());
            }
            n.getNauczanePrzedmioty().clear();
        }

        for (Przedmiot p : this.przedmioty) {
            Nauczyciel nauczycielPrzedmiotu = p.getNauczyciel();

            if (nauczycielPrzedmiotu != null) {
                // ---- DODATKOWE ZABEZPIECZENIE ----
                // Sprawdź, czy lista tego nauczyciela (z przedmiotu) jest zainicjalizowana
                if (nauczycielPrzedmiotu.getNauczanePrzedmioty() == null) {
                    nauczycielPrzedmiotu.setNauczanePrzedmioty(new ArrayList<>());
                }

                // Teraz wywołanie jest bezpieczne
                nauczycielPrzedmiotu.uczPrzedmiotu(p);
            }
        }
        System.out.println("Odtworzono powiazania miedzy nauczycielami a przedmiotami.");
    }

    public static Dziennik wczytajZJson(String sciezkaDoPliku) {
        Gson gson = new Gson();
        Dziennik dziennik = null;

        try (FileReader reader = new FileReader(sciezkaDoPliku)) {
            dziennik = gson.fromJson(reader, Dziennik.class);
            System.out.println("Wczytano stan dziennika z pliku: " + sciezkaDoPliku);
        } catch (IOException e) {
            System.err.println("Blad podczas wczytywania pliku JSON: " + e.getMessage());
        }
        return dziennik;
    }

    public void zapiszDoJson(String sciezkaDoPliku) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(sciezkaDoPliku)) {
            gson.toJson(this, writer);
            System.out.println("Zapisano stan dziennika do pliku: " + sciezkaDoPliku);
        } catch (IOException e) {
            System.err.println("Blad podczas zapisu do pliku JSON: " + e.getMessage());
        }
    }

    private void usuwanieOceny(Student student, Nauczyciel nauczyciel) {
        System.out.println("Aktualne oceny:");
        System.out.println(student.getListaOcen());

        int index = -1;
        int maxIndex = (student.getListaOcen().size() - 1);

        System.out.println("Ktora ocene studenta: " + student + " chcesz usunac?");
        System.out.println("Podaj indeks z zakresu 0 - " + maxIndex);

        while (true) {
            try {
                index = scanner.nextInt();

                if (index >= 0 && index <= maxIndex) {
                    break;
                } else {
                    System.out.println("Liczba poza zakresem. Podaj indeks z zakresu 0 - " + maxIndex);
                }
            } catch (InputMismatchException e) {
                System.out.println("Prosze o prawidlowe dane wejsciowe.");
                scanner.nextLine();
            }
        }
        nauczyciel.usunOcene(student, index);
        System.out.println("Oceny po usunieciu:");
        System.out.println(student.getListaOcen());
    }

    private ArrayList<Przedmiot> pokazPrzedmiotyNauczyciela(Nauczyciel nauczyciel) {
        ArrayList<Przedmiot> dostepnePrzedmioty = new ArrayList<>();
        for (Przedmiot przedmiot : nauczyciel.getNauczanePrzedmioty()) {
            if (!dostepnePrzedmioty.contains(przedmiot)) {
                dostepnePrzedmioty.add(przedmiot);
            }
        }
        return dostepnePrzedmioty;
    }

    private Przedmiot wybierzPrzedmiotNauczyciel(Nauczyciel nauczyciel) {
        String wybor;
        while (true) {
            System.out.println("Wybierz przedmiot z wczesniej podanych.");
            wybor = scanner.nextLine();
            for (Przedmiot przedmiot : pokazPrzedmiotyNauczyciela(nauczyciel)) {
                if (przedmiot.getNazwa().equalsIgnoreCase(wybor)) {
                    return przedmiot;
                }
            }
            System.out.println("Podaj poprawny przedmiot.");
        }
    }

    private void wyswietlMenuNauczyciela() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje przedmioty.");
        System.out.println("2. Wystaw ocene uczniowi.");
        System.out.println("3. Usun ocene uczniowi.");
        System.out.println("0. Wyjscie");
    }

    private Nauczyciel wybierzNauczyciela() {
        System.out.println("Wybierz jako kto sie logujesz:");
        for (int i = 0; i < nauczyciele.size(); i++) {
            System.out.println((i + 1) + ". " + nauczyciele.get(i));
        }
        System.out.println("Wybierz numer: ");

        int wybor = -1;
        while (true) {
            try {
                wybor = scanner.nextInt();
                if (wybor >= 1 && wybor <= nauczyciele.size()) {
                    scanner.nextLine();
                    return nauczyciele.get(wybor - 1);
                } else {
                    System.out.println("Podaj poprawny numer z listy.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Prosze o prawidlowe dane wejsciowe.");
                scanner.nextLine();
            }
        }
    }

    private Przedmiot wybierzPrzedmiot(Student student) {
        String wybor;
        System.out.println("Wybierz przedmiot z wczesniej podanych.");
        while (true) {
            wybor = scanner.nextLine();
            for (Ocena ocena : student.getListaOcen()) {
                if (ocena.getPrzedmiot().getNazwa().equalsIgnoreCase(wybor)) {
                    return ocena.getPrzedmiot();
                }
            }
            System.out.println("Podaj poprawny przedmiot.");
        }
    }

    private ArrayList<String> pokazPrzedmiotyStudenta(Student student) {
        ArrayList<String> dostepnePrzedmioty = new ArrayList<>();
        for (Ocena ocena : student.getListaOcen()) {
            if (!dostepnePrzedmioty.contains(ocena.getPrzedmiot().getNazwa())) {
                dostepnePrzedmioty.add(ocena.getPrzedmiot().getNazwa());
            }
        }
        return dostepnePrzedmioty;
    }

    public void getListaOcenPrzedmiot(Student student, Przedmiot przedmiot) {
        for (Ocena ocena : student.getListaOcen()) {
            if (ocena.getPrzedmiot().equals(przedmiot)) {
                System.out.println(ocena);
            }
        }
    }

    private Student wybierzStudenta() {
        System.out.println("Wybierz studenta:");
        for (int i = 0; i < studenci.size(); i++) {
            System.out.println((i + 1) + ". " + studenci.get(i));
        }
        System.out.println("Wybierz numer: ");

        int wybor = -1;
        while (true) {
            try {
                wybor = scanner.nextInt();
                if (wybor >= 1 && wybor <= studenci.size()) {
                    scanner.nextLine();
                    return studenci.get(wybor - 1);
                } else {
                    System.out.println("Podaj poprawny numer z listy.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Prosze o prawidlowe dane wejsciowe.");
                scanner.nextLine();
            }
        }
    }

    private int pobierzLiczbeZZakresu(int min, int max) {
        int input = -1;
        while (true) {
            System.out.println("Wybierz liczbe z zakresu " + min + " - " + max);
            try {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    scanner.nextLine();
                    break;
                } else {
                    System.out.println("Podaj prawidlowa opcje.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Prosze o prawidlowe dane wejsciowe.");
                scanner.nextLine();
            }
        }
        return input;
    }

    private void wyswietlMenuUcznia() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje oceny.");
        System.out.println("2. Wyswietl swoje oceny z danego przedmiotu.");
        System.out.println("3. Oblicz srednia wazona z danego przedmiotu.");
        System.out.println("0. Wyjscie");
    }

    private String logowanie() {
        String input = "null";
        System.out.println("Kto sie loguje? \"Uczen\", \"Nauczyciel\" czy \"Admin\"? Jesli chcesz zapisac dane wpisz \"Zapisz\". Jesli chcesz wyjsc wpisz \"Wyjscie\"");
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Uczen")
                    || input.equalsIgnoreCase("Nauczyciel")
                    || input.equalsIgnoreCase("Admin")
                    || input.equalsIgnoreCase("Wyjscie")
                    || input.equalsIgnoreCase("Zapisz")) {
                break;
            }
            System.out.println("Podaj prawidlowa komende.");
        }
        return input;
    }

    public void inicjalizujDaneTestowe() {
        System.out.println("Ladowanie danych testowych...");

        // --- Nauczyciele ---
        // Tworzymy nauczycieli
        Nauczyciel nKowalski = new Nauczyciel("Jan", "Kowalski");
        Nauczyciel nNowak = new Nauczyciel("Maria", "Nowak");

        // Dodajemy ich do głównej listy dziennika
        dodajNauczyciela(nKowalski);
        dodajNauczyciela(nNowak);

        // --- Studenci ---
        // Tworzymy studentów
        Student sZielinska = new Student("Anna", "Zielinska");
        Student sWisniewski = new Student("Piotr", "Wisniewski");
        Student sLis = new Student("Ewa", "Lis");

        // Dodajemy ich do głównej listy dziennika
        dodajStudenta(sZielinska);
        dodajStudenta(sWisniewski);
        dodajStudenta(sLis);

        // --- Przedmioty ---
        // Tworzymy przedmioty, od razu przypisując nauczycieli
        // (Konstruktor Przedmiotu sam doda przedmiot do listy nauczyciela)
        Przedmiot matma = new Przedmiot("Matematyka", nKowalski);
        Przedmiot polski = new Przedmiot("Jezyk Polski", nNowak);
        Przedmiot angielski = new Przedmiot("Jezyk Angielski", nNowak);

        // Dodajemy je do głównej listy dziennika
        dodajPrzedmiot(matma);
        dodajPrzedmiot(polski);
        dodajPrzedmiot(angielski);

        // --- Oceny ---
        // Nauczyciele wystawiają oceny studentom z konkretnych przedmiotów

        // Oceny Anny Zielińskiej
        nKowalski.wystawOcene(matma, sZielinska, 5, 3); // Matma, ocena 5, waga 3
        nKowalski.wystawOcene(matma, sZielinska, 4, 1); // Matma, ocena 4, waga 1
        nNowak.wystawOcene(polski, sZielinska, 6, 2); // Polski, ocena 6, waga 2

        // Oceny Piotra Wiśniewskiego
        nKowalski.wystawOcene(matma, sWisniewski, 3, 3); // Matma, ocena 3, waga 3
        nNowak.wystawOcene(angielski, sWisniewski, 5, 1); // Angielski, ocena 5, waga 1
        nNowak.wystawOcene(angielski, sWisniewski, 2, 1); // Angielski, ocena 2, waga 1

        // Oceny Ewy Lis
        nNowak.wystawOcene(polski, sLis, 4, 2); // Polski, ocena 4, waga 2
        nNowak.wystawOcene(angielski, sLis, 3, 1); // Angielski, ocena 3, waga 1

        System.out.println("Dane zaladowane pomyslnie!");
        System.out.println("--- --- --- --- --- --- --- ---");
    }

    private void wyswietlMenuAdmina() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Dodaj nowego nauczyciela.");
        System.out.println("2. Przydziel nauczycielowi przedmiot.");
        System.out.println("3. Zabierz przydzial przedmiotu nauczycielowi.");
        System.out.println("4. Usun nauczyciela.");
        System.out.println("5. Dodaj nowego ucznia.");
        System.out.println("6. Usun ucznia.");
        System.out.println("7. Dodaj nowa ocene.");
        System.out.println("8. Usun ocene.");
        System.out.println("9. Dodaj przedmiot.");
        System.out.println("10. Usun przedmiot.");
    }
}
