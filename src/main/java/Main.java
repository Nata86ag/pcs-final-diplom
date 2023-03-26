import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static java.lang.System.out;
import static java.sql.DriverManager.println;

public class Main {
    private static final int PORT = 8989;


    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        System.out.println(engine.search("Бизнес"));
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            Gson gson = new Gson();

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    String word = in.readLine();
                    word = word.toLowerCase();
                    List<PageEntry> searchResult = engine.search(word.toLowerCase());
                    out.println(gson.toJson(searchResult));
                }
            }
        } catch (IOException e) {
            out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}