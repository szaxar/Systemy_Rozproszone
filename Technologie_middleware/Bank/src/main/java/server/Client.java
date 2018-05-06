package server;

public class Client {

    private int PESEL;
    private String name;
    private int monthlyInflows;
    private int GUID;
    private boolean isPremium;
    private float balance;

    public Client(int PESEL, String name, int monthlyInflows , int GUID , boolean isPremium,float balance) {
        this.PESEL = PESEL;
        this.name=name;
        this.monthlyInflows = monthlyInflows;
        this.isPremium=isPremium;
        this.balance=balance;
    }

    public void setGUID(int GUID) {
        this.GUID = GUID;
    }


    public int getPESEL() {
        return PESEL;
    }

    public String getName() {
        return name;
    }


    public int getMonthlyInflows() {
        return monthlyInflows;
    }

    public int getGUID() {
        return GUID;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float value) {
        this.balance=value;
    }

    public boolean isPremium() {
        return isPremium;
    }
}
