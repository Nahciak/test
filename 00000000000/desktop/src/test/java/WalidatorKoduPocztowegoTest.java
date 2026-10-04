import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalidatorKoduPocztowegoTest {

    @ParameterizedTest
    @ValueSource(strings = {"65124", "00000", "99999", "01234"})
    @DisplayName("Kod z 5 cyfr jest poprawny")
    void poprawnyKod(String kod) {
        assertEquals("Dane przesyłki zostały wprowadzone", WalidatorKoduPocztowego.sprawdz(kod));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1", "1234", "123456", "65-124", "abcd", "abcdef"})
    @DisplayName("Kod o długości innej niż 5 znaków ma nieprawidłową liczbę cyfr")
    void zlaLiczbaZnakow(String kod) {
        assertEquals("Nieprawidłowa liczba cyfr w kodzie pocztowym", WalidatorKoduPocztowego.sprawdz(kod));
    }

    @ParameterizedTest
    @ValueSource(strings = {"6512a", "a5124", "65 24", "6-124", "abcde", "1234.", "１２３４５"})
    @DisplayName("Kod z 5 znaków, w którym co najmniej jeden nie jest cyfrą, jest odrzucany")
    void znakiInneNizCyfry(String kod) {
        assertEquals("Kod pocztowy powinien się składać z samych cyfr", WalidatorKoduPocztowego.sprawdz(kod));
    }

    @Test
    @DisplayName("Długość jest sprawdzana przed rodzajem znaków")
    void dlugoscMaPierwszenstwo() {
        assertEquals(WalidatorKoduPocztowego.KOMUNIKAT_ZLA_DLUGOSC, WalidatorKoduPocztowego.sprawdz("ab"));
    }
}
