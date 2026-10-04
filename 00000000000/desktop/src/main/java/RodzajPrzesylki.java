/**
 * Rodzaje przesyłek obsługiwane przez aplikację wraz z przypisanym obrazem i ceną.
 */
public enum RodzajPrzesylki {
    POCZTOWKA("pocztowka.png", "Cena: 1 zł"),
    LIST("list.png", "Cena: 1,5 zł"),
    PACZKA("paczka.png", "Cena: 10 zł");

    private final String nazwaObrazu;
    private final String opisCeny;

    RodzajPrzesylki(String nazwaObrazu, String opisCeny) {
        this.nazwaObrazu = nazwaObrazu;
        this.opisCeny = opisCeny;
    }

    public String getNazwaObrazu() {
        return nazwaObrazu;
    }

    public String getOpisCeny() {
        return opisCeny;
    }
}
