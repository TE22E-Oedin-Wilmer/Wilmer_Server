import java.net.*;
import java.io.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class MulticastChatServer {

    public static void main(String args[]) throws Exception {
        int portnumber = 5000;
        if (args.length > 0) {
            portnumber = Integer.parseInt(args[0]);
        }

        MulticastSocket serverMulticastSocket = new MulticastSocket(portnumber);
        InetAddress group = InetAddress.getByName("225.4.5.6");
        serverMulticastSocket.joinGroup(group);

        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // ScriptEngine för att beräkna uttryck
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

        while (true) {
            serverMulticastSocket.receive(packet);
            String expression = new String(packet.getData(), 0, packet.getLength()).trim();
            System.out.println("Mottaget uttryck: " + expression);

            String resultMsg;
            try {
                Object result = engine.eval(expression);
                resultMsg = "Resultatet är: " + result.toString();
            } catch (Exception e) {
                resultMsg = "Fel i uttrycket!";
            }

            // Skicka tillbaka svaret
            byte[] responseData = resultMsg.getBytes();
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData, responseData.length, group, portnumber);
            serverMulticastSocket.send(responsePacket);
        }
    }
}
