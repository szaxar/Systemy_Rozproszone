import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_AppLocalSender extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {

                    getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/remote").tell(s  , getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

}
