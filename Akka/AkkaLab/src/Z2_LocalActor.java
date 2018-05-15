import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder().match(String.class, s -> {
            // TODO
            if (s.startsWith("s")) {
                context().child("sender").get().tell(s, getSelf()); // send task to child
            }
            else{
                System.out.println(s);
            }
        }).matchAny(o -> log.info("received unknown message"))
                    .build();

    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(Z2_AppLocalSender.class), "sender");
    }
}
