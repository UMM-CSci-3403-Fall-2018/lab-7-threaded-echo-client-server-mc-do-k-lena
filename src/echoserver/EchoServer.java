package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			OutputStream outputStream = socket.getOutputStream();

			Thread a = new Thread(new ServerThreader(outputStream, socket, inputStream));
			a.start();
		}
	}
}

class ServerThreader implements Runnable {
	private OutputStream outputStream;
	private Socket socket;
	private InputStream inputStream;

	public ServerThreader(OutputStream outputStream, Socket socket, InputStream inputStream) {
		this.socket = socket;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public void run() {
		try {
			int b;
			while ((b = inputStream.read()) != -1) {
				outputStream.write(b);
			}
			socket.shutdownOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}