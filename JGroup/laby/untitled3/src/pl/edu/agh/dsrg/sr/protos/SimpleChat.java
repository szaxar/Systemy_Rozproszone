package pl.edu.agh.dsrg.sr.protos;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.FLUSH;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.stack.ProtocolStack;



public class SimpleChat {
    JChannel channel;
    String line;

    BankOperationProtos.BankOperation operation;

    private void start() throws Exception {




        Thread.sleep((long) (Math.random() * 10));
            channel = new JChannel(false);

            //channel = new JChannel(true);


            ProtocolStack stack = new ProtocolStack();
            channel.setProtocolStack(stack);
            stack.addProtocol(new UDP())
                    .addProtocol(new PING())
                    .addProtocol(new MERGE3())
                    .addProtocol(new FD_SOCK())
                    .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                    .addProtocol(new VERIFY_SUSPECT())
                    .addProtocol(new BARRIER())
                    .addProtocol(new NAKACK2())
                    .addProtocol(new UNICAST3())
                    .addProtocol(new STABLE())
                    .addProtocol(new GMS())
                    .addProtocol(new UFC())
                    .addProtocol(new MFC())
                    .addProtocol(new FRAG2())
                    .addProtocol(new SEQUENCER())
                    .addProtocol(new FLUSH());

            stack.init();

            channel.connect("operation");
        for(int i=0;i<2000;i++) {

    operation = BankOperationProtos.BankOperation.newBuilder()
            .setValue(Math.random() / 100 + 1.0)
            .setType(BankOperationProtos.BankOperation.OperationType.MULTIPLY).build();
    operation.toByteArray();

            byte[] raw_stream = operation.toByteArray();

    Message msg = new Message(null, null, raw_stream);
    channel.send(msg);
    Thread.sleep((long) (300000));
}
            channel.close();

    }


    public static void main(String[] args) throws Exception {

        System.setProperty("java.net.preferIPv4Stack","true");
        new SimpleChat().start();

    }
}