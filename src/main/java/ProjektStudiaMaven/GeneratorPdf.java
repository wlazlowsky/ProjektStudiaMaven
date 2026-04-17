package ProjektStudiaMaven;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class GeneratorPdf {

    public static void generujRaportStudenta(Student student, String sciezka) {
        Document dokument = new Document();
        try {
            PdfWriter.getInstance(dokument, new FileOutputStream(sciezka));
            dokument.open();
            dokument.add(new Paragraph("Raport ocen studenta: " + student.getImie() + " " + student.getNazwisko() + "."));
            PdfPTable tabela = new PdfPTable(3);
            tabela.addCell("Przedmiot");
            tabela.addCell("Ocena");
            tabela.addCell("Waga");
            for (Ocena ocena : student.getListaOcen()){
                tabela.addCell(ocena.getPrzedmiot().getNazwa());
                tabela.addCell(String.valueOf(ocena.getWartosc()));
                tabela.addCell(String.valueOf(ocena.getWaga()));
            }
            dokument.add(tabela);
            System.out.println("Plik zostal pomyslnie wygenerowany pod sciezka: " + sciezka);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (dokument.isOpen()) {
                dokument.close();
            }
        }
    }
}
