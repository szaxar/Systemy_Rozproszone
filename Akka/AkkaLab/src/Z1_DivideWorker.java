import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.IllegalFormatException;

/**
 * Created by lab on 5/14/2018.
 */
public class Z1_DivideWorker extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    Integer counter=0;
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    counter++;
                    String result = Devide(s);
                    getSender().tell("result: "+ result+"(opration count: "+counter+")" , getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String Devide(String s){
        String[] split = s.split(" ");
        int a = Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[2]);
        if(b==0) throw new ArithmeticException();
        else return (a/b) + "";
    }
}
