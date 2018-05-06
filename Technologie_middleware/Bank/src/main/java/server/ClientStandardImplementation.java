package server;

import Client.AccountData;
import Client.ClientOperation;
import Client.accountType;
import com.zeroc.Ice.Current;

import java.util.HashMap;

public class ClientStandardImplementation implements ClientOperation {

    HashMap<Integer, Client> clients;

    public ClientStandardImplementation(HashMap<Integer, Client> clients) {
        this.clients = clients;
    }

    @Override
    public AccountData addClient(String name, int PESEL, int monthlyInflows, Current current) {
        int GUID = clients.size();
        AccountData accountData = new AccountData();
        accountData.GUID = GUID;
        Client client;
        if (monthlyInflows > 500) {
            client = new Client(PESEL, name, monthlyInflows, GUID, true, 0);
            accountData.type = accountType.PREMIUM;
        } else {
            client = new Client(PESEL, name, monthlyInflows, GUID, false, 0);
            accountData.type = accountType.STANDARD;
        }
        clients.put(GUID, client);
        return accountData;
    }

    @Override
    public float checkCredit(int PESEL, int GUID, int value, int time, Current current) {
        if (clients.get(GUID) == null) {
            return -1;
        }
        Client client = clients.get(GUID);
        if (client.getPESEL() != PESEL) {
            return -1;
        } else {
            return calculatePrice(time, value);
        }
    }

    @Override
    public int getCredit(int PESEL, int GUID, int value, int time, Current current) {
        if (clients.get(GUID) == null) {
            return -1;
        }
        Client client = clients.get(GUID);
        if (client.getPESEL() != PESEL) {
            return -1;
        } else {
            client.setBalance(clients.get(GUID).getBalance() + value);
        }

        return 0;
    }

    @Override
    public float getBalance(int PESEL, int GUID, Current current) {
        float balance = -1;
        if (clients.get(GUID) == null) {
            return -2;
        }
        if (clients.get(GUID).getPESEL() == PESEL) {
            balance =  clients.get(GUID).getBalance();
        }
        return balance;
    }

    @Override
    public accountType login(int PESEL, int GUID, Current current) {
        if (clients.get(GUID) == null) {
            return null;
        }
        if (clients.get(GUID).getPESEL() == PESEL) {
            if(clients.get(GUID).isPremium()) return accountType.PREMIUM;
            else return accountType.STANDARD;
        }
        return null;
    }

    private float calculatePrice(int time, int value) {
        return time * value;
    }
}
