import io
import unittest
from contextlib import redirect_stdout

from sito_eratostenesa import GORNA_GRANICA, main, wypelnij_sito

LICZBY_PIERWSZE_DO_100 = [
    2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47,
    53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
]


def liczby_pierwsze_z_sita(sito):
    return [i for i in range(2, len(sito)) if sito[i]]


class TestWypelnijSito(unittest.TestCase):

    def test_liczby_pierwsze_do_100(self):
        sito = [True] * 101
        wypelnij_sito(sito)
        self.assertEqual(liczby_pierwsze_z_sita(sito), LICZBY_PIERWSZE_DO_100)

    def test_funkcja_nie_zwraca_wartosci(self):
        self.assertIsNone(wypelnij_sito([True] * 11))

    def test_kwadraty_liczb_pierwszych_sa_wykreslone(self):
        sito = [True] * 101
        wypelnij_sito(sito)
        for kwadrat in (4, 9, 25, 49):
            self.assertFalse(sito[kwadrat])

    def test_male_zakresy(self):
        przypadki = {2: [2], 3: [2, 3], 4: [2, 3], 10: [2, 3, 5, 7], 11: [2, 3, 5, 7, 11]}
        for n, oczekiwane in przypadki.items():
            with self.subTest(n=n):
                sito = [True] * (n + 1)
                wypelnij_sito(sito)
                self.assertEqual(liczby_pierwsze_z_sita(sito), oczekiwane)

    def test_zgodnosc_z_dzieleniem_proby(self):
        n = 1000
        sito = [True] * (n + 1)
        wypelnij_sito(sito)
        for liczba in range(2, n + 1):
            jest_pierwsza = all(liczba % d for d in range(2, int(liczba ** 0.5) + 1))
            self.assertEqual(sito[liczba], jest_pierwsza, liczba)


class TestMain(unittest.TestCase):

    def test_zakres_zgodny_z_trescia_zadania(self):
        self.assertEqual(GORNA_GRANICA, 100)

    def test_wyswietla_komunikat_i_liczby_pierwsze(self):
        wyjscie = io.StringIO()
        with redirect_stdout(wyjscie):
            main()
        komunikat, liczby = wyjscie.getvalue().strip().split("\n")
        self.assertEqual(komunikat, "Liczby pierwsze w przedziale 2..100:")
        self.assertEqual([int(x) for x in liczby.split(", ")], LICZBY_PIERWSZE_DO_100)


if __name__ == "__main__":
    unittest.main()
