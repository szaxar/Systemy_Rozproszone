package bookstore;


import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class BookstoreOrder extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String[] message = s.split(" ");
                    if (message.length == 2) {
                        File order = new File("Resources/order.txt");
                        synchronized (order) {
                            if (order.exists()) {
                                FileWriter file = new FileWriter(order.getAbsolutePath(), true);
                                BufferedWriter out = new BufferedWriter(file);

                                out.write(message[1] + "\n");
                                getSender().tell("order1 Done", getSelf());
                                out.close();
                            } else {
                                throw new FileNotFoundException("Database order don't exist");
                            }
                        }
                    } else {
                        throw new IllegalArgumentException("Wrong number of request");
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

}
