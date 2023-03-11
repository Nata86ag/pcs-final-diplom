import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] arg) throws IOException {

        try (Socket socket = new Socket("localhost", 8989);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("бизнес");
            String json = in.readLine();
            System.out.println(json);
        } catch (IOException e) {
            System.out.println("Не могу запустить клиента");
            throw new RuntimeException(e);
        }
    }
}