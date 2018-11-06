package echoserver;



import java.net.*;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	public static final int portNumber = 6013;

	public static void main(String[] args) {
		try {
			// Start listening on the specified port
			ServerSocket sock = new ServerSocket(portNumber);

			// Initalize byte variable
			int serverByte;
			// Run forever, which is common for server style services
			while (true) {
				// Wait until someone connects, thereby requesting a date
				Socket client = sock.accept();
				System.out.println("Got a request!");

				// Construct a input from the client
				// and an output to the client
				InputStream input = client.getInputStream();
				OutputStream output = client.getOutputStream();

				// Takes the input from the client and returns it to the client
				while ((serverByte = input.read()) != -1) {
					output.write(serverByte);
					output.flush();
				}

				// just in case
				output.flush();

				// Close the client socket since we're done.
				client.close();
			}
			// *Very* minimal error handling.
		} catch (IOException ioe) {
			System.out.println("We caught an unexpected exception");
			System.err.println(ioe);
		}
	}
}