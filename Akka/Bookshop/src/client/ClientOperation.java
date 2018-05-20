package client;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ClientOperation extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("stream") || s.startsWith("find1") || s.startsWith("order1") || s.startsWith("error")) {
                        System.out.println(s.split(" ")[1]);
                    } else {
                        getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/bookstore").tell(s, getSelf());
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

}
