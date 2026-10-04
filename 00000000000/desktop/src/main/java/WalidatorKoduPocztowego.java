/**
 * Walidacja kodu pocztowego. Dla uproszczenia kod składa się z 5 cyfr (bez znaku '-').
 */
public final class WalidatorKoduPocztowego {

    public static final int DLUGOSC_KODU_POCZTOWEGO = 5;

    public static final String KOMUNIKAT_POPRAWNY = "Dane przesyłki zostały wprowadzone";
    public static final String KOMUNIKAT_ZLA_DLUGOSC = "Nieprawidłowa liczba cyfr w kodzie pocztowym";
    public static final String KOMUNIKAT_NIE_CYFRY = "Kod pocztowy powinien się składać z samych cyfr";

    private WalidatorKoduPocztowego() {
    }

    /**
     * Sprawdza kod pocztowy i zwraca komunikat, który należy wyświetlić użytkownikowi.
     *
     * @param kodPocztowy tekst wpisany w pole kodu pocztowego
     * @return treść komunikatu zgodna z wynikiem walidacji
     */
    public static String sprawdz(String kodPocztowy) {
        if (kodPocztowy == null || kodPocztowy.length() != DLUGOSC_KODU_POCZTOWEGO) {
            return KOMUNIKAT_ZLA_DLUGOSC;
        }
        if (!zawieraSameCyfry(kodPocztowy)) {
            return KOMUNIKAT_NIE_CYFRY;
        }
        return KOMUNIKAT_POPRAWNY;
    }

    private static boolean zawieraSameCyfry(String tekst) {
        for (char znak : tekst.toCharArray()) {
            if (znak < '0' || znak > '9') {
                return false;
            }
        }
        return true;
    }
}
