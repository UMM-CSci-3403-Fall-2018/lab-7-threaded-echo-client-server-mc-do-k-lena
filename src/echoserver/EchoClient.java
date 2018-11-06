package echoserver;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


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
			OutputStream output = socket.getOutputStream();
			InputStream input = socket.getInputStream();

			// Write data from standard input to the server
			// Print all the input we receive from the server
			int keyboardByte;
			int outputByte;
			while ((keyboardByte = System.in.read()) != -1) {
				output.write(keyboardByte);
				output.flush();
				outputByte = input.read();
				System.out.write(outputByte);

			}

			// Flushes out last bytes
			System.out.flush();



			// Close the socket when we're done reading from it
			socket.close();

			// Provide some minimal error handling.
		} catch (ConnectException ce) {
			System.out.println("We were unable to connect to " + server);
			System.out.println("You should make sure the server is running.");
		} catch (IOException ioe) {
			System.out.println("We caught an unexpected exception");
			System.err.println(ioe);
		}
	}
}
