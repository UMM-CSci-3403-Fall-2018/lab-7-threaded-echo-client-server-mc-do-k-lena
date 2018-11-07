package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
    public static final int PORT_NUMBER = 6013;

    public static void main(String[] args) throws IOException {
        EchoClient client = new EchoClient();
        client.start();
    }

    private void start() throws IOException {
        Socket socket = new Socket("localhost", PORT_NUMBER);
        InputStream socketInputStream = socket.getInputStream();
        OutputStream socketOutputStream = socket.getOutputStream();

        Thread outputThread = new Thread(new KeyboardReader(socketOutputStream, socket));
        Thread inputThread = new Thread(new ServerWriter(socketInputStream, socket));

        outputThread.start();
        inputThread.start();

        try {
            outputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            inputThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.flush();
    }
}

class KeyboardReader implements Runnable {
    private OutputStream outputStream;
    private Socket socket;

    public KeyboardReader(OutputStream outputStream, Socket socket) {
        this.socket = socket;
        this.outputStream = outputStream;
    }

    public void run() {
        try {
            int readByte;
            while ((readByte = System.in.read()) != -1) {
                outputStream.write(readByte);
            }

            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerWriter implements Runnable {
    private InputStream inputStream;
    private Socket inputSocket;

    public ServerWriter(InputStream inputStream, Socket inputSocket) {
        this.inputStream = inputStream;
        this.inputSocket = inputSocket;
    }
    public void run() {
        try {
            int socketByte;
            while ((socketByte = inputStream.read()) != -1){
                System.out.write(socketByte);
            }

            inputSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}