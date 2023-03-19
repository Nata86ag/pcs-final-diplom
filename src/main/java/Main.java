import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static java.lang.System.out;

public class Main {
    private static final int PORT = 8989;


    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        out.println(engine.search("бизнес"));
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            Gson gson = new Gson();

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    String word = in.readLine();
                    List<PageEntry> searchResult = engine.search(word);
                    out.println(gson.toJson(searchResult));
                }
            }
        } catch (IOException e) {
            out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}