package smallproject.tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TCPServer {
    private static void work(Socket client) throws IOException {
        //TODO- output not working
        Thread thread = new Thread(new Runnable() {

            public void run() {
                try {
                    InputStreamReader isr = new InputStreamReader(client.getInputStream());
                    BufferedReader reader = new BufferedReader(isr);
                    String reqBody = reader.readLine();
                    while (!reqBody.isBlank()) {
                        System.out.println(reqBody);
                        reqBody = reader.readLine();
                    }
                    Date today = new Date();
                    String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "Hello World!" + today;
                    client.getOutputStream().write(httpResponse.getBytes("UTF-8"));

                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6666);
        System.out.println("Server is listening on port : 6666");
        while (true) {
            Socket client = server.accept();
            System.out.println("The client has been connected " + client.getChannel());
            work(client);
        }

    }
}
/**
 * Improvements : --
 * limiting number of threads
 * add thread pool to save on thread creation time
 * connection timeout
 * tcp backing queue confirmation
 * **/