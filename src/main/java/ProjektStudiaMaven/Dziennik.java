package ProjektStudiaMaven;

import java.io.File;
import java.util.ArrayList;

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

    public void start(InterfejsKonsolowy ui) {
        boolean uruchomiony = true;
        while (uruchomiony) {
            String user = ui.logowanie();
            switch (user) {
                case "Student" -> {
                    if (studenci.isEmpty()) {
                        System.out.println("Brak studentow w dzienniku.");
                        ui.czekajNaEnter();
                        break;
                    }
                    Student zalogowanyStudent = wybierzStudenta(ui);
                    if (zalogowanyStudent == null) break;
                    System.out.println("Zalogowano jako " + zalogowanyStudent);
                    ui.czekajNaEnter();
                    boolean uruchomionyStudent = true;
                    while (uruchomionyStudent) {
                        ui.wyswietlMenuStudenta();
                        int input = ui.pobierzLiczbeZZakresu(0, 4);
                        switch (input) {
                            case 1 -> {
                                System.out.println(zalogowanyStudent.getListaOcen());
                                ui.czekajNaEnter();
                            }

                            case 2 -> {
                                Przedmiot p2 = wybierzPrzedmiotStudenta(ui, zalogowanyStudent);
                                if (p2 != null) getListaOcenPrzedmiot(zalogowanyStudent, p2);
                                ui.czekajNaEnter();
                            }
                            case 3 -> {
                                Przedmiot p3 = wybierzPrzedmiotStudenta(ui, zalogowanyStudent);
                                if (p3 != null) System.out.println("Srednia: " + zalogowanyStudent.obliczSrednia(p3));
                                ui.czekajNaEnter();
                            }
                            case 4 -> {
                                if (zalogowanyStudent.getListaOcen().isEmpty()) {
                                    System.out.println("Nie posiadasz ocen z ktorych mozna wygenerowac raport.");
                                } else {
                                    File folderRaportow = new File("raporty");

                                    if (!folderRaportow.exists()) {
                                        folderRaportow.mkdir();
                                    }

                                    String nazwaRaportu = "raporty/Raport_" + zalogowanyStudent.getImie() + zalogowanyStudent.getNazwisko() + ".pdf";
                                    GeneratorPdf.generujRaportStudenta(zalogowanyStudent, nazwaRaportu);
                                }
                                ui.czekajNaEnter();
                            }
                            case 0 -> uruchomionyStudent = false;
                        }
                    }
                }
                case "Nauczyciel" -> {
                    if (nauczyciele.isEmpty()) {
                        System.out.println("Brak nauczycieli w dzienniku.");
                        ui.czekajNaEnter();
                        break;
                    }
                    Nauczyciel zalogowanyNauczyciel = wybierzNauczyciela(ui);
                    if (zalogowanyNauczyciel == null) break;
                    System.out.println("Zalogowano jako: " + zalogowanyNauczyciel);
                    ui.czekajNaEnter();
                    boolean uruchomionyNauczyciel = true;
                    while (uruchomionyNauczyciel) {
                        ui.wyswietlMenuNauczyciela();
                        int input = ui.pobierzLiczbeZZakresu(0, 3);
                        switch (input) {
                            case 1 -> {
                                if (zalogowanyNauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Brak nauczanych przedmiotow.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                System.out.println(zalogowanyNauczyciel.getNauczanePrzedmioty());
                                ui.czekajNaEnter();
                            }
                            case 2 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi chcesz wystawic ocene?");
                                Student wybranyStudent = wybierzStudenta(ui);
                                if (wybranyStudent == null) break;
                                if (zalogowanyNauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Brak nauczanych przedmiotow.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                Przedmiot wybranyPrzedmiot = wybierzPrzedmiot(ui, zalogowanyNauczyciel);
                                if (wybranyPrzedmiot == null) break;
                                System.out.println("Podaj ocene.");
                                int ocenaDoWstawienia = ui.pobierzLiczbeZZakresu(1, 6);
                                if (ocenaDoWstawienia == 0) break;
                                System.out.println("Podaj wage oceny.");
                                int wagaDoWstawienia = ui.pobierzLiczbeZZakresu(1, 5);
                                if (wagaDoWstawienia == 0) break;
                                Ocena doWstawienia = new Ocena(wybranyPrzedmiot, ocenaDoWstawienia, wagaDoWstawienia);
                                wybranyStudent.addOcena(doWstawienia);
                                BazaDanych.dodajOceneDoBazy(wybranyStudent, doWstawienia);
                            }
                            case 3 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi chcesz usunac ocene?");
                                Student wybranyStudentUsun = wybierzStudenta(ui);
                                if (wybranyStudentUsun == null) break;
                                if (wybranyStudentUsun.getListaOcen().isEmpty()) {
                                    System.out.println("Wybrany student nie ma zadnych ocen.");
                                    break;
                                }
                                usuwanieOceny(ui, wybranyStudentUsun);
                            }
                            case 0 -> uruchomionyNauczyciel = false;
                        }
                    }
                }
                case "Admin" -> {
                    System.out.println("Zalogowano jako: Admin");
                    ui.czekajNaEnter();
                    boolean uruchomionyAdmin = true;
                    while (uruchomionyAdmin) {
                        ui.wyswietlMenuAdmina();
                        int input = ui.pobierzLiczbeZZakresu(0, 10);
                        switch (input) {
                            case 1 -> {
                                System.out.println("Podaj imie nauczyciela.");
                                String noweImie = ui.pobierzTekst();
                                System.out.println("Podaj nazwisko nauczyciela.");
                                String noweNazwisko = ui.pobierzTekst();

                                Nauczyciel nowyNauczyciel = new Nauczyciel(noweImie, noweNazwisko);

                                nauczyciele.add(nowyNauczyciel);
                                BazaDanych.dodajNauczycielaDoBazy(nowyNauczyciel);

                                System.out.println("Lista nauczycieli po dodaniu:");
                                System.out.println(nauczyciele);

                                ui.czekajNaEnter();
                            }
                            case 2 -> {
                                if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                Nauczyciel case2Nauczyciel = wybierzNauczyciela(ui);
                                if (case2Nauczyciel == null) {
                                    break;
                                }
                                Przedmiot case2Przedmiot = wybierzPrzedmiotWszystkie(ui);
                                if (case2Przedmiot == null) {
                                    break;
                                }
                                case2Nauczyciel.uczPrzedmiotu(case2Przedmiot);
                                BazaDanych.przypiszPrzedmiotNauczycielowi(case2Nauczyciel, case2Przedmiot);
                                System.out.println("Nauczyciel " + case2Nauczyciel + " od teraz uczy " + case2Przedmiot + ".");
                                ui.czekajNaEnter();
                            }
                            case 3 -> {
                                Nauczyciel case3Nauczyciel = wybierzNauczyciela(ui);
                                if (case3Nauczyciel == null) {
                                    break;
                                }
                                if (case3Nauczyciel.getNauczanePrzedmioty().isEmpty()) {
                                    System.out.println("Wybrany nauczyciel nie uczy zadnego przedmiotu.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                Przedmiot case3Przedmiot = wybierzPrzedmiot(ui, case3Nauczyciel);
                                if (case3Przedmiot == null) {
                                    break;
                                }
                                case3Nauczyciel.nieUczPrzedmiotu(case3Przedmiot);
                                System.out.println("Zaktualizowane nauczane przedmioty przez " + case3Nauczyciel + ":");
                                System.out.println(case3Nauczyciel.getNauczanePrzedmioty());
                                ui.czekajNaEnter();
                            }
                            case 4 -> {
                                Nauczyciel case4Nauczyciel = wybierzNauczyciela(ui);
                                if (case4Nauczyciel == null) {
                                    break;
                                }
                                nauczyciele.remove(case4Nauczyciel);
                                System.out.println("Zaktualizowana lista nauczycieli: " + nauczyciele);
                                ui.czekajNaEnter();
                            }
                            case 5 -> {
                                System.out.println("Podaj imie studenta do dodania:");
                                String case5Imie = ui.pobierzTekst();
                                System.out.println("Podaj nazwisko studenta do dodania:");
                                String case5Nazwisko = ui.pobierzTekst();

                                Student nowyStudent = new Student(case5Imie, case5Nazwisko);

                                studenci.add(nowyStudent);
                                BazaDanych.dodajStudentaDoBazy(nowyStudent);

                                System.out.println("Lista studentow po dodaniu:");
                                System.out.println(studenci);

                                ui.czekajNaEnter();
                            }
                            case 6 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak dostepnych studentow.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                Student case6Student = wybierzStudenta(ui);
                                if (case6Student == null) break;
                                studenci.remove(case6Student);
                                System.out.println("Zaktualizowana lista studentow:");
                                System.out.println(studenci);
                                ui.czekajNaEnter();
                            }
                            case 7 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                } else if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    ui.czekajNaEnter();
                                }
                                System.out.println("Z jakiego przedmiotu wstawic ocene?");
                                Przedmiot case7Przedmiot = wybierzPrzedmiotWszystkie(ui);
                                if (case7Przedmiot == null) break;
                                System.out.println("Komu wstawic ocene?");
                                Student case7Student = wybierzStudenta(ui);
                                if (case7Student == null) break;
                                System.out.println("Jaka ma to byc ocena?");
                                int case7Wartosc = ui.pobierzLiczbeZZakresu(1, 6);
                                if (case7Wartosc == 0) break;
                                System.out.println("Jaka ma byc waga?");
                                int case7Waga = ui.pobierzLiczbeZZakresu(1, 5);
                                if (case7Waga == 0) break;
                                Ocena nowaOcena = new Ocena(case7Przedmiot, case7Wartosc, case7Waga);
                                case7Student.addOcena(nowaOcena);
                                BazaDanych.dodajOceneDoBazy(case7Student, nowaOcena);
                                System.out.println("Oceny studenta " + case7Student + " po wstawieniu oceny:");
                                System.out.println(case7Student.wyswietlOceny());
                                ui.czekajNaEnter();
                            }
                            case 8 -> {
                                if (studenci.isEmpty()) {
                                    System.out.println("Brak studentow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktoremu studentowi usunac ocene?");
                                Student case8Student = wybierzStudenta(ui);
                                if (case8Student == null) break;
                                if (case8Student.getListaOcen().isEmpty()) {
                                    System.out.println("Wybrany student nie ma zadnych ocen.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                usuwanieOceny(ui, case8Student);
                                ui.czekajNaEnter();
                            }
                            case 9 -> {
                                System.out.println("Jaki przedmiot chcesz dodac?");
                                Przedmiot case9Przedmiot = new Przedmiot(ui.pobierzTekst());
                                if (przedmioty.contains(case9Przedmiot)) {
                                    System.out.println("Przedmiot ktory chcesz dodac juz znajduje sie w dzienniku.");
                                    break;
                                }
                                System.out.println("Lista przedmiotow przed dodaniem:");
                                System.out.println(przedmioty);
                                System.out.println("Lista przedmiotow po dodaniu:");
                                przedmioty.add(case9Przedmiot);
                                BazaDanych.dodajPrzedmiotDoBazy(case9Przedmiot);
                                System.out.println(przedmioty);
                                ui.czekajNaEnter();
                            }
                            case 10 -> {
                                if (przedmioty.isEmpty()) {
                                    System.out.println("Brak przedmiotow w dzienniku.");
                                    ui.czekajNaEnter();
                                    break;
                                }
                                System.out.println("Ktory przedmiot chcesz usunac?");
                                int case10i = 1;
                                for (Przedmiot p : przedmioty) {
                                    System.out.println(case10i + ". " + p);
                                    case10i++;
                                }
                                int case10Wybor = ui.pobierzLiczbeZZakresu(1, przedmioty.size());
                                if (case10Wybor == 0) break;
                                Przedmiot case10Przedmiot = przedmioty.get(case10Wybor - 1);
                                for (Nauczyciel n : nauczyciele) {
                                    n.nieUczPrzedmiotu(case10Przedmiot);
                                }
                                przedmioty.remove(case10Przedmiot);
                                System.out.println("Lista przedmiotow po usunieciu:");
                                System.out.println(przedmioty);
                                ui.czekajNaEnter();
                            }
                            case 0 -> uruchomionyAdmin = false;
                        }
                    }
                }
//                case "Zapisz" -> BazaDanych.zapiszDoJson(this, "dziennik.json");
                case "Wyjscie" -> uruchomiony = false;
            }
        }
    }

    private Przedmiot wybierzPrzedmiot(InterfejsKonsolowy ui, Nauczyciel n) {
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
        int wybor = ui.pobierzLiczbeZZakresu(1, n.getNauczanePrzedmioty().size());
        if (wybor == 0) return null;

        return n.getNauczanePrzedmioty().get(wybor - 1);
    }

    private Przedmiot wybierzPrzedmiotWszystkie(InterfejsKonsolowy ui) {
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
        int wybor = ui.pobierzLiczbeZZakresu(1, przedmioty.size());
        if (wybor == 0) return null;


        return przedmioty.get(wybor - 1);
    }

    private Nauczyciel wybierzNauczyciela(InterfejsKonsolowy ui) {
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
        int wybor = ui.pobierzLiczbeZZakresu(0, nauczyciele.size());
        if (wybor == 0) return null;

        return nauczyciele.get(wybor - 1);
    }


    public void odtworzPowiazania() {
        for (Nauczyciel n : this.nauczyciele) {
            if (n.getNauczanePrzedmioty() == null) {
                n.setNauczanePrzedmioty(new ArrayList<>());
            }
        }
        System.out.println("Sprawdzono spojnosc danych nauczycieli.");
    }

    private void usuwanieOceny(InterfejsKonsolowy ui, Student student) {
        System.out.println("Aktualne oceny:");
        System.out.println(student.getListaOcen());

        int maxIndex = (student.getListaOcen().size());

        System.out.println("Ktora ocene studenta: " + student + " chcesz usunac?");

        int i = 1;
        for (Ocena o : student.getListaOcen()) {
            System.out.println(i + ". " + o);
            i++;
        }

        int index = ui.pobierzLiczbeZZakresu(0, maxIndex);
        if (index != 0) {
            student.removeOcena(index - 1);
            System.out.println("Oceny po usunieciu:");
            System.out.println(student.getListaOcen());
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

    private Przedmiot wybierzPrzedmiotStudenta(InterfejsKonsolowy ui, Student student) {
        ArrayList<String> nazwyPrzedmiotow = pokazPrzedmiotyStudenta(student);

        if (nazwyPrzedmiotow.isEmpty()) {
            System.out.println("Nie masz ocen z zadnych przedmiotow.");
            return null;
        }

        System.out.println("Wybierz przedmiot:");
        for (int i = 0; i < nazwyPrzedmiotow.size(); i++) {
            System.out.println((i + 1) + ". " + nazwyPrzedmiotow.get(i));
        }

        int wybor = ui.pobierzLiczbeZZakresu(1, nazwyPrzedmiotow.size());
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

    private Student wybierzStudenta(InterfejsKonsolowy ui) {
        if (studenci.isEmpty()) {
            System.out.println("Brak studentow w dzienniku.");
            return null;
        }

        System.out.println("Dostepni studenci:");
        for (int i = 0; i < studenci.size(); i++) {
            System.out.println((i + 1) + ". " + studenci.get(i));
        }
        int wybor = ui.pobierzLiczbeZZakresu(1, studenci.size());
        if (wybor == 0) {
            return null;
        }
        return studenci.get(wybor - 1);
    }

    public Student getStudentById(int id){
        for (Student s : studenci){
            if (s.getId() == id){
                return s;
            }
        }
        return null;
    }
    public Nauczyciel getNauczycielById(int id){
        for (Nauczyciel n : nauczyciele){
            if (n.getId() == id){
                return n;
            }
        }
        return null;
    }
    public Przedmiot getPrzedmiotById(int id){
        for (Przedmiot p : przedmioty){
            if (p.getId() == id){
                return p;
            }
        }
        return null;
    }
}
