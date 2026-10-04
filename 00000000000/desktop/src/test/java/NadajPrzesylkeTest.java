import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testy okna utworzonego w GUI Designerze. Kontrolki są wyszukiwane w drzewie komponentów
 * po ich treści, a zdarzenia wywoływane przez doClick() - tak jak zrobiłby to użytkownik.
 * Testy działają w trybie headless, ponieważ tworzony jest tylko panel, bez okna.
 */
class NadajPrzesylkeTest {

    private final List<String> wyswietloneKomunikaty = new ArrayList<>();
    private JComponent panelGlowny;

    @BeforeEach
    void utworzOkno() {
        panelGlowny = new NadajPrzesylke(wyswietloneKomunikaty::add).$$$getRootComponent$$$();
    }

    @Test
    void tytulOknaZawieraNumerZdajacego() {
        assertEquals("Nadaj Przesyłkę, PESEL: 00000000000", NadajPrzesylke.TYTUL_OKNA);
    }

    @Test
    void stanPoczatkowy() {
        assertTrue(poleRadio("Pocztówka").isSelected());
        assertFalse(poleRadio("List").isSelected());
        assertFalse(poleRadio("Paczka").isSelected());
        assertEquals("Cena: ", etykietaCeny().getText());
        assertTrue(nazwaWyswietlanegoObrazu().endsWith("pocztowka.png"));
    }

    @Test
    void etykietaCenyJestPogrubionaIWieksza() {
        Font czcionkaCeny = etykietaCeny().getFont();
        Font czcionkaPrzycisku = przycisk("Sprawdź Cenę").getFont();
        assertTrue(czcionkaCeny.isBold());
        assertTrue(czcionkaCeny.getSize() > czcionkaPrzycisku.getSize());
    }

    @Test
    void polaRadioDzialajaWGrupie() {
        poleRadio("List").doClick();
        poleRadio("Paczka").doClick();
        assertTrue(poleRadio("Paczka").isSelected());
        assertFalse(poleRadio("List").isSelected());
        assertFalse(poleRadio("Pocztówka").isSelected());
    }

    @Test
    void sprawdzenieCenyNiePoszerzaOkna() {
        Dimension rozmiarPoczatkowy = panelGlowny.getPreferredSize();
        for (String rodzaj : List.of("Pocztówka", "List", "Paczka")) {
            poleRadio(rodzaj).doClick();
            przycisk("Sprawdź Cenę").doClick();
            assertEquals(rozmiarPoczatkowy, panelGlowny.getPreferredSize(), rodzaj);
        }
    }

    @Test
    void wszystkieKontrolkiZTresciZadaniaIstnieja() {
        for (String tekst : List.of("Ulica z numerem", "Kod pocztowy", "Miasto")) {
            JLabel etykieta = znajdz(JLabel.class, l -> tekst.equals(l.getText()));
            assertTrue(etykieta.getLabelFor() instanceof JTextField, "Etykieta " + tekst + " bez pola");
        }
        przycisk("Zatwierdź");
    }

    @ParameterizedTest
    @CsvSource({
            "Pocztówka, Cena: 1 zł,   pocztowka.png",
            "List,      'Cena: 1,5 zł', list.png",
            "Paczka,    Cena: 10 zł,  paczka.png"
    })
    void sprawdzCeneWyswietlaObrazICene(String rodzaj, String oczekiwanaCena, String oczekiwanyObraz) {
        poleRadio(rodzaj).doClick();
        przycisk("Sprawdź Cenę").doClick();

        assertEquals(oczekiwanaCena, etykietaCeny().getText());
        assertTrue(nazwaWyswietlanegoObrazu().endsWith(oczekiwanyObraz),
                "Wyświetlany obraz: " + nazwaWyswietlanegoObrazu());
    }

    @ParameterizedTest
    @CsvSource({
            "65124,  Dane przesyłki zostały wprowadzone",
            "6512,   Nieprawidłowa liczba cyfr w kodzie pocztowym",
            "651245, Nieprawidłowa liczba cyfr w kodzie pocztowym",
            "65a24,  Kod pocztowy powinien się składać z samych cyfr"
    })
    void zatwierdzWyswietlaKomunikatWalidacji(String kod, String oczekiwanyKomunikat) {
        poleKodPocztowego().setText(kod);
        przycisk("Zatwierdź").doClick();

        assertEquals(List.of(oczekiwanyKomunikat), wyswietloneKomunikaty);
    }

    private JRadioButton poleRadio(String tekst) {
        return znajdz(JRadioButton.class, p -> tekst.equals(p.getText()));
    }

    private JButton przycisk(String tekst) {
        return znajdz(JButton.class, p -> tekst.equals(p.getText()));
    }

    private JLabel etykietaCeny() {
        return znajdz(JLabel.class, l -> l.getText() != null && l.getText().startsWith("Cena:"));
    }

    private String nazwaWyswietlanegoObrazu() {
        JLabel etykietaObrazu = znajdz(JLabel.class, l -> l.getIcon() != null);
        return ((ImageIcon) etykietaObrazu.getIcon()).getDescription();
    }

    private JTextField poleKodPocztowego() {
        JLabel etykieta = znajdz(JLabel.class, l -> "Kod pocztowy".equals(l.getText()));
        return (JTextField) etykieta.getLabelFor();
    }

    private <T extends Component> T znajdz(Class<T> typ, Predicate<T> warunek) {
        List<T> znalezione = new ArrayList<>();
        szukaj(panelGlowny, typ, warunek, znalezione);
        assertEquals(1, znalezione.size(), "Oczekiwano dokładnie jednej kontrolki " + typ.getSimpleName());
        return znalezione.get(0);
    }

    private static <T extends Component> void szukaj(Container kontener, Class<T> typ,
                                                     Predicate<T> warunek, List<T> znalezione) {
        for (Component komponent : kontener.getComponents()) {
            if (typ.isInstance(komponent) && warunek.test(typ.cast(komponent))) {
                znalezione.add(typ.cast(komponent));
            }
            if (komponent instanceof Container && !(komponent instanceof AbstractButton)) {
                szukaj((Container) komponent, typ, warunek, znalezione);
            }
        }
    }
}
