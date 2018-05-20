package bookstore;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.File;
import java.io.FileNotFoundException;

public class BookstoreFind extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    int counter=0;
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if(s.startsWith("answear")){
                        String[] message=s.split(" ");
                        if(!message[1].equals("brak")) {
                           getSender().tell("find1 "+message[2], getSelf());
                           context().stop(getSelf());
                        }
                        else{
                            counter++;
                            if(counter==2){
                                getSender().tell("find1 Nie_znaleziono_ksiazki", getSelf());
                                context().stop(getSelf());
                            }
                        }
                    }
                    else {

                        context().actorOf(Props.create(FindBookActor.class), "database1");
                        context().actorOf(Props.create(FindBookActor.class), "database2");

                        String[] message = s.split(" ");
                        if (message.length == 2) {
                            File database = new File("Resources/database1.txt");
                            File database1 = new File("Resources/database2.txt");

                            if (database.exists() && database1.exists()) {

                                context().child("database1").get().forward(database.getAbsolutePath() + " " + message[1], context());
                                context().child("database2").get().forward(database1.getAbsolutePath() + " " + message[1], context());

                            } else {
                                throw new FileNotFoundException("Database don't exist");
                            }
                        } else {
                            throw new IllegalArgumentException("Wrong number of request");
                        }
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

}
