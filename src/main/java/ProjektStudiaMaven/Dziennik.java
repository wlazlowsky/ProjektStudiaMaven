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
                case "Student" -> {
                    if (studenci.isEmpty()) {
                        System.out.println("Brak studentow w dzienniku.");
                        czekajNaEnter();
                        break;
                    }
                    Student zalogowanyStudent = wybierzStudenta();
                    if (zalogowanyStudent == null) break;
                    System.out.println("Zalogowano jako " + zalogowanyStudent);
                    czekajNaEnter();
                    boolean uruchomionyStudent = true;
                    while (uruchomionyStudent) {
                        wyswietlMenuStudenta();
                        int input = pobierzLiczbeZZakresu(0, 3);
                        switch (input) {
                            case 1 -> {
                                System.out.println(zalogowanyStudent.getListaOcen());
                                czekajNaEnter();
                            }

                            case 2 -> {
                                Przedmiot p2 = wybierzPrzedmiotStudenta(zalogowanyStudent);
                                if (p2 != null) getListaOcenPrzedmiot(zalogowanyStudent, p2);
                                czekajNaEnter();
                            }
                            case 3 -> {
                                Przedmiot p3 = wybierzPrzedmiotStudenta(zalogowanyStudent);
                                if (p3 != null) System.out.println("Srednia: " + zalogowanyStudent.obliczSrednia(p3));
                                czekajNaEnter();
                            }
                            case 0 -> uruchomionyStudent = false;
                        }
                    }
                }
                case "Nauczyciel" -> {
                    if (nauczyciele.isEmpty()) {
                        System.out.println("Brak nauczycieli w dzienniku.");
                        czekajNaEnter();
                        break;
                    }
                    Nauczyciel zalogowanyNauczyciel = wybierzNauczyciela();
                    if(zalogowanyNauczyciel == null) break;
                    System.out.println("Zalogowano jako: " + zalogowanyNauczyciel);
                    czekajNaEnter();
                    boolean uruchomionyNauczyciel = true;
                    while (uruchomionyNauczyciel) {
                        wyswietlMenuNauczyciela();
                        int input = pobierzLiczbeZZakresu(0, 3);
                        switch (input) {
                            case 1 -> {
                                if (zalogowanyNauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Brak nauczanych przedmiotow.");
                                    czekajNaEnter();
                                    break;
                                }
                                System.out.println(zalogowanyNauczyciel.getNauczanePrzedmioty());
                                czekajNaEnter();
                            }
                            case 2 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi chcesz wystawic ocene?");
                                Student wybranyStudent = wybierzStudenta();
                                if (wybranyStudent == null) break;
                                if (zalogowanyNauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Brak nauczanych przedmiotow.");
                                    czekajNaEnter();
                                    break;
                                }
                                Przedmiot wybranyPrzedmiot = wybierzPrzedmiot(zalogowanyNauczyciel);
                                if (wybranyPrzedmiot == null) break;
                                System.out.println("Podaj ocene.");
                                int ocenaDoWstawienia = pobierzLiczbeZZakresu(1, 6);
                                if (ocenaDoWstawienia == 0) break;
                                System.out.println("Podaj wage oceny.");
                                int wagaDoWstawienia = pobierzLiczbeZZakresu(1, 5);
                                if (wagaDoWstawienia == 0) break;
                                wybranyStudent.addOcena(new Ocena(wybranyPrzedmiot, ocenaDoWstawienia, wagaDoWstawienia));
                            }
                            case 3 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi chcesz usunac ocene?");
                                Student wybranyStudentUsun = wybierzStudenta();
                                if (wybranyStudentUsun == null) break;
                                if (wybranyStudentUsun.getListaOcen().isEmpty()) {
                                    System.out.println("Wybrany student nie ma zadnych ocen.");
                                    break;
                                }
                                usuwanieOceny(wybranyStudentUsun);
                            }
                            case 0 -> uruchomionyNauczyciel = false;
                        }
                    }
                }
                case "Admin" -> {
                    System.out.println("Zalogowano jako: Admin");
                    czekajNaEnter();
                    boolean uruchomionyAdmin = true;
                    while (uruchomionyAdmin) {
                        wyswietlMenuAdmina();
                        int input = pobierzLiczbeZZakresu(0, 10);
                        switch (input) {
                            case 1 -> {
                                System.out.println("Podaj imie nauczyciela.");
                                String noweImie = scanner.nextLine();
                                System.out.println("Podaj nazwisko nauczyciela.");
                                String noweNazwisko = scanner.nextLine();
                                Nauczyciel nowyNauczyciel = new Nauczyciel(noweImie, noweNazwisko);
                                nauczyciele.add(nowyNauczyciel);
                                System.out.println("Dostepni nauczyciele po dodaniu " + nowyNauczyciel);
                                System.out.println(nauczyciele);
                                czekajNaEnter();
                            }
                            case 2 -> {
                                if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                }
                                Nauczyciel case2Nauczyciel = wybierzNauczyciela();
                                if (case2Nauczyciel == null) {
                                    break;
                                }
                                Przedmiot case2Przedmiot = wybierzPrzedmiotWszystkie();
                                if (case2Przedmiot == null) {
                                    break;
                                }
                                case2Nauczyciel.uczPrzedmiotu(case2Przedmiot);
                                System.out.println("Nauczyciel " + case2Nauczyciel + " od teraz uczy " + case2Przedmiot + ".");
                                czekajNaEnter();
                            }
                            case 3 -> {
                                Nauczyciel case3Nauczyciel = wybierzNauczyciela();
                                if (case3Nauczyciel == null) {
                                    break;
                                }
                                if(case3Nauczyciel.getNauczanePrzedmioty().isEmpty()){
                                    System.out.println("Wybrany nauczyciel nie uczy zadnego przedmiotu.");
                                    czekajNaEnter();
                                    break;
                                }
                                Przedmiot case3Przedmiot = wybierzPrzedmiot(case3Nauczyciel);
                                if (case3Przedmiot == null) {
                                    break;
                                }
                                case3Nauczyciel.nieUczPrzedmiotu(case3Przedmiot);
                                System.out.println("Zaktualizowane nauczane przedmioty przez " + case3Nauczyciel + ":");
                                System.out.println(case3Nauczyciel.getNauczanePrzedmioty());
                                czekajNaEnter();
                            }
                            case 4 -> {
                                Nauczyciel case4Nauczyciel = wybierzNauczyciela();
                                if (case4Nauczyciel == null) {
                                    break;
                                }
                                nauczyciele.remove(case4Nauczyciel);
                                System.out.println("Zaktualizowana lista nauczycieli: " + nauczyciele);
                                czekajNaEnter();
                            }
                            case 5 -> {
                                System.out.println("Podaj imie studenta do dodania:");
                                String case5Imie = scanner.nextLine();
                                System.out.println("Podaj nazwisko studenta do dodania:");
                                String case5Nazwisko = scanner.nextLine();
                                studenci.add(new Student(case5Imie, case5Nazwisko));
                                System.out.println("Lista studentow po dodaniu " + case5Imie + " " + case5Nazwisko);
                                System.out.println(studenci);
                                czekajNaEnter();
                            }
                            case 6 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak dostepnych studentow.");
                                    czekajNaEnter();
                                    break;
                                }
                                Student case6Student = wybierzStudenta();
                                if (case6Student == null) break;
                                studenci.remove(case6Student);
                                System.out.println("Zaktualizowana lista studentow:");
                                System.out.println(studenci);
                                czekajNaEnter();
                            }
                            case 7 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                } else if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    czekajNaEnter();
                                }
                                System.out.println("Z jakiego przedmiotu wstawic ocene?");
                                Przedmiot case7Przedmiot = wybierzPrzedmiotWszystkie();
                                if (case7Przedmiot == null) break;
                                System.out.println("Komu wstawic ocene?");
                                Student case7Student = wybierzStudenta();
                                if (case7Student == null) break;
                                System.out.println("Jaka ma to byc ocena?");
                                int case7Wartosc = pobierzLiczbeZZakresu(1, 6);
                                if (case7Wartosc == 0) break;
                                System.out.println("Jaka ma byc waga?");
                                int case7Waga = pobierzLiczbeZZakresu(1, 5);
                                if (case7Waga == 0) break;
                                case7Student.addOcena(new Ocena(case7Przedmiot, case7Wartosc, case7Waga));
                                System.out.println("Oceny studenta " + case7Student + " po wstawieniu oceny:");
                                System.out.println(case7Student.wyswietlOceny());
                                czekajNaEnter();
                            }
                            case 8 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi usunac ocene?");
                                Student case8Student = wybierzStudenta();
                                if (case8Student == null) break;
                                if (case8Student.getListaOcen().isEmpty()) {
                                    System.out.println("Wybrany student nie ma zadnych ocen.");
                                    czekajNaEnter();
                                    break;
                                }
                                usuwanieOceny(case8Student);
                                czekajNaEnter();
                            }
                            case 9 -> {
                                System.out.println("Jaki przedmiot chcesz dodac?");
                                Przedmiot case9Przedmiot = new Przedmiot(scanner.nextLine());
                                if (przedmioty.contains(case9Przedmiot)) {
                                    System.out.println("Przedmiot ktory chcesz dodac juz znajduje sie w dzienniku.");
                                    break;
                                }
                                System.out.println("Lista przedmiotow przed dodaniem:");
                                System.out.println(przedmioty);
                                System.out.println("Lista przedmiotow po dodaniu:");
                                przedmioty.add(case9Przedmiot);
                                System.out.println(przedmioty);
                                czekajNaEnter();
                            }
                            case 10 -> {
                                if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktory przedmiot chcesz usunac?");
                                int case10i = 1;
                                for (Przedmiot p : przedmioty) {
                                    System.out.println(case10i + ". " + p);
                                    case10i++;
                                }
                                int case10Wybor = pobierzLiczbeZZakresu(1, przedmioty.size());
                                if (case10Wybor == 0) break;
                                Przedmiot case10Przedmiot = przedmioty.get(case10Wybor - 1);
                                for (Nauczyciel n : nauczyciele) {
                                    n.nieUczPrzedmiotu(case10Przedmiot);
                                }
                                przedmioty.remove(case10Przedmiot);
                                System.out.println("Lista przedmiotow po usunieciu:");
                                System.out.println(przedmioty);
                                czekajNaEnter();
                            }
                            case 0 -> uruchomionyAdmin = false;
                        }
                    }
                }
                case "Zapisz" -> zapiszDoJson("dziennik.json");
                case "Wyjscie" -> uruchomiony = false;
            }
        }
    }
    private void czekajNaEnter() {
        System.out.println("\nNacisnij ENTER, aby kontynuowac...");
        try {
            scanner.nextLine();
        } catch (Exception e){

        }
    }

    private Przedmiot wybierzPrzedmiot(Nauczyciel n) {
        if (przedmioty.isEmpty()) {
            System.out.println("Brak przedmiotow w dzienniku.");
            return null;
        }

        System.out.println("Dostepne przedmioty:");
        int i = 1;
        for (Przedmiot p : n.getNauczanePrzedmioty()) {
            System.out.println(i + ". " + p);
            i++;
        }
        int wybor = pobierzLiczbeZZakresu(1, n.getNauczanePrzedmioty().size());
        if (wybor == 0) return null;

        return n.getNauczanePrzedmioty().get(wybor - 1);
    }

    private Przedmiot wybierzPrzedmiotWszystkie() {
        if (przedmioty.isEmpty()) {
            System.out.println("Brak przedmiotow w dzienniku.");
            return null;
        }

        System.out.println("Dostepne przedmioty:");
        int i = 1;
        for (Przedmiot p : przedmioty) {
            System.out.println(i + ". " + p);
            i++;
        }
        int wybor = pobierzLiczbeZZakresu(1, przedmioty.size());
        if (wybor == 0) return null;


        return przedmioty.get(wybor - 1);
    }

    private Nauczyciel wybierzNauczyciela() {
        if (nauczyciele.isEmpty()) {
            System.out.println("Brak nauczycieli w dzienniku.");
            return null;
        }

        System.out.println("Dostepni nauczyciele:");
        int i = 1;
        for (Nauczyciel n : nauczyciele) {
            System.out.println(i + ". " + n);
            i++;
        }
        int wybor = pobierzLiczbeZZakresu(0, nauczyciele.size());
        if (wybor == 0) return null;

        return nauczyciele.get(wybor - 1);
    }

    public void inicjalizujScanner() {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }
    }

    public void odtworzPowiazania() {
        for (Nauczyciel n : this.nauczyciele) {
            if (n.getNauczanePrzedmioty() == null) {
                n.setNauczanePrzedmioty(new ArrayList<>());
            }
        }
        System.out.println("Sprawdzono spojnosc danych nauczycieli.");
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

    private void usuwanieOceny(Student student) {
        System.out.println("Aktualne oceny:");
        System.out.println(student.getListaOcen());

        int maxIndex = (student.getListaOcen().size());

        System.out.println("Ktora ocene studenta: " + student + " chcesz usunac?");

        int i = 1;
        for (Ocena o : student.getListaOcen()) {
            System.out.println(i + ". " + o);
            i++;
        }

        int index = pobierzLiczbeZZakresu(0, maxIndex);
        if (index != 0) {
            student.removeOcena(index - 1);
            System.out.println("Oceny po usunieciu:");
            System.out.println(student.getListaOcen());
        }
    }

    private void wyswietlMenuNauczyciela() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje przedmioty.");
        System.out.println("2. Wystaw ocene studentowi.");
        System.out.println("3. Usun ocene studentowi.");
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

    private Przedmiot wybierzPrzedmiotStudenta(Student student) {
        ArrayList<String> nazwyPrzedmiotow = pokazPrzedmiotyStudenta(student);

        if (nazwyPrzedmiotow.isEmpty()) {
            System.out.println("Nie masz ocen z zadnych przedmiotow.");
            return null;
        }

        System.out.println("Wybierz przedmiot:");
        for (int i = 0; i < nazwyPrzedmiotow.size(); i++) {
            System.out.println((i + 1) + ". " + nazwyPrzedmiotow.get(i));
        }

        int wybor = pobierzLiczbeZZakresu(1, nazwyPrzedmiotow.size());
        if (wybor == 0) return null;

        String wybranaNazwa = nazwyPrzedmiotow.get(wybor - 1);

        // Znajdź obiekt przedmiotu na podstawie nazwy (bierzemy pierwszy pasujący z ocen)
        for (Ocena o : student.getListaOcen()) {
            if (o.getPrzedmiot().getNazwa().equals(wybranaNazwa)) {
                return o.getPrzedmiot();
            }
        }
        return null;
    }

    public void getListaOcenPrzedmiot(Student student, Przedmiot przedmiot) {
        for (Ocena ocena : student.getListaOcen()) {
            if (ocena.getPrzedmiot().equals(przedmiot)) {
                System.out.println(ocena);
            }
        }
    }

    private Student wybierzStudenta() {
        if (studenci.isEmpty()) {
            System.out.println("Brak studentow w dzienniku.");
            return null;
        }

        System.out.println("Dostepni studenci:");
        for (int i = 0; i < studenci.size(); i++) {
            System.out.println((i + 1) + ". " + studenci.get(i));
        }
        int wybor = pobierzLiczbeZZakresu(1, studenci.size());
        if (wybor==0){
            return null;
        }
        return studenci.get(wybor-1);
    }

    private int pobierzLiczbeZZakresu(int min, int max) {
        int input;
        while (true) {
            System.out.println("0. Wyjscie.");
            System.out.println("Wybierz liczbe z zakresu " + min + " - " + max);
            try {
                input = scanner.nextInt();
                if (input == 0) {
                    scanner.nextLine();
                    return 0;
                }
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

    private void wyswietlMenuStudenta() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje oceny.");
        System.out.println("2. Wyswietl swoje oceny z danego przedmiotu.");
        System.out.println("3. Oblicz srednia wazona z danego przedmiotu.");
    }

    private String logowanie() {
        String input;
        System.out.println("Kto sie loguje? \"Student\", \"Nauczyciel\" czy \"Admin\"? Jesli chcesz zapisac dane wpisz \"Zapisz\". Jesli chcesz wyjsc wpisz \"Wyjscie\"");
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Student")
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
        Przedmiot matma = new Przedmiot("Matematyka");
        Przedmiot polski = new Przedmiot("Jezyk Polski");
        Przedmiot angielski = new Przedmiot("Jezyk Angielski");

        // Dodajemy je do głównej listy dziennika
        dodajPrzedmiot(matma);
        dodajPrzedmiot(polski);
        dodajPrzedmiot(angielski);

        // ZMIANA: Ręcznie przypisujemy przedmioty nauczycielom
        nKowalski.uczPrzedmiotu(matma);
        nNowak.uczPrzedmiotu(polski);
        nNowak.uczPrzedmiotu(angielski);

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
        System.out.println("5. Dodaj nowego studenta.");
        System.out.println("6. Usun studenta.");
        System.out.println("7. Dodaj nowa ocene.");
        System.out.println("8. Usun ocene.");
        System.out.println("9. Dodaj przedmiot.");
        System.out.println("10. Usun przedmiot.");
    }
}
