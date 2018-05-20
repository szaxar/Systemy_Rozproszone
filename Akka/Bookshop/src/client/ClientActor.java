package client;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder().match(String.class, s -> {
            if (s.startsWith("find")) {
                context().child("find").get().tell(s, getSelf()); // send task to child
            } else if (s.startsWith("order")) {
                context().child("order").get().tell(s, getSelf()); // send task to child
            } else if (s.startsWith("get")) {
                context().child("get").get().tell(s, getSelf()); // send task to child
            }
        }).matchAny(o -> log.info("received unknown message"))
                .build();
    }

    @Override
    public void preStart() {
        context().actorOf(Props.create(ClientOperation.class), "find");
        context().actorOf(Props.create(ClientOperation.class), "order");
        context().actorOf(Props.create(ClientOperation.class), "get");
    }
}
