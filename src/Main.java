import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("Servern är igång på port 7777...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Klient ansluten.");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String response = calculateExpression(inputLine);
                out.println(response);
            }

            clientSocket.close();
            System.out.println("Klient frånkopplad.");
        }
    }

    private static String calculateExpression(String input) {
        input = input.replaceAll("\\s+", "");

        try {
            if (input.contains("+")) {
                String[] parts = input.split("\\+");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                return "Summan av " + input + " är " + (a + b);
            } else if (input.contains("-")) {
                String[] parts = input.split("-");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                return "Differensen av " + input + " är " + (a - b);
            } else if (input.contains("*")) {
                String[] parts = input.split("\\*");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                return "Produkten av " + input + " är " + (a * b);
            } else if (input.contains("/")) {
                String[] parts = input.split("/");
                int a = Integer.parseInt(parts[0]);
                int b = Integer.parseInt(parts[1]);
                if (b == 0) return "Division med noll är inte tillåten.";
                return "Kvoten av " + input + " är " + (a / b);
            } else {
                return "Ogiltigt uttryck.";
            }
        } catch (Exception e) {
            return "Fel vid beräkning: " + e.getMessage();
        }
    }
}
