import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class Main {
    private static final int PORT = 8989;

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        out.println(engine.search("бизнес"));
        try (ServerSocket serverSocket = new ServerSocket(8989)) {

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    String word = bufferedReader.readLine();
                    printWriter.println(engine.search(word));
                    Gson gson = new GsonBuilder().create();
                    String jsonList = gson.toJson(engine.search(word));
                    out.println(jsonList);
                }
            }
        } catch (IOException e) {
            out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}

