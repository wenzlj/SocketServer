
import java.io.*;

public class Client {
    public static void main(String[] args) {
        Client client = new Client();

    }

    void test() throws IOException {
        int port = 187;
        java.net.Socket socket = new java.net.Socket(port); // verbindet sich mit Server
        String zuSendendeNachricht = "Player ready!";
        schreibeNachricht(socket, zuSendendeNachricht);

    }

    void schreibeNachricht(java.net.Socket socket, String nachricht) throws IOException {
        PrintWriter printWriter =
                new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()));
        printWriter.print(nachricht);
        printWriter.flush();
    }
}
