
import pl.edu.agh.dsrg.sr.protos.BankOperationProtos;

import java.io.IOException;
import java.net.*;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {

        DatagramSocket socket= null;

        socket=new DatagramSocket();


        BankOperationProtos.BankOperation operation;

        for(int i=0;i<2000;i++) {

            operation = BankOperationProtos.BankOperation.newBuilder()
                    .setValue(Math.random() / 100 + 1.0)
                    .setType(BankOperationProtos.BankOperation.OperationType.MULTIPLY).build();

            byte[] buffer= operation.toByteArray();
            InetAddress raw_stream= InetAddress.getByName("224.0.0.7");

            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, raw_stream , 6789);
            socket.send(datagramPacket);

            Thread.sleep((long)(Math.random()*10));
        }
    }
}
