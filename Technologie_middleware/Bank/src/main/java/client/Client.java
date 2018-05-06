package client;
// **********************************************************************
//
// Copyright (c) 2003-2016 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************


import Client.*;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Exception;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import java.io.IOException;
import java.rmi.server.UID;

public class Client {
    public static void main(String[] args) {
        int status = 0;
        int GUID = 0;
        int PESEL = 0;
        AccountData accountData = new AccountData();
        Communicator communicator = Util.initialize(args);
        boolean loginFlag = false;
        String line = null;
        java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        try {
            System.out.println("Write port");
            String port=in.readLine();

            communicator = Util.initialize(args);

            ObjectPrx base = communicator.stringToProxy("client:tcp -h localhost -p "+port+":udp -h localhost -p "+port);

            ClientOperationPrx obj = ClientOperationPrx.checkedCast(base);
            if (obj == null) throw new Error("Invalid proxy");


            do {
                System.out.println("Please log in or create an account");
                System.out.print("==> ");
                System.out.flush();
                line = in.readLine();

                if (line.equals("log in")) {
                    System.out.println("Please write GUID");
                    GUID = Integer.parseInt(in.readLine());
                    System.out.println("Please write PESEL");
                    PESEL = Integer.parseInt(in.readLine());
                    accountData.type=obj.login(PESEL, GUID);
                    loginFlag = true;

                }
                if (line.equals("register")) {
                    System.out.println("Write name:");
                    String name = in.readLine();
                    System.out.println("Write PESEL");
                    PESEL = Integer.parseInt(in.readLine());
                    System.out.println("Write Monthly Inflows");
                    int monthlyInflows = Integer.parseInt(in.readLine());
                    accountData = obj.addClient(name, PESEL, monthlyInflows);
                    GUID=accountData.GUID;
                    System.out.println("GUID = " + GUID);
                    loginFlag = true;
                }
                if (line.equals("x")) {
                    break;
                }
                if (loginFlag == true) {
                    if (accountData.type.equals(accountType.STANDARD)) {

                        do {
                            try {
                                System.out.println("Please write operation checkCredit,getCredit,logout,getBalance");
                                System.out.print("==> ");
                                System.out.flush();
                                line = in.readLine();
                                if (line == null) {
                                    break;
                                }
                                if (line.equals("checkCredit")) {
                                    System.out.println("Write how much you want borrow");
                                    int value = Integer.parseInt(in.readLine());
                                    System.out.println("on how much time");
                                    int time = Integer.parseInt(in.readLine());
                                    float r = obj.checkCredit(PESEL, GUID, value, time);
                                    System.out.println("RESULT = " + r);
                                }
                                if (line.equals("getCredit")) {
                                    System.out.println("Write how much you want borrow");
                                    int value = Integer.parseInt(in.readLine());
                                    System.out.println("on how much time");
                                    int time = Integer.parseInt(in.readLine());
                                    int r = obj.getCredit(PESEL, GUID, value, time);
                                    System.out.println("RESULT = " + r);
                                }

                                if (line.equals("getBalance")) {
                                    float r = obj.getBalance(PESEL, GUID);
                                    System.out.println("RESULT = " + r);
                                }
                                if (line.equals("logout")) {
                                    loginFlag = false;
                                    GUID = 0;
                                    PESEL = 0;
                                }
                            } catch (IOException ex) {
                                System.err.println(ex);
                            }
                        }
                        while (!line.equals("logout"));
                    } else {
                        ObjectPrx base1 = communicator.stringToProxy("client1:tcp -h localhost -p 10000:udp -h localhost -p 10000");
                        PremiumAccountOperationPrx obj1 = PremiumAccountOperationPrx.checkedCast(base1);
                        if (obj1 == null) throw new Error("Invalid proxy");

                        do {
                            try {
                                System.out.println("Please write operation checkCredit,getCredit,logout,getBalance");
                                System.out.print("==> ");
                                System.out.flush();
                                line = in.readLine();
                                if (line == null) {
                                    break;
                                }
                                if (line.equals("checkCredit")) {
                                    System.out.println("Write how much you want borrow");
                                    int value = Integer.parseInt(in.readLine());
                                    System.out.println("on how much time");
                                    int time = Integer.parseInt(in.readLine());
                                    System.out.println("Write currency (0-PLN , 1-GBP,2-USD,3-EUR)");
                                    int currency = Integer.parseInt(in.readLine());
                                    Currency currency1 = Currency.valueOf(currency);

                                    CurrenciesValue r = obj1.checkCreditPremium(PESEL, GUID, value, time, currency1);
                                    System.out.println("RESULT = " + r.value + " "+ r.value2);
                                }
                                if (line.equals("getCredit")) {
                                    System.out.println("Write how much you want borrow");
                                    int value = Integer.parseInt(in.readLine());
                                    System.out.println("on how much time");
                                    int time = Integer.parseInt(in.readLine());
                                    System.out.println("Write currency (0-PLN , 1-GBP,2-USD,3-EUR)");
                                    int currency = Integer.parseInt(in.readLine());
                                    Currency currency1 = Currency.valueOf(currency);
                                    int r = obj1.getCreditPremium(PESEL, GUID, value, time, currency1);
                                    System.out.println("RESULT = " + r);
                                }

                                if (line.equals("getBalance")) {
                                    float r = obj1.getBalance(PESEL, GUID);
                                    System.out.println("RESULT = " + r);
                                }
                                if (line.equals("logout")) {
                                    loginFlag = false;
                                    GUID = 0;
                                    PESEL = 0;
                                }
                            } catch (IOException ex) {
                                System.err.println(ex);
                            }
                        }
                        while (!line.equals("logout"));

                    }


                }
            } while (!line.equals("x"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }

}