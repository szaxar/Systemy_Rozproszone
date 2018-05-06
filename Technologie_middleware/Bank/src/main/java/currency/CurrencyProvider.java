package currency;

import grpc.gen.*;
import io.grpc.stub.StreamObserver;

import java.util.Random;

public class CurrencyProvider extends CurrencyStreamGrpc.CurrencyStreamImplBase {
    @Override
    public void subscribeCurrencyValue(CurrencyMsgRequest msgRequest, StreamObserver<CurrencyMsgReturn> msgReturnObserver){
        System.out.println("Subscription for currency: " + msgRequest.getCurrency().name());
        float currencyStart = 1;
        if(msgRequest.getCurrency().equals(Currency.EUR))
            currencyStart = (float) 4.3;
        if(msgRequest.getCurrency().equals(Currency.USD))
            currencyStart = (float) 3.5;
        if(msgRequest.getCurrency().equals(Currency.GBP))
            currencyStart = (float) 5.3;
        while(true){
            try {
                Thread.sleep(new Random().nextInt(2000)+5000);

                if(!msgRequest.getCurrency().equals(Currency.PLN)) {
                    float currencyDifference = (float) (new Random().nextInt(1000) / 100000.0);
                    if(new Random().nextInt(2) == 1)
                        currencyStart += currencyDifference;
                    else
                        currencyStart -= currencyDifference;
                }
                CurrencyMsgReturn currencyMsgReturn = CurrencyMsgReturn.newBuilder().setCurrencyValue(currencyStart).build();
                msgReturnObserver.onNext(currencyMsgReturn);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
