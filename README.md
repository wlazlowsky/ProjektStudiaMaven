[README.md](https://github.com/user-attachments/files/26868747/README.md)
# 🎓 Elektroniczny Dziennik Akademicki

Konsolowa aplikacja w języku Java realizująca system zarządzania ocenami i użytkownikami na uczelni lub w szkole. Projekt demonstruje praktyczne zastosowanie paradygmatów programowania obiektowego (OOP), integrację z relacyjną bazą danych oraz automatyczne generowanie dokumentów.

## 🚀 Główne funkcjonalności

System opiera się na interaktywnym interfejsie konsolowym i posiada podział na trzy role użytkowników:

* **🛠️ Administrator**: Zarządzanie użytkownikami (dodawanie/usuwanie), dodawanie nowych przedmiotów oraz zarządzanie relacjami (przydzielanie przedmiotów nauczycielom).
* **📚 Nauczyciel**: Przeglądanie przypisanych przedmiotów, wystawianie ocen o różnej wadze oraz usuwanie wpisów w dzienniku konkretnych studentów.
* **🎓 Student**: Podgląd własnych ocen z podziałem na przedmioty, automatyczne wyliczanie średniej ważonej oraz generowanie oficjalnego raportu ocen.

### ✨ Wyróżniające się cechy techniczne:
* **Relacyjna Baza Danych (SQLite)**: Pełna integracja z bazą danych za pomocą JDBC. Ochrona przed atakami SQL Injection (użycie `PreparedStatement`), obsługa kluczy obcych i rekonstrukcja relacji wiele-do-wielu (Nauczyciel-Przedmiot) w pamięci RAM.
* **Generowanie Raportów PDF (iText)**: Możliwość wygenerowania przez studenta estetycznego raportu swoich ocen do zewnętrznego pliku `.pdf`.
* **Architektura OOP**: Hermetyzacja danych, dziedziczenie (wykorzystanie klasy abstrakcyjnej `Osoba`) i dbałość o separację logiki bazy danych od interfejsu.

## 🛠 Wykorzystane technologie

* **Java 17+**
* **Maven** (Zarządzanie pakietami i cyklem życia projektu)
* **SQLite JDBC** (Trwały zapis danych)
* **iTextPDF 5.5** (Obsługa formatu PDF)

## ⚙️ Jak uruchomić projekt?

1. Sklonuj repozytorium na swój komputer:
   ```bash
   git clone [https://github.com/wlazlowsky/ProjektStudiaMaven](https://github.com/wlazlowsky/ProjektStudiaMaven)
   ```
2. Otwórz projekt w wybranym środowisku IDE (np. IntelliJ IDEA, Eclipse).

3. Odśwież Mavena (Przeładuj pom.xml), aby pobrać niezbędne zależności (sqlite-jdbc oraz itextpdf).

4. Uruchom klasę główną main.java. Aplikacja automatycznie zainicjuje strukturę tabel i utworzy lokalny plik bazy danych dziennik.db przy pierwszym starcie.
