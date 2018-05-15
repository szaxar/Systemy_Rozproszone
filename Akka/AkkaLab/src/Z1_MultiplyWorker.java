import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z1_MultiplyWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);    
    Integer counter=0;
    @Override
    public AbstractActor.Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, s -> {
                        counter++;
                        String result = Multiply(s);
                        getSender().tell("result: " + result + "(opration count: "+counter+")", getSelf());
                    })
                    .matchAny(o -> log.info("received unknown message"))
                    .build();
    }
    
    private String Multiply(String s){
        String[] split = s.split(" ");
        int a = Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[2]);
        return (a*b) + "";
    }
}
