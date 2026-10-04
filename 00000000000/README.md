# INF.04 – czerwiec 2023 (arkusz INF.04-01-23.06-SG)

Rozwiązanie zadania egzaminacyjnego. Numer zdającego: `00000000000`.

```
00000000000/
├── konsola/        sito Eratostenesa (Python) + testy unittest, konsola.zip
├── desktop/        „Nadaj Przesyłkę” (Java Swing, IntelliJ GUI Designer) + testy JUnit 5, desktop.zip
└── dokumentacja/   zrzuty konsola1, desktop1–desktop6 oraz egzamin.docx
```

## Część I – aplikacja konsolowa (Python)

```
cd konsola
python3 sito_eratostenesa.py      # liczby pierwsze 2..100
python3 -m unittest -v            # 7 testów
```

Wypełnianie tablicy realizuje funkcja `wypelnij_sito(sito)` – przyjmuje listę, nic nie zwraca;
nad funkcją znajduje się nagłówek dokumentacji zgodny z listingiem 1.

## Część II – aplikacja desktopowa (Java + Swing)

Projekt Maven – w IntelliJ IDEA: *File → Open → `desktop/pom.xml`*.

| Plik | Rola |
|------|------|
| `src/main/java/NadajPrzesylke.form` | układ okna z IntelliJ GUI Designer (GridBagLayout) |
| `src/main/java/NadajPrzesylke.java` | klasa powiązana z formularzem: obsługa kliknięć „Sprawdź Cenę” i „Zatwierdź” |
| `src/main/java/RodzajPrzesylki.java` | obraz i cena dla Pocztówki / Listu / Paczki |
| `src/main/java/WalidatorKoduPocztowego.java` | walidacja kodu (5 cyfr) i treść komunikatów |
| `src/main/resources/*.png` | obrazy w zasobach aplikacji |
| `src/test/java/*Test.java` | testy JUnit 5 (logika + testy GUI z klikaniem przycisków) |

Kod `$$$setupUI$$$()` jest wygenerowany do źródła, więc projekt kompiluje się także poza IntelliJ.
Aby IntelliJ dalej aktualizował go po zmianach w formularzu, ustaw
*Settings → Editor → GUI Designer → Generate GUI into: **Java source code***.

```
cd desktop
mvn package                       # kompilacja + 38 testów JUnit, wynik: target/NadajPrzesylke.jar
java -jar NadajPrzesylkeApp.jar   # uruchomienie gotowego pliku wykonywalnego
```
