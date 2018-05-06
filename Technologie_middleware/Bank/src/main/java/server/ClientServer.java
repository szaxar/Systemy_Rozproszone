package server;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Exception;
import com.zeroc.Ice.Object;
import grpc.gen.Currency;

import java.util.HashMap;

public class ClientServer implements Runnable {

    private String[] args;
    private HashMap<String, Float> currencies;
    private String port;

    public ClientServer(String[] args, HashMap<String, Float> currencies,String port) {
        this.args = args;
        this.currencies = currencies;
        this.port=port;
    }

    @Override
    public void run() {
        int status = 0;
        Communicator communicator = null;

        HashMap<Integer, Client> clients = new HashMap<Integer, Client>();
        try {
            // 1. Inicjalizacja ICE - utworzenie communicatora

            communicator = Util.initialize(args);

            // 2. Konfiguracja adaptera
            // METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
            //Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

            // METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie źródłowym
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter", "tcp -h localhost -p "+port+":udp -h localhost -p "+port);

            // 3. Stworzenie serwanta/serwantów
            Object object = new ClientStandardImplementation(clients);
            Object object2 = new ClientPremiumImplementation(clients,currencies);

            // 4. Dodanie wpisów do tablicy ASM
            adapter.add(object, new Identity("client", null));
            adapter.add(object2, new Identity("client1", null));

            // 5. Aktywacja adaptera i przejście w pętlę przetwarzania żądań
            adapter.activate();

            System.out.println("Entering event processing loop...");

            communicator.waitForShutdown();

        } catch (Exception e) {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null) {
            // Clean up
            //
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }
}
