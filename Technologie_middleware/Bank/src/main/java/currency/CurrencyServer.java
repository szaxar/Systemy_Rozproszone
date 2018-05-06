package currency;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class CurrencyServer {
    private static final Logger logger = Logger.getLogger(CurrencyServer.class.getName());

    private int port = 50052;
    private Server server;

    private void start() throws IOException, InterruptedException {
        server = ServerBuilder.forPort(port)
                .addService(new CurrencyProvider())
                .build()
                .start();
        logger.info("Server started");
        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                CurrencyServer.this.stop();
                System.err.println("server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final CurrencyServer server = new CurrencyServer();
        server.start();
        server.blockUntilShutdown();
    }

}

