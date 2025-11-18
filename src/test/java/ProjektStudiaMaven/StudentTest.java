package ProjektStudiaMaven;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {
    @Test
    void testObliczaniaSredniej() {
        Student student = new Student("Jan", "Kowalski");
        Przedmiot przedmiot = new Przedmiot("Matematyka");
        student.addOcena(new Ocena(przedmiot, 2, 3));
        student.addOcena(new Ocena(przedmiot, 5, 1));
        student.addOcena(new Ocena(przedmiot, 3, 2));

        double spodziewanaWartosc = 2.83;
        double wynikMetody = student.obliczSrednia(przedmiot);
        assertEquals(spodziewanaWartosc,wynikMetody, 0.01);
    }

    @Test
    void testBrakOcen(){
        Student student = new Student("Jan", "Kowalski");
        Przedmiot przedmiot = new Przedmiot("Matematyka");
        Przedmiot przedmiot2 = new Przedmiot("Jezyk Polski");

        assertTrue(student.getListaOcen().isEmpty());

        student.addOcena(new Ocena(przedmiot2, 2, 3));

        double spodziewanaWartosc = 0;
        double wynikMetody = student.obliczSrednia(przedmiot);

        assertEquals(spodziewanaWartosc,wynikMetody);
    }
}
