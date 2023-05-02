import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class Recipient {

    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(1050);

            while (true) {
                DatagramPacket pack = new DatagramPacket(new byte[5], 5);
                ds.receive(pack);
                System.out.println(new String(pack.getData()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}