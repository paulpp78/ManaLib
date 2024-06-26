package server.serv.bttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BttpProtocole {
    private final Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public BttpProtocole(Socket socket) {
        this.socket = socket;
        initInOut();
    }

    public void initInOut() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public synchronized String communicate(String response) throws IOException {
        out.println(response);
        return in.readLine();
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized String getResponse() throws IOException {
        return in.readLine();
    }

    public synchronized void sendResponse(String response) throws IOException {
        out.println(response);
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Erreur lors de la fermeture : " + e.getMessage());
        }
    }
}
