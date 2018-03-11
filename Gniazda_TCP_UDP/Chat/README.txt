Zadanie domowe
•Napisać aplikację typu chat (5 pkt.)
– Klienci łączą się serwerem przez protokół TCP
– Serwer przyjmuje wiadomości od każdego klienta i rozsyła je do pozostałych (wraz z id/nickiem klienta)
– Serwer jest wielowątkowy – każde połączenie od klienta powinno mieć swój wątek
– Proszę zwrócić uwagę na poprawną obsługę wątków
•Dodać dodatkowy kanał UDP (3 pkt.)
– Serwer oraz każdy klient otwierają dodatkowy kanał UDP (ten sam numer portu jak przy TCP)
– Po wpisaniu komendy ‘U’ u klienta przesyłana jest wiadomość przez UDP na serwer, który rozsyła ją do pozostałych klientów
