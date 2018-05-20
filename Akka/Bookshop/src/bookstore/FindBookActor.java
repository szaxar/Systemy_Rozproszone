package bookstore;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FindBookActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    Boolean isFound = false;
                    String[] message = s.split(" ");

                    File file = new File(message[0]);
                    Scanner in = null;
                    try {
                        in = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String line;

                    do {
                        line = in.nextLine();
                        if (line.split(" ")[0].equals(message[1])) {
                            isFound = true;
                            break;
                        }
                    } while (in.hasNext());
                    if (isFound) context().parent().forward("answear " + line, context());
                    else context().parent().forward("answear brak", context());

                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
