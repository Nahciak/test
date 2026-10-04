import math

GORNA_GRANICA = 100


# *******************************************************
#    nazwa funkcji:       wypelnij_sito
#    parametry wejściowe: sito - lista wartości logicznych indeksowana od 0 do n,
#                         przechowuje informację, czy dana liczba jest pierwsza
#    wartość zwracana:    brak (funkcja modyfikuje przekazaną listę)
#    informacje:          funkcja realizuje algorytm sita Eratostenesa: dla każdej
#                         liczby i niewiększej niż pierwiastek z n, która nie została
#                         wykreślona, wykreśla jej wielokrotności większe od niej samej
#                         (ustawia wartość False). Po zakończeniu działania wartość True
#                         pod indeksem i oznacza, że liczba i jest pierwsza
#    autor:               00000000000
# *******************************************************
def wypelnij_sito(sito):
    gorna_granica = len(sito) - 1
    for i in range(2, int(math.sqrt(gorna_granica)) + 1):
        if sito[i]:
            for j in range(2 * i, gorna_granica + 1, i):
                sito[j] = False


def main():
    sito = [True] * (GORNA_GRANICA + 1)
    wypelnij_sito(sito)

    liczby_pierwsze = [i for i in range(2, GORNA_GRANICA + 1) if sito[i]]
    print(f"Liczby pierwsze w przedziale 2..{GORNA_GRANICA}:")
    print(", ".join(str(liczba) for liczba in liczby_pierwsze))


if __name__ == "__main__":
    main()
