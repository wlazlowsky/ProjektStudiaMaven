package ProjektStudiaMaven;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Dziennik {
    private ArrayList<Student> studenci;
    private ArrayList<Nauczyciel> nauczyciele;
    private ArrayList<Przedmiot> przedmioty;

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

    Scanner scanner = new Scanner(System.in);

    public void start() {
        inicjalizujDaneTestowe();
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
                case "Wyjscie":
                    uruchomiony = false;
                    break;
            }
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
        System.out.println("Kto sie loguje? \"Uczen\" czy \"Nauczyciel\"? Jesli chcesz wyjsc wpisz \"Wyjscie\"");
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Uczen")
                    || input.equalsIgnoreCase("Nauczyciel")
                    || input.equalsIgnoreCase("Wyjscie")) {
                break;
            }
            System.out.println("Podaj prawidlowa komende.");
        }
        return input;
    }

    private void inicjalizujDaneTestowe() {
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
}
