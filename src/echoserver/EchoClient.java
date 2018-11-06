package echoserver;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class KeyboardReader implements Runnable {
    public static OutputStream socketOutput;
    public KeyboardReader(OutputStream socketOutput) {
        this.socketOutput = socketOutput;
    }
    public void run() {
        try{
            int readByte;
            while ((readByte = System.in.read()) != -1) {
                socketOutput.write(readByte);
                socketOutput.flush();
            }
        } catch (IOException io) {}
    }
}

class ServerReader implements Runnable {
    public static InputStream socketInput;
    public ServerReader(InputStream socketInput) {
        this.socketInput = socketInput;
    }

    public void run()  {
        try {
            int socketByte;
            while((socketByte = socketInput.read()) != -1) {
                System.out.write(socketByte);
            }
        } catch (IOException io) {}
    }
}


public class EchoClient {
	private static final int portNumber = 6013;

	public static void main(String[] args) throws IOException {
		String server;
		// Use "127.0.0.1", i.e., localhost, if no server is specified.
		if (args.length == 0) {
			server = "127.0.0.1";
		} else {
			server = args[0];
		}

		try {


			// Connect to the server
			Socket socket = new Socket(server, portNumber);

			// Get the output stream so we can read from standard input and sent it to the socket
			// Get the input stream so we can print data from socket
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();


            Thread outputThread = new Thread(new KeyboardReader(outputStream));
            outputThread.start();

            Thread inputThread = new Thread(new ServerReader(inputStream));
            inputThread.start();

            outputThread.join();
            inputThread.join();

			// Flushes out last bytes
			System.out.flush();

			// Close the socket when we're done reading from it
			socket.close();

			// Provide some minimal error handling.
		} catch (InterruptedException iex) {
            System.out.println("Exception in thread: "+iex.getMessage());
		}
	}
}
