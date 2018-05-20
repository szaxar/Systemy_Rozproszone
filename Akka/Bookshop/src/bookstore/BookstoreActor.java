package bookstore;

import akka.actor.AbstractActor;
import akka.actor.AllForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class BookstoreActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder(
        ).match(String.class, s -> {
            if (s.startsWith("find")) {
                context().actorOf(Props.create(BookstoreFind.class), "find");
                context().child("find").get().forward(s, context()); // send task to child
            } else if (s.startsWith("get")) {
                context().child("get").get().forward(s, context()); // send task to child
            } else if (s.startsWith("order")) {
                context().child("order").get().forward(s, context()); // send task to child
            }
        }).matchAny(o -> log.info("received unknown message"))
                .build();
    }

    @Override
    public void preStart() {
        context().actorOf(Props.create(BookstoreOrder.class), "order");
        context().actorOf(Props.create(BookstoreGet.class), "get");
    }

    private static SupervisorStrategy strategy
            = new AllForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.

            match(IllegalArgumentException.class, o -> resume()).
            match(FileNotFoundException.class, o -> restart()).
            matchAny(o -> restart()).
            build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
