package server;

import grpc.gen.Currencies;
import grpc.gen.Currency;

import java.io.IOException;
import java.util.*;


public class Server {
    public static void main(String[] args) throws IOException {

        HashMap<String,Float> currencies  =new HashMap<>();
        ArrayList<Currency> currencies1=new ArrayList<>();
        currencies.put(Currency.PLN.name(), (float) 1);

        String line = null;
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println("Write port");
        String port=in.readLine();

        do{
            System.out.println("Write currencies (EUR,USD,GBP)");
            line=in.readLine();
            if(!line.equals("x")) {
                currencies.put(line, (float) 0);
                currencies1.add(Currency.valueOf(line));
            }
        }while(!line.equals("x"));


        ArrayList<Thread> threads=new ArrayList<>();

        for(Currency currency:currencies1) {
            threads.add(new Thread(new CurrencyServer("localhost", 50052,currency,currencies)));
        }
        Thread thread2=new Thread(new ClientServer(args,currencies,port));

        for(Thread thread:threads){
            thread.start();
        }
        thread2.start();
    }
}








