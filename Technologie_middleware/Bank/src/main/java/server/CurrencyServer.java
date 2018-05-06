package server;

import grpc.gen.Currency;
import grpc.gen.CurrencyMsgRequest;
import grpc.gen.CurrencyMsgReturn;
import grpc.gen.CurrencyStreamGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrencyServer implements Runnable {

    private final Currency currency;
    private CurrencyStreamGrpc.CurrencyStreamBlockingStub currencyStreamBlockingStub;
    private HashMap<String,Float> currencies;

    private static final java.util.logging.Logger logger = Logger.getLogger(CurrencyServer.class.getName());
    private final ManagedChannel channel;
    private final CurrencyStreamGrpc.CurrencyStreamStub currencyStreamNonBlockingStub;

    public CurrencyServer(String host, int port, Currency currency, HashMap<String,Float> currencies) {
        this.currency=currency;
        this.currencies=currencies;

        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        this.currencyStreamBlockingStub = CurrencyStreamGrpc.newBlockingStub(channel);
        this.currencyStreamNonBlockingStub = CurrencyStreamGrpc.newStub(channel);
    }

    @Override
    public void run() {
        CurrencyMsgRequest request = CurrencyMsgRequest.newBuilder().setCurrency(currency).build();

        Iterator<CurrencyMsgReturn> numbers;
        try {
            numbers = currencyStreamBlockingStub.subscribeCurrencyValue(request);
            while (numbers.hasNext()) {
                CurrencyMsgReturn num = numbers.next();
                System.out.println(currency.getValueDescriptor().getName()+" : " + num.getCurrencyValue());
                currencies.put(currency.name(), num.getCurrencyValue());
            }
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }
}
