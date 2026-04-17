package ProjektStudiaMaven;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfejsKonsolowy {
    private final Scanner scanner;

    public InterfejsKonsolowy() {
        this.scanner = new Scanner(System.in);
    }

    void czekajNaEnter() {
        System.out.println("\nNacisnij ENTER, aby kontynuowac...");
        try {
            scanner.nextLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    int pobierzLiczbeZZakresu(int min, int max) {
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

    void wyswietlMenuNauczyciela() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje przedmioty.");
        System.out.println("2. Wystaw ocene studentowi.");
        System.out.println("3. Usun ocene studentowi.");
    }

    void wyswietlMenuStudenta() {
        System.out.println("Co chcesz zrobic?");
        System.out.println("1. Wyswietl wszystkie swoje oceny.");
        System.out.println("2. Wyswietl swoje oceny z danego przedmiotu.");
        System.out.println("3. Oblicz srednia wazona z danego przedmiotu.");
        System.out.println("4. Wygeneruj raport ocen do pliku PDF.");
    }

    void wyswietlMenuAdmina() {
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

    String logowanie() {
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

    String pobierzTekst() {
        return scanner.nextLine();
    }

    void wyswietlWiadomosc(String wiadomosc) {
        System.out.println(wiadomosc);
    }
}
