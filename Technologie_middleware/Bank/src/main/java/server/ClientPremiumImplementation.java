package server;

import Client.AccountData;
import Client.Currency;
import Client.PremiumAccountOperation;
import Client.accountType;
import com.zeroc.Ice.Current;
import Client.CurrenciesValue;
import java.util.HashMap;

public class ClientPremiumImplementation implements PremiumAccountOperation {

    HashMap<Integer, Client> clients;
    private HashMap<String, Float> currencies;

    public ClientPremiumImplementation(HashMap<Integer, Client> clients, HashMap<String, Float> currencies) {
        this.clients = clients;
        this.currencies = currencies;
    }


    @Override
    public CurrenciesValue checkCreditPremium(int PESEL, int GUID, int value, int time, Currency currency, Current current) {
        if (clients.get(GUID) == null) {
            return null;
        }
        Client client = clients.get(GUID);
        if (client.getPESEL() != PESEL) {
            return null;
        }

        if(!currencies.containsKey(currency.name())){
                return null;
        }

            float value1= calculatePricePremium(time, value,currency);
            float value2=calculatePrice(time , value);
            CurrenciesValue currenciesValue= new CurrenciesValue(value2,value1);
            return currenciesValue;

    }

    @Override
    public int getCreditPremium(int PESEL, int GUID, int value, int time, Currency currency, Current current) {
        if (clients.get(GUID) == null) {
            return -1;
        }
        Client client = clients.get(GUID);
        if (client.getPESEL() != PESEL) {
            return -1;
        } else {
            if(!currencies.containsKey(currency.name())){
                return -1;
            }
            client.setBalance(clients.get(GUID).getBalance() + value*currencies.get(currency.name()));
        }
        return 0;
    }

    @Override
    public AccountData addClient(String name, int PESEL, int monthlyInflows, Current current) {
        return null;
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
            balance = clients.get(GUID).getBalance();
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
        else {
            return null;
        }
    }

    private int calculatePrice(int time, int value) {
        return time * value;
    }

    private float calculatePricePremium(int time, int value,Currency currency) {
        return time*value*currencies.get(currency.name());
    }
}
