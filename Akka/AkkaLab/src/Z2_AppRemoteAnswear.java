import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_AppRemoteAnswear extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {

                    getContext().actorSelection("akka.tcp://local_system@127.0.0.1:2552/user/local").tell(s.split(" ")[1].toUpperCase()  , getSelf());
                    System.out.println("sended "+s.split(" ")[1].toUpperCase());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

}
