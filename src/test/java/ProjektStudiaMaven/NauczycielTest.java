package ProjektStudiaMaven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NauczycielTest {
    @Test
    public void testNauczyciel() {
        Nauczyciel n = new Nauczyciel("Maria", "Nowak");
        Student s = new Student("Jan", "Kowalski");
        Przedmiot p = new Przedmiot("Matematyka");

        n.wystawOcene(p, s, 3, 2);
        n.wystawOcene(p, s, 3, 2);

        assertEquals(2, s.getListaOcen().size());
    }
}
