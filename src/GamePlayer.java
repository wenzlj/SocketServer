import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GamePlayer {

    private boolean running = false;
    private Socket connection;

    private DataInputStream receive;
    private DataOutputStream send;

    private ArrayList<ActionListener> listener = new ArrayList<>();

    public GamePlayer(Socket player) {
        this.connection = player;
    }

    public void start() throws IOException {
        this.running = true;

        try {
            this.receive = new DataInputStream(this.connection.getInputStream());
            this.send = new DataOutputStream(this.connection.getOutputStream());
        } catch (Exception e) {

        }

        Thread t = new Thread() {

            @Override
            public void run() {
                while (running) {
                    try {
                        String message = receive.readUTF();

                        // verarbeite Nachricht!
                        notifyListener(message);
                    } catch (IOException e) {

                    }
                }
            }
        };

        t.start();
    }

    public void send(String message) {
        if (this.send != null) {
            try {
                send.writeUTF(message);
                send.flush();
            } catch (IOException e) {

            }
        }
    }

    public void stop() {
        this.running = false;
    }

    public void notifyListener(String message) {
        for (ActionListener l: listener) {
            l.actionPerformed(new ActionEvent(this, 0, message));
        }
    }

    public void addActionListener (ActionListener l) {
        this.listener.add(l);
    }
}
