import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RodzajPrzesylkiTest {

    @Test
    void cenyZgodneZTresciaZadania() {
        assertEquals("Cena: 1 zł", RodzajPrzesylki.POCZTOWKA.getOpisCeny());
        assertEquals("Cena: 1,5 zł", RodzajPrzesylki.LIST.getOpisCeny());
        assertEquals("Cena: 10 zł", RodzajPrzesylki.PACZKA.getOpisCeny());
    }

    @Test
    void obrazyZgodneZTresciaZadania() {
        assertEquals("pocztowka.png", RodzajPrzesylki.POCZTOWKA.getNazwaObrazu());
        assertEquals("list.png", RodzajPrzesylki.LIST.getNazwaObrazu());
        assertEquals("paczka.png", RodzajPrzesylki.PACZKA.getNazwaObrazu());
    }

    @ParameterizedTest
    @EnumSource(RodzajPrzesylki.class)
    void obrazJestWZasobachAplikacji(RodzajPrzesylki rodzaj) {
        assertNotNull(getClass().getResource("/" + rodzaj.getNazwaObrazu()),
                "Brak pliku " + rodzaj.getNazwaObrazu() + " w zasobach");
    }
}
