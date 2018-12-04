import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    private int port;
    private boolean running = false;

    private ArrayList<GamePlayer> clients = new ArrayList<>();

    public GameServer(int port) {
        this.port = port;
    }

    private ActionListener broadcastListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (GamePlayer p : clients) {
                if (!p.equals(e.getSource())){
                    p.send(e.getActionCommand());
                }
            }
        }
    };
    public void start() {
        this.running = true;

        Thread serverThread = new Thread() {

            @Override
            public void run() {
                try (ServerSocket server = new ServerSocket(port)) {


                    while (running) {
                        // Client verbindet sich auf den Server
                        Socket client = server.accept();

                        // Weitere Aktionen mit dem Client
                        GamePlayer p = new GamePlayer(client);

                        p.addActionListener(broadcastListener);

                        clients.add(p);
                    }
                } catch (Exception e) {

                }
            }
        };

        serverThread.start();
    }

    public void stop() {
        this.running = false;
    }
}
