import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String args[])
    {
        System.out.println("JAVA UDP SERVER");
        DatagramSocket socket = null;
        int portNumber = 9008;

        try{
            socket = new DatagramSocket(portNumber);
            byte[] receiveBuffer = new byte[1024];

            while(true) {
                ///receiving
                Arrays.fill(receiveBuffer, (byte)0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);


                //Get actual data length and copy it
                byte[] data = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), receivePacket.getOffset(), data, 0, receivePacket.getLength());

                //Create string from actual data
                String msg = new String (data);

                byte[] sendBuffer;
                if(msg.equals("1")) sendBuffer="Java".getBytes();
                else if(msg.equals("2")) sendBuffer="Python".getBytes();
                else sendBuffer="Unknown".getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, receivePacket.getAddress(), receivePacket.getPort());
                socket.send(sendPacket);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
